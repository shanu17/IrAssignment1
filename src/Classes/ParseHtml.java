package Classes;

import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;

public class ParseHtml extends HTMLEditorKit.ParserCallback{
	
	private boolean load;
	private StringBuffer web_content;
	
	public ParseHtml() {
		super();
		this.web_content = new StringBuffer();
		this.load = true;
	}
	
	/*
	 * Specifying the starting tag to parse
	 * These functions get overriden
	 */
	public void handleStartTag(HTML.Tag t, MutableAttributeSet x, int pos) {
		if (t.equals(HTML.Tag.BODY))
			load = true;
	}
	
	/*
	 * Specifying the ending tag to parse
	 * These functions get overriden 
	 */
	public void handleEndTag(HTML.Tag t, int pos) {
		if(t.equals(HTML.Tag.BODY))
			load = false;
	}
	
	/*
	 * Creating the data available
	 */
	public void handleText(char[] data, int pos) {
		if (load)
			this.web_content.append(data).append(' ');
	}
	
	public StringBuffer get_web_content() {
		return web_content;
	}
}
