import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import weka.filters.unsupervised.attribute.StringToWordVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.stemmers.IteratedLovinsStemmer;

public class Util {

	public static Instances readArff(String path) throws IOException{
		 BufferedReader reader = new BufferedReader(
       new FileReader(path));
		Instances data = new Instances(reader);
		reader.close();
		return data;
	}
	public static String readFile(String path) 
			  throws IOException 
			{
			  byte[] encoded = Files.readAllBytes(Paths.get(path));
			  return new String(encoded,Charset.defaultCharset());
			}
	public static void Outfile(String text,String path) throws IOException{
		BufferedWriter writer = new BufferedWriter(new FileWriter(path));
	    writer.write(text);
	    writer.newLine();
	    writer.flush();
	    writer.close();
		
	}
}
