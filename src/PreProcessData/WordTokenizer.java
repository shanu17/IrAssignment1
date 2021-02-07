package PreProcessData;

import java.util.StringTokenizer;

/**
 * This is for INFSCI-2140 in 2020
 *
 * TextTokenizer can split a sequence of text into individual word tokens.
 */
public class WordTokenizer {
	//you can add essential private methods or variables
	private StringTokenizer sTokenizer;
	private String wekaDelimiters = " \r\n\t.,;:\"()?!";
	private String extraDelimiters = "-+*/[]_";
	

	// YOU MUST IMPLEMENT THIS METHOD
	public WordTokenizer( char[] texts ) {
		// this constructor will tokenize the input texts (usually it is a char array for a whole document)
		this.sTokenizer = new StringTokenizer(String.valueOf(texts), this.wekaDelimiters + this.extraDelimiters);
	}

	// YOU MUST IMPLEMENT THIS METHOD
	public char[] nextWord() {
		// read and return the next word of the document
		// or return null if it is the end of the document
		while (this.sTokenizer.hasMoreTokens()) {
			char[] c = this.sTokenizer.nextToken().toCharArray();
			if (not_alpha(c))
				continue;
			return c;
		}
		return null;
	}

	private boolean not_alpha(char[] t) {
		for (char c : t) {
			if (c == '\'')
				continue;
			if (!Character.isLetter(c))
				return true;
		}
		return false;
	}
}
