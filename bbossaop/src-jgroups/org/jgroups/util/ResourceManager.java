package org.jgroups.util;

import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.rmi.server.UID;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.jgroups.Global;

/**
 * Manages resources such as multicast addresses and multicast ports, and TCP
 * ports. This class is mainly used for running unit tests in parallel (TestNG)
 * and preventing clusters intended to be separate from joining each other.
 * 
 * @author Bela Ban
 * @version $Id: ResourceManager.java,v 1.9 2010/06/15 10:10:38 belaban Exp $
 */
public class ResourceManager {
	private static final IpAddressRep rep;
	private static short mcast_port;
	private static short tcp_port;
    private static SocketFactory socket_factory=new DefaultSocketFactory();

	static {

        StackType type=Util.getIpStackType();

        String tmp_addr = System.getProperty(Global.INITIAL_MCAST_ADDR,
                                             type == StackType.IPv6? "ff0e::9:9:9" : "230.1.1.1");
        mcast_port = Short.valueOf(System.getProperty(Global.INITIAL_MCAST_PORT, "7000"));
		tcp_port = Short.valueOf(System.getProperty(Global.INITIAL_TCP_PORT, "10000"));
		try {
			InetAddress tmp = InetAddress.getByName(tmp_addr);
			if (!tmp.isMulticastAddress())
				throw new IllegalArgumentException("initial multicast address "	+ tmp_addr + " is not a valid multicast address");
			
			if (tmp instanceof Inet4Address)
				rep = new IPv4AddressRep(tmp_addr);
			else
				rep = new IPv6AddressRep(tmp_addr);
		
		} catch (UnknownHostException e) {
			throw new RuntimeException("initial multicast address " + tmp_addr + " is incorrect", e);
		}
	}

	private ResourceManager() {
	}

	/**
	 * Returns the next available multicast address, e.g. "228.1.2.3". This
	 * class is a JVM singleton
	 * 
	 * @return
	 */
	public static String getNextMulticastAddress() {
		return rep.nextAddress();
	}

	public static synchronized short getNextMulticastPort(InetAddress bind_addr) throws Exception {
		short port = mcast_port++;
		try {
			DatagramSocket sock = Util.createDatagramSocket(socket_factory, "jgroups.temp.resourcemgr.mcast_sock", bind_addr, port);
			port = (short) sock.getLocalPort();
			socket_factory.close(sock);
			ServerSocket srv_sock = Util.createServerSocket(socket_factory, "jgroups.temp.resourcemgr.srv_sock", bind_addr, port);
			port = (short) srv_sock.getLocalPort();
			socket_factory.close(srv_sock);
			return port;
		} finally {
			mcast_port = (short) (port + 1);
		}
	}

	public static synchronized List<Short> getNextTcpPorts(
			InetAddress bind_addr, int num_requested_ports) throws Exception {
		short port = tcp_port++;
		List<Short> retval = new ArrayList<Short>(num_requested_ports);

		for (int i = 0; i < num_requested_ports; i++) {
			ServerSocket sock = Util.createServerSocket(socket_factory, "jgroups.temp.resourcemgr.srv_sock", bind_addr, port);
			port = (short) sock.getLocalPort();
			retval.add(port);
			tcp_port = ++port;
			socket_factory.close(sock);
		}
		return retval;
	}

	public static String getUniqueClusterName(String base_name) {
		return base_name != null ? base_name + "-" + new UID().toString()
				: new UID().toString();
	}

	public static String getUniqueClusterName() {
		return getUniqueClusterName(null);
	}

	public static void main(String[] args) throws Exception {
		List<Short> ports = getNextTcpPorts(InetAddress
				.getByName("192.168.1.5"), 15);
		System.out.println("ports = " + ports);

		ports = getNextTcpPorts(InetAddress.getByName("192.168.1.5"), 5);
		System.out.println("ports = " + ports);

	}

	/*
	 * Interface for IpAddress representations
	 */
	public interface IpAddressRep {
		public String nextAddress();
	}

	/** Representation of an IPv4 address */
	static class IPv4AddressRep implements IpAddressRep {
		short a = 225, b = MIN, c = MIN, d = MIN;

		private static final short MIN = 1, MAX = 250;
		private static final char DOT = '.';

		IPv4AddressRep(String initial_addr) {
			StringTokenizer tok = new StringTokenizer(initial_addr, ".", false);
			a = Short.valueOf(tok.nextToken());
			b = Short.valueOf(tok.nextToken());
			c = Short.valueOf(tok.nextToken());
			d = Short.valueOf(tok.nextToken());
		}

		public synchronized String nextAddress() {
			StringBuilder sb = new StringBuilder();
			sb.append(a).append(DOT).append(b).append(DOT).append(c)
					.append(DOT).append(d);
			increment();
			return sb.toString();
		}

		private void increment() {
			d++;
			if (d > MAX) {
				d = MIN;
				c++;
				if (c > MAX) {
					c = MIN;
					b++;
					if (b > MAX) {
						b = MIN;
						a++;
						if (a > MAX)
							a = 225;
					}
				}
			}
		}
	}

	/** Representation of an IPv6 address */
	static class IPv6AddressRep implements IpAddressRep {

		byte[] bv = null;
		InetAddress address = null;
		
		private static boolean carry = false;

		IPv6AddressRep(String initial_addr) {
			
			try {
				address = InetAddress.getByName(initial_addr);
			} catch (UnknownHostException e) {
				//
				throw new RuntimeException ("Multicast address " + initial_addr + " has incorrect format", e) ; 
			} catch (SecurityException e) {
				//
				throw new RuntimeException ("Security violation in accessing multicast address " + initial_addr, e) ; 
			}

			// get the byte representation
			bv = address.getAddress();
		}

		public synchronized String nextAddress() {
			// build the existing address, then create the next one
			try {
				address = InetAddress.getByAddress(bv);
			} catch (UnknownHostException e) {
				//
				throw new RuntimeException ("Multicast address has incorrect length", e) ; 
			} 
			increment();
			
			// strings are returned as hostname/IP address, so remove the hostname prefix
			// before returning the string
			String addressWithHostname = address.toString();
			
			return addressWithHostname.substring(addressWithHostname.indexOf('/')+1) ;
		}

		private void increment() {
			
			// process hex digits from right to left
			for (int i = bv.length - 1; i >= 0; i--) {

				// increment the ith byte
				bv[i] =incrementHexValue(bv[i]);

				// if carry, increment (i-1)th byte
				if (carry) {
					carry = false;
					continue;
				}
				// otherwise, we are done
				return ;
			}
			// if we reach here, incrementing no longer possible
			throw new RuntimeException ("Cannot increment multicast address ") ; 
		}

		// increments a byte in hex and notes if carry req'd
		// these bytes contain 2's complement representations
		// of hex values "00" through "ff"
		private static byte incrementHexValue(byte b) {

			// "00" to "7e"
			if (b >= 0 && b < 127)
				return (byte) (b + (byte) 1);
			// "7f"
			else if (b == 127) {
				return (byte) -128;
			}
			// "80" to "fe"
			else if (b >= -128 && b < -1) {
				return (byte) (b + (byte) 1);
			}
			// "ff"
			else if (b == -1) {
				carry = true;
				return (byte) 0;
			}
			return 0;
		}
	}
}
