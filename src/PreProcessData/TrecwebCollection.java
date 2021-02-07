package PreProcessData;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.text.html.parser.ParserDelegator;

import Classes.Path;
import Classes.ParseHtml;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 * This is for INFSCI-2140 in 2020
 *
 */
public class TrecwebCollection implements DocumentCollection {
	//you can add essential private methods or variables
	private BufferedReader trec_web_reader;
	Boolean exist_text;
	
	// YOU SHOULD IMPLEMENT THIS METHOD
	public TrecwebCollection() throws IOException {
		// This constructor should open the file existing in Path.DataWebDir
		// and also should make preparation for function nextDocument()
		// you cannot load the whole corpus into memory here!!
		try {
			BufferedInputStream f = new BufferedInputStream (new FileInputStream (new File (Path.DataWebDir)));
			int buffer_size = 10 * 1024 * 1024; // Buffer of 10 MB
			this.trec_web_reader = new BufferedReader(new InputStreamReader(f, "utf-8"), buffer_size);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	// YOU SHOULD IMPLEMENT THIS METHOD
	@Override
	public Map<String, Object> nextDocument() throws IOException {
		// this method should load one document from the corpus, and return this document's number and content.
		// the returned document should never be returned again.
		// when no document left, return null
		// NT: the returned content of the document should be cleaned, all html tags should be removed.
		// NTT: remember to close the file that you opened, when you do not use it any more
		try {
			String line, doc_no = "";
			StringBuffer data = new StringBuffer();
			
			while ((line = trec_web_reader.readLine()) != null) {
				int line_length = line.length();
				if (line.length() > 15 && line.substring(0,7).equals("<DOCNO>"))
					doc_no = line.split("</")[0].substring(7).trim(); // Split string into 2 when encountering </ and get the first part of the string starting from the 7th poistion
				else if (line.equals("<DOCHDR>"))
					exist_text = false;
				else if (line.equals("<html>") || (line_length > 10 && line.substring(0,5).equals("<html")))
					exist_text = true;
				else if (line.equals("</DOC>")) {
					exist_text = null;
					if (data == null) {
						return null;
					}
					Map<String, Object> webpage = new LinkedHashMap<String, Object>();
					ParseHtml parse = new ParseHtml(); //Parsing HTML tags
					ParserDelegator parse_del = new ParserDelegator(); // Delegator for using the starting and ending tags specified in ParseHtml class
					parse_del.parse(new BufferedReader(new InputStreamReader(new ByteArrayInputStream(data.toString().getBytes()))), parse, true);
					webpage.put(doc_no, parse.get_web_content().toString().toCharArray());
					return webpage;
				}
				if (exist_text != null && exist_text)
					data.append(line);
			}
			trec_web_reader.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
