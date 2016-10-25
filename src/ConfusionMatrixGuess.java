import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;


public class ConfusionMatrixGuess implements IGuess {
	private Map<String,String> hash ;
	
	public ConfusionMatrixGuess(String path) throws IOException{
		int[][] confusionMatrix =new int[30][];
		
		FileInputStream fstream = new FileInputStream(path);
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
		String strLine;
		int i=0,j=0;
		while ((strLine = br.readLine()) != null)   {
			confusionMatrix[i]=new int[30];
			String[] items=strLine.split(",");
			j=0;
			for(String x:items){
				confusionMatrix[i][j]=Integer.parseInt(x.trim());
				j++;
			}
			
			//System.out.println (strLine);
			i++;
		}
		br.close();
		hash=generateMap(confusionMatrix);
	}
	private static int Width=30;
	private static int Height=30;
	public HashMap<String,String> generateMap(int[][] confusionMatrix){
		HashMap<String,String> h=new HashMap<String,String>();
		int index=0;
		for(int z=0;z<Width;z++){
			int k1=0,k2=0;
			int index1=-1,index2=-1;
			for(int x=0;x<Height;x++){				
				if(x!=index && (confusionMatrix[x][z]>k1 || confusionMatrix[x][z]>k2)){
					if(k1>=k2){
						k2=confusionMatrix[x][z];
						index2=x;
					}
					else if(k2>k1){
						k1=confusionMatrix[x][z];
						index1=x;
					}
				}
			}
			h.put(""+index, index1+" "+index2);//[""+index]
			index++;
		}
		return h;
	}
	@Override
	public String guess(String classname) {
		return classname+" "+hash.get(classname);
	}

}
