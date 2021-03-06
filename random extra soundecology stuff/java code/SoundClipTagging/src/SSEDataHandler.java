import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class SSEDataHandler {

	
	//take in the path to soundscape data and or the wav files path
	public static String[] rules; 
	
	public static void setRules(String[] r){
		rules = r; 
	}
	public static int getIndex()
	{
		if (rules == null)
			return -1; 
		
		
		
		return 0;  
	}
	
	public ArrayList<String[]> getDataList(String path) throws IOException{
		String pathOfFolder= "C:/Users/evan/Desktop/dataNDSINORMrunFIXED - Copy.csv"; 
		pathOfFolder = "../../dataNDSIrunFIXED.csv";
//		JFileChooser chooser = new JFileChooser();
//		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
//		int returnVal = chooser.showOpenDialog(null);
//		if(returnVal == JFileChooser.APPROVE_OPTION) 
//		{
//			//stores path 
//			pathOfFolder = chooser.getSelectedFile().getAbsolutePath();
//		}
//    	
        File excel =  new File(pathOfFolder);
        FileInputStream fis = new FileInputStream(excel);
        
        BufferedReader in = new BufferedReader(new FileReader(excel)); 
      
        ArrayList<String[]> data = new ArrayList<String[]>();
        String input = in.readLine();
        String[] row = input.split(",");
        setRules(row);
        System.out.println(row.length); 
        int count =0; 
        while (true)
        {
        	data.add(row); 
        	input = in.readLine();
        	if (input==null)
        		break; 
        	row = input.split(","); 
        }
        	System.out.println(data.size());
        

        for (String[] row1: data){
        	for (String value: row1)
        		value =  value.trim();
        }
        
        return data; 
	}
}



// A = filename 
//D - duration
//F= index
//L - left channel 
//M - right channel 
//N - from
//O - to 