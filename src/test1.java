import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import weka.classifiers.*;
import weka.classifiers.bayes.NaiveBayesMultinomialText;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.functions.SGDText;
import weka.core.Instances;
import weka.core.Instance;
import weka.core.stemmers.*;
import weka.core.tokenizers.NGramTokenizer;
import weka.core.tokenizers.Tokenizer;
public class test1 {
	
	 public static void main(String[] args) {
		Instances train;
		Instances test;
		try {
//			train = readArff("D:\\Documents\\test1000rand.arff"); "D:\\Documents\\testall.arff"
			train = readArff("D:\\Documents\\test1000rand.arff");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return;
		}
	    train.setClassIndex(1);
		//String opt = "-W -P 0 -M 5.0 -norm 1.0 -lnorm 2.0 -lowercase -stoplist -stopwords C:\\Users\\Fernando\\workspace\\GPCommentsAnalyzer\\pt-br_stopwords.dat -tokenizer \"weka.core.tokenizers.NGramTokenizer -delimiters ' \\r\\n\\t.,;:\\\'\\\"()?!\' -max 2 -min 1\" -stemmer weka.core.stemmers.NullStemmer";
        //nb.setOptions(Utils.splitOptions(opt));  
	    String outputModel;
	  Classifier tester=new SGDText(); 
//		tester.
//		tester.setStemmer(new LovinsStemmer());
	//*	tester.setTokenizer(getTokenize());
	//*	tester.setMinWordFrequency(1);
//		tester.setUseWordFrequencies(true);
//		tester.setNormalizeDocLength(true);
		try {
//			ArrayList<Integer> p=getList(train.numInstances(),100);
			tester.buildClassifier(new Instances(train));
			/*for (int i=0; i<100; i++) {
	            Instance instance = train.instance(i);
	            //train.delete(i);
	            if(i%1000==0)System.out.println("get data:"+i);
	            tester.updateClassifier(instance);
	        }*/

			Eval2(tester,train);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    

		try {
//			train = readArff("D:\\Documents\\test1000rand.arff"); "D:\\Documents\\testall.arff"
			test = readArff("D:\\Documents\\test10000rand.arff");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return;
		}
		
	}
	 private static ArrayList getList(int end,int size){
		 
		 ArrayList<Integer> arr= new ArrayList();
		 Random r=new Random(1);

		 while(arr.size()<size){
		   int p=(int)Math.floor((double)r.nextFloat()*end);
		   if(!arr.contains(p)){
			   arr.add(p);
		   }
		 }
		 return arr;
	 }
	 private static void Eval(Classifier tester,Instances train) throws Exception{
		 Evaluation eval = new Evaluation(train);                                           
	        eval.evaluateModel(tester, train);
	        printInfo(eval);
	 }
	 
	 private static void Eval2(Classifier tester,Instances train) throws Exception{
		 Evaluation eval = new Evaluation(train);                                           
		 eval.crossValidateModel(tester, train,10,new Random(1)); 
		 printInfo(eval);
		 
	 }
	 private static void printInfo(Evaluation eval) throws Exception{
		 System.out.println(eval.toSummaryString());                                        
	        System.out.println(eval.toClassDetailsString());                                   
	        System.out.println(eval.toMatrixString()); 
		 
	 }
	private static Tokenizer getTokenize(){
		NGramTokenizer t=new NGramTokenizer();
		t.setNGramMaxSize(1);
		t.setNGramMinSize(1);
		t.setDelimiters("\\s\\r\\n\\t");
		return t;
	}
	private static Instances readArff(String path) throws IOException{
		 BufferedReader reader = new BufferedReader(
        new FileReader(path));
		Instances data = new Instances(reader);
		reader.close();
		return data;
	}
}
