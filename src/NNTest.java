
import weka.classifiers.functions.NeuralNetwork;
import weka.core.Instances;


public class NNTest {
	 public static void main(String[] args) throws Exception {
			
		 	String source="testall";
		    Instances structure = Util.readArff("D:\\Documents\\"+source+".arff");
		    NeuralNetwork nn=new NeuralNetwork();

	 }
}
