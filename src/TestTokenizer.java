import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import weka.core.tokenizers.NGramTokenizer;
import weka.core.tokenizers.Tokenizer;


public class TestTokenizer {

	@Test
	public void test() {
		NGramTokenizer t=new NGramTokenizer();
		t.setNGramMaxSize(1);
		t.setNGramMinSize(1);
		t.setDelimiters("\\s\\r\\n\\t");
		t.tokenize("regen :(\r\n jjjppp kkk\n krkekrk");
		while(t.hasMoreElements())
		System.out.println(t.nextElement());
		
	}

	
	@Test
	public void ConfusionTest() {
		ConfusionMatrixGuess guess;
		try {
			guess = new ConfusionMatrixGuess("D:\\Documents\\confusion.csv");
			System.out.println(guess.guess("0"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
