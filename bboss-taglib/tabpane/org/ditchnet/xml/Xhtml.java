/*
 * The contents of this file are subject to the GNU Lesser General Public
 * License Version 2.1 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of
 * the License at http://www.gnu.org/copyleft/lesser.html
 *
 * Software distributed under the License is distributed on an "AS
 * IS" basis, WITHOUT WARRANTY OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * rights and limitations under the License.
 *
 * Developer:
 * Todd Ditchendorf, todd@ditchnet.org
 *
 */

/**
 *	@author Todd Ditchendorf
 *	@since 2005-03-12
 *	@version 0.8
 *
 *
 *
 *
 */
package org.ditchnet.xml;

/**
 *	<p>Some of the {@link org.ditchnet.jsp.util.JspResponseWriter} class' 
 *	public instance methods write markup (XML, specifically) to the response
 *	stream. The <code>Xml</code> interface, it's static inner interfaces
 *	<code>Tag</code> and <code>Attr</code> enforce type-safety and decrease the
 *	likelyhood of malformed XML (which is not truely XML anyway) being 
 *	generated by the JspResponseWriter class.</p>
 *	<p>This class is one implementation of the <code>Xml</code> interface that
 *	represents a specific XML vocabulary - XHTML 1.0 Transitional.</p>
 *	<p>This class' static inner classes {@link org.ditchnet.xml.Xhtml.Tag}
 *	and {@link org.ditchnet.xml.Xhtml.Attr} present an enumerations of
 *	elements and attributes included in the XHTML vocabulary.</p>
 *
 *	@author Todd Ditchendorf
 *	 
 *
 */
public class Xhtml implements Xml {
	
	/**
	 *	Private constructor. This is a utility class that may not be 
	 *	instanciated by client code.
	 */
	private Xhtml() { }
	
	/**
	 *	<p>This class implements the Type-Safe Enum pattern as outlined by
	 *	Joshua Bloch in <em>Effective Java</em>. The only constructor is 
	 *	private, and therefore this class may not be instanciated by client
	 *	code.</p>
	 *	<p>This class' static member variables present the complete set of 
	 *	elements available in the XHTML 1.0 Transitional vocabulary.</p>
	 *
	 *	@since 2005-03-12
	 *	@version 0.8
	 *	@author Todd Ditchendorf
	 *
	 */
	public static class Tag implements Xml.Tag {
		
		public static final Tag A				= new Tag("a");
		public static final Tag ABBR 			= new Tag("abbr");
		public static final Tag ACRONYM 		= new Tag("acronym");
		public static final Tag ADDRESS 		= new Tag("address");
		public static final Tag APPLET			= new Tag("applet");
		public static final Tag BASE 			= new Tag("base");
		public static final Tag BLOCKQUOTE		= new Tag("blockquote");
		public static final Tag BODY 			= new Tag("body");
		public static final Tag BR				= new Tag("br");
		public static final Tag BUTTON			= new Tag("button");
		public static final Tag CAPTION 		= new Tag("caption");
		public static final Tag CITE 			= new Tag("cite");
		public static final Tag CODE 			= new Tag("code");
		public static final Tag COMMENT 		= new Tag("comment");
		public static final Tag DD				= new Tag("dd");
		public static final Tag DEL 			= new Tag("del");
		public static final Tag DFN 			= new Tag("dfn");
		public static final Tag DIR 			= new Tag("dir");
		public static final Tag DIV 			= new Tag("div");
		public static final Tag DL				= new Tag("dl");
		public static final Tag DT				= new Tag("dt");
		public static final Tag EM				= new Tag("em");
		public static final Tag HEAD 			= new Tag("head");
		public static final Tag FORM 			= new Tag("form");
		public static final Tag FIELDSET 		= new Tag("fieldset");
		public static final Tag HTML 			= new Tag("html");
		public static final Tag H1				= new Tag("h1");
		public static final Tag H2				= new Tag("h2");
		public static final Tag H3				= new Tag("h3");
		public static final Tag H4				= new Tag("h4");
		public static final Tag H5				= new Tag("h5");
		public static final Tag H6				= new Tag("h6");
		public static final Tag IFRAME			= new Tag("iframe");
		public static final Tag IMG 			= new Tag("img");
		public static final Tag INPUT			= new Tag("input");
		public static final Tag LABEL			= new Tag("label");
		public static final Tag LEGEND			= new Tag("legend");
		public static final Tag LI				= new Tag("li");
		public static final Tag LINK 			= new Tag("link");
		public static final Tag META 			= new Tag("meta");
		public static final Tag NOSCRIPT 		= new Tag("noscript");
		public static final Tag OBJECT			= new Tag("object");
		public static final Tag OL				= new Tag("ol");
		public static final Tag OPTGROUP		= new Tag("optgroup");
		public static final Tag OPTION			= new Tag("option");
		public static final Tag PARAM			= new Tag("param");
		public static final Tag PRE 			= new Tag("pre");
		public static final Tag Q	 			= new Tag("q");
		public static final Tag SAMP 			= new Tag("samp");
		public static final Tag SPAN 			= new Tag("span");
		public static final Tag SCRIPT			= new Tag("script");
		public static final Tag STRONG			= new Tag("strong");
		public static final Tag SUMMARY 		= new Tag("summary");
		public static final Tag TABLE			= new Tag("table");
		public static final Tag TITLE			= new Tag("title");
		public static final Tag TEXTAREA		= new Tag("textarea");
		public static final Tag TBODY			= new Tag("tbody");
		public static final Tag TD				= new Tag("td");
		public static final Tag TFOOT			= new Tag("tfoot");
		public static final Tag THEAD			= new Tag("thead");
		public static final Tag TH				= new Tag("th");
		public static final Tag TR				= new Tag("tr");
		public static final Tag UL				= new Tag("ul");
		public static final Tag VAR				= new Tag("var");
		
