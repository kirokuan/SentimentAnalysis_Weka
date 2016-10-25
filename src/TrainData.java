import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import org.tartarus.snowball.ext.porterStemmer;

import ptstemmer.implementations.PorterStemmer;
import weka.attributeSelection.ASEvaluation;
import weka.attributeSelection.ASSearch;
import weka.attributeSelection.InfoGainAttributeEval;
import weka.attributeSelection.Ranker;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.bayes.NaiveBayesMultinomialText;
import weka.classifiers.bayes.NaiveBayesMultinomialUpdateable;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.functions.NeuralNetwork;
import weka.classifiers.functions.SGDText;
import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.RandomForest;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.core.stemmers.IteratedLovinsStemmer;
import weka.core.stemmers.LovinsStemmer;
import weka.core.stemmers.PTStemmer;
import weka.core.stemmers.SnowballStemmer;
import weka.core.stopwords.Rainbow;
import weka.core.tokenizers.NGramTokenizer;
import weka.core.tokenizers.Tokenizer;
import weka.filters.Filter;
import weka.filters.MultiFilter;
import weka.filters.SupervisedFilter;
import weka.filters.supervised.attribute.AttributeSelection;
import weka.filters.unsupervised.attribute.StringToWordVector;


public class TrainData {
	 public static void main(String[] args) throws Exception {
		
		 String source;
		 if(args.length>0)	
		 source=args[0];
		 else source="D:\\Documents\\test10000rand.arff";
		    Instances structure = Util.readArff(source);//"D:\\Documents\\test1000rand.arff"
		    FilteredClassifier f = new FilteredClassifier();
		    /*Filter[] filters= new Filter[2];
		    MultiFilter mfilters = new MultiFilter();
		    Filter filter2 = new AttributeSelection();
		    
		    ASEvaluation attEvaluator = new InfoGainAttributeEval();
		    ((AttributeSelection) filter2).setEvaluator(attEvaluator);
		    Ranker ranker = new Ranker();
		    //ranker.setThreshold(0); //<0 ignored
		    ASSearch asSearch = ranker;
		    ((AttributeSelection) filter2).setSearch(asSearch);
		    filters[1] = filter2; 
       */
		    StringToWordVector s = GetWord2Vec();
		    /*filters[0]=s;
		    mfilters.setFilters(filters);
		    f.setFilter(mfilters);*/
		    try {
		    	f.setFilter(s);
		    	structure.setClassIndex(1);
				s.setInputFormat(structure);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 /*   MultilayerPerceptron mlp = new MultilayerPerceptron();
		  //Setting Parameters
		  mlp.setLearningRate(0.1);
		  mlp.setMomentum(0.2);
		  mlp.setTrainingTime(2000);
		  mlp.setHiddenLayers("3");
	
		*/

		    Classifier[] classifiers=new Classifier[]{
		    	//	new NaiveBayesMultinomialUpdateable(),
		    		//new RigdeClassifier()
//		    		mlp,
		    		new NaiveBayes()//   	sgd
		   // 		new NaiveBayes(),
		   // 		new RandomForest(),
		    	//new SGDText()

		    };
		    String outputPath=(args.length>0)?args[1]:source+".model";
		    for(Classifier c:classifiers){
		    	 ClasssifyBuild(f,c,structure,outputPath);
		    	
		    }

		  
	 }
	 private static Classifier ClasssifyBuild(FilteredClassifier f,Classifier c,Instances i,String filename) throws Exception{
		 f.setClassifier(c);
		 f.buildClassifier(i);
		 weka.core.SerializationHelper.write(filename,f);
		 System.out.println("build done"); 
		 Eval2(f,i);
		// doTest(f,filename,c.getClass().getSimpleName());
		 return c;
	 }

	 private static StringToWordVector GetWord2Vec(){
		 StringToWordVector s = new StringToWordVector();
		    s.setLowerCaseTokens(true);
		    s.setTokenizer( getTokenize());
		    s.setStopwordsHandler(new Rainbow());
		    s.setStemmer(new IteratedLovinsStemmer());
//		    s.setTFTransform(true);
//		    s.setIDFTransform(true);
//		    s.setDoNotOperateOnPerClassBasis(false);  
//		    s.setOutputWordCounts(true);
		    s.setWordsToKeep(100000);
		 return s;
	 }

	 private static Tokenizer getTokenize(){
			NGramTokenizer t=new NGramTokenizer();
			t.setNGramMaxSize(1);
			t.setNGramMinSize(1);
			t.setDelimiters("\\s\\r\\n\\t");
		//	t.setDelimiters("\\W");
			return t;
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
}
