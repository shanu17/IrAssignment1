package PreProcessData;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import Classes.Path;
import java.util.Map;
import java.util.HashMap;

/**
 * This is for INFSCI-2140 in 2020
 *
 */
public class TrectextCollection implements DocumentCollection {
	//you can add essential private methods or variables
	
	BufferedReader file_read;
	// YOU SHOULD IMPLEMENT THIS METHOD
	public TrectextCollection() throws IOException {
		// This constructor should open the file existing in Path.DataTextDir
		// and also should make preparation for function nextDocument()
		// you cannot load the whole corpus into memory here!!
		
		try {
			BufferedInputStream f = new BufferedInputStream (new FileInputStream (new File (Path.DataTextDir)));
			int buffer_size = 10 * 1024 * 1024; // Read up to 10 MB of data
			this.file_read = new BufferedReader(new InputStreamReader(f, "utf-8"), buffer_size); 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// YOU SHOULD IMPLEMENT THIS METHOD
	public Map<String, Object> nextDocument() throws IOException {
		// this method should load one document from the corpus, and return this document's number and content.
		// the returned document should never be returned again.
		// when no document left, return null
		// NTT: remember to close the file that you opened, when you do not use it any more
		
		try {
			Map<String, Object> doc = new HashMap<String, Object>();
			String line, doc_no = "", text = "";
			Boolean exist_text = null;
			
			while((line = file_read.readLine()) != null) {
				int line_length = line.length();
				if(line_length > 15 && line.substring(0,7).equals("<DOCNO>")) {
					doc_no = line.split("</")[0].substring(8).trim(); // Split the string when it encounters </ and then get the first part of the string starting from position 8
					doc.put(doc_no, null);
					continue;
				} else if (line.equals("<TEXT>")) {
					exist_text = true;
					continue;
				} else if (line.equals("</TEXT>")) {
					exist_text = false;
				} else if (line.equals("</DOC>")) {
					return doc;
				}
				if (exist_text != null) {
					if(exist_text)
						text += line.trim();
					else {
						doc.put(doc_no, text.toCharArray());
						exist_text = false;
					}
				}
			}
			file_read.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
