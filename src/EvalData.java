import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import weka.classifiers.Classifier;
import weka.classifiers.meta.FilteredClassifier;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;


public class EvalData {
	 FastVector fvWekaAttributes;
	 Instances TestSet;
	 static Instances header;
	public Classifier classifier;
	public IGuess guessHelper;
	public EvalData(Classifier _classifier){
		 Attribute content = new Attribute("text", (FastVector)null);
		
		 FastVector allVal = new FastVector(30);
		 for(int i=0;i<30;i++){
			 allVal.addElement(""+i);
		 }
		 Attribute mainClass = new Attribute("@@class@@",allVal);
		 fvWekaAttributes = new FastVector(2);
		 fvWekaAttributes.addElement(content);
		 fvWekaAttributes.addElement(mainClass);
		 TestSet = new Instances("Rel", fvWekaAttributes, 10);
		 TestSet.setClassIndex(1);
		 classifier=_classifier;
		 try {
			header =  Util.readArff("D:\\Documents\\header.arff");
			header.setClassIndex(1);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) throws Exception {
		String modelPath="D:\\documents\\test-rel10000-striptags2.arff.model";
		FilteredClassifier classifier =(FilteredClassifier)weka.core.SerializationHelper.read(modelPath);
		
		EvalData e=new EvalData(classifier);
		e.guessHelper=new ConfusionMatrixGuess("D:\\Documents\\confusion.csv");
		e.ClassifyFiles("D:\\Downloads\\test\\");
	}
	public void ClassifyFiles(String foldername) throws Exception{
    	
		File folder = new File(foldername);
		File[] listOfFiles = folder.listFiles();
		StringBuilder x=new StringBuilder("Id,predictions\n");
		for (File file : listOfFiles) {
		    if (file.isFile()) {
				Instance i=BuildInstance(Util.readFile(file.getPath()));
				double clsLabel = classifier.classifyInstance(i);
				String classes=guessHelper.guess(""+((int)clsLabel));
				//String classname=header.classAttribute().value((int) clsLabel);
				StringBuilder e=new StringBuilder();
				for(String n:classes.split("\\s+")){
					e.append(" "+name(Integer.parseInt(n)));
				}
				String result=e.toString().trim();
				String id=file.getName().split("\\.")[0];
				x.append(id+","+result+"\n");
				System.out.println(id+","+result);
		    }
		}
		Util.Outfile(x.toString(),"D:\\Documents\\test-rel10000-striptags2.arff.model-result.csv");
	}
	private String name(int index){
		return header.classAttribute().value(index);
	}
	 public Instance BuildInstance(String text){
		  // Create instance of length two.
		    Instance instance = new DenseInstance(2);

		    // Set value for message attribute
		    Attribute messageAtt = TestSet.attribute("text");
		    instance.setValue(messageAtt, messageAtt.addStringValue(text));

		    // Give instance access to attribute information from the dataset.
		    instance.setDataset(TestSet);
		    return instance;
	 }
}