		/** This tag's name */
		private final String name;
		
		/**
		 *	Sole private constructor. Used only to construct public static final 
		 *	enumeration of elements available in XHTML 1.0 Transitional.
		 *	Stores reference to this tag's name.
		 */
		private Tag(final String name) { 
			this.name = name;
		}
		
		/**
		 *	Return this tag's name.
		 */
		public String toString() {
			return name;
		}
		
	}
	
	/**
	 *	<p>This class implements the Type-Safe Enum pattern as outlined by
	 *	Joshua Bloch in <em>Effective Java</em>. The only constructor is 
	 *	private, and therefore this class may not be instanciated by client
	 *	code.</p>
	 *	<p>This class' static member variables present the complete set of 
	 *	attribute names available in the XHTML 1.0 Transitional 
	 *	vocabulary.</p>
	 *
	 *	@since 2005-03-12
	 *	@version 0.8
	 *	@author Todd Ditchendorf
	 *
	 *
	 */
	public static class Attr implements Xml.Attr {
				
		public static final Attr ALT				= new Attr("alt");
		public static final Attr BORDER				= new Attr("border");
		public static final Attr CHECKED 			= new Attr("checked");
		public static final Attr CLASS				= new Attr("class");
		public static final Attr DIR	 			= new Attr("dir");
		public static final Attr DISABLED			= new Attr("disabled");
		public static final Attr HREF				= new Attr("href");
		public static final Attr ID					= new Attr("id");
		public static final Attr ENABLECOOKIE		= new Attr("enablecookie");
		
		public static final Attr LANG	 			= new Attr("lang");
		public static final Attr LONGDESC	 		= new Attr("longdesc");
		public static final Attr NAME				= new Attr("name");
		public static final Attr ONBLUR				= new Attr("onblur");
		public static final Attr ONCHANGE			= new Attr("onchange");
		public static final Attr ONCLICK 			= new Attr("onclick");
		public static final Attr ONDBLCLICK 		= new Attr("ondblclick");
		public static final Attr ONFOCUS	 		= new Attr("onfocus");
		public static final Attr ONKEYDOWN			= new Attr("onkeydown");
		public static final Attr ONKEYPRESS 		= new Attr("onkeypress");
		public static final Attr ONKEYUP 			= new Attr("onkeyup");
		public static final Attr ONLOAD				= new Attr("onload");
		public static final Attr ONMOUSEDOWN 		= new Attr("onmousedown");
		public static final Attr ONMOUSEMOVE 		= new Attr("onmousemove");
		public static final Attr ONMOUSEOVER 		= new Attr("onmouseover");
		public static final Attr ONSELECT			= new Attr("onselect");
		public static final Attr REL 				= new Attr("rel");
		public static final Attr SELECTED			= new Attr("selected");
		public static final Attr SRC 				= new Attr("src");
		public static final Attr STYLE				= new Attr("style");
		public static final Attr TARGET 			= new Attr("target");
		public static final Attr TITLE				= new Attr("title");
		public static final Attr TYPE				= new Attr("type");
		public static final Attr VALUE				= new Attr("value");
		public static final Attr IFRAMEID				= new Attr("iframeid");
		public static final Attr IFRAMESRC				= new Attr("iframesrc");
		
		/** This attribute's name */
		private final String name;
		
		/**
		 *	Sole private constructor. Used only to construct public static final 
		 *	enumeration of attribute names available in XHTML 1.0
		 *	Transitional.
		 *	Stores reference to this tag's name.
		 */
		private Attr(final String name) { 
			this.name = name;
		}
		
		/**
		 *	Return this attribute's name.
		 */
		public String toString() {
			return name;
		}
	}
	
}


