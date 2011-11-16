
<%@page import="org.frameworkset.spi.assemble.ProviderInfoQueue"%>
<%@page import="java.util.List,org.frameworkset.spi.assemble.Pro"%>
<%@page import="java.lang.reflect.Method"%>
<%@page import="org.frameworkset.web.servlet.context.WebApplicationContext"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Iterator"%><%
/**
 * 
 * <p>Title: 管理服务明细信息显示页面</p>
 *
 * <p>Description: 管理服务明细信息显示页面</p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: chinacreator</p>
 * @Date 2008-9-19
 * @author gao.tang
 * @version 1.0
 */
 %>
<%@ page language="java" contentType="text/html; charset=GBK" session="false"%>
<%@page import="org.frameworkset.spi.assemble.*,java.util.*,org.frameworkset.spi.BaseApplicationContext"%>
<%@ taglib prefix="tab" uri="/WEB-INF/tabpane-taglib.tld" %>		

<% 
	String rootpath = request.getContextPath();
	String selected = request.getParameter("selected");
	org.frameworkset.persitent.util.SQLUtil sqlutil = org.frameworkset.persitent.util.SQLUtil.getInstance(selected.substring(4));
	BaseApplicationContext context = sqlutil.getSqlcontext();
	
	java.util.Set<String> keys = context.getPropertyKeys();
	Iterator<String> its = keys == null ? null:keys.iterator();
	
	
	
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title><%=context.getConfigfile() %></title>
		<link rel="stylesheet" type="text/css" href="<%=rootpath%>/sysmanager/css/treeview.css">
<%@ include file="/include/css.jsp"%>
		<link rel="stylesheet" type="text/css" href="<%=rootpath%>/sysmanager/css/contentpage.css">
		<link rel="stylesheet" type="text/css" href="<%=rootpath%>/sysmanager/css/tab.winclassic.css">
		<tab:tabConfig/>	
	</head>
	
	<body class="contentbodymargin" scroll="yes">
	
		<table class="thin" >
			<tr><td colspan="3" class="headercolor">SQL文件概要信息</td></tr>
			<tr>
			<td  width="20%">SQL文件名称：<%=selected.substring(4) %></td>
			
			</tr>
			<tr>
			
			<td  width="20%">SQL文件更新检测机制：<%=sqlutil.getRefresh_interval() > 0?"开启":"关闭" %></td>
			
			</tr>
			<tr>
			
			<td width="50%">SQL文件更新检测时间间隔：<%=sqlutil.getRefresh_interval() +"" %></td>
			</tr>
			<tr>
			
			<td width="50%">开启SQL文件更新检测机制和检测时间间隔配置方法：<br>
			在bboss-aop.jar包中的aop.properties文件中，配置参数sqlfile.refresh_interval<br>
			sqlfile.refresh_interval=5000<br>
			单位为毫秒，如果配置为0或者负数，则关闭刷新机制
			</td>
			</tr>
			
			<tr>
			
			<td width="50%">SQL文件容器类型：<%=context.getClass().getCanonicalName() %></td>
			</tr>
			
		</table>
		<table class="thin" >
			<tr><td colspan="3" class="headercolor">SQL配置信息</td></tr>
			<tr>
			<td class="headercolor" >SQL名称</td>
			<td class="headercolor" >sql</td>
			<td class="headercolor" >描述</td>
			</tr>
			<%while(its != null && its.hasNext() ){
				Pro pro = context.getProBean(its.next());
				
			 %>
			<tr>
			<td class="headercolor"><%=pro.getName() %></td>
			<td wrap><%=pro.getValue() %></td>
			<td width="40"><%=pro.getDescription() == null?"":pro.getDescription() %></td>
			</tr>
			<%} %>
		</table>
	

	</body>
</html>