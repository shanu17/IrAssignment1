package PreProcessData;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashSet;

import Classes.*;

/**
 * This is for INFSCI-2140 in 2020
 *
 */

public class StopWordRemover {
	//you can add essential private methods or variables.
	private HashSet<String> stopwords;

	public StopWordRemover( ) {
		// load and store the stop words from the fileinputstream with appropriate data structure
		// that you believe is suitable for matching stop words.
		// address of stopword.txt should be Path.StopwordDir
		try {
			this.stopwords = new HashSet<>(); //HashSet has search of O(1) and no duplicate values
			BufferedInputStream f = new BufferedInputStream(new FileInputStream(new File(Path.StopwordDir)));
			int buffer_size = 10 * 1024 * 1024;
			BufferedReader stopword_reader = new BufferedReader(new InputStreamReader(f, "utf-8"), buffer_size);
			String l;
			while((l = stopword_reader.readLine()) != null)
				stopwords.add(l);
			stopword_reader.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	// YOU MUST IMPLEMENT THIS METHOD
	public boolean isStopword( char[] word ) {
		// return true if the input word is a stopword, or false if not
		return this.stopwords.contains(String.valueOf(word));
	}
}
