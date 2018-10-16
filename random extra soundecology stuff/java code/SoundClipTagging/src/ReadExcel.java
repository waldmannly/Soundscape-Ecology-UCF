import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.JFileChooser;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFChart;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcel {

    /**
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
    	
		String pathOfFolder= "C:/Users/evan/Desktop/dataNDSINORMrunFIXED - Copy.csv"; 
		String pathOfTextFile=null;
		File folder=null;
//		JFileChooser chooser = new JFileChooser();
//		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
//		int returnVal = chooser.showOpenDialog(null);
//		if(returnVal == JFileChooser.APPROVE_OPTION) 
//		{
//			//stores path 
//			pathOfFolder = chooser.getSelectedFile().getAbsolutePath();
//
//		
//		}
//    	
        File excel =  new File(pathOfFolder);
        FileInputStream fis = new FileInputStream(excel);
//        XSSFWorkbook wb = new XSSFWorkbook(fis);
//        XSSFSheet ws = wb.getSheetAt(0);

//        int rowNum = ws.getLastRowNum() + 1;
//        int colNum = ws.getRow(0).getLastCellNum();
        
        BufferedReader in = new BufferedReader(new FileReader(excel)); 
      
        ArrayList<String[]> data = new ArrayList<String[]>();
        String input = in.readLine();
        String[] row = input.split(",");
        SSEDataHandler ssedh = new SSEDataHandler(); 
        ssedh.setRules(row);
        System.out.println(row.length); 
        int count =0; 
        while (1==1)
        {
        	
//        	StringTokenizer tokenizer = new StringTokenizer(input); 
//        	  for(int i = 0; i <15; i++)
//              {
//        		  row[i] = tokenizer.nextToken(); 
//              }
//        	System.out.println (row[0]+" " + count++);
        	data.add(row); 
        	input = in.readLine();
        	if (input==null)
        		break; 
        	row = input.split(","); 
        }
        	System.out.println(data.size());
//        for(int i = 0; i <rowNum; i++)
//        {
////            XSSFRow row = ws.getRow(i);
//            
//                for (int j = 0; j < colNum; j++){
//                    XSSFCell cell = row.getCell(j);
//                    String value = cell.toString();
//                    data[i][j] = value;
//                    System.out.print (" " + value);
//                }
//                System.out.println();
//        }
        
//      wb.close(); 
        count =0; 
        for (String[] row1: data){
        	for (String value: row1)
        		System.out.print (", " + value);
        	System.out.println(count++);
        }
        
        //return data; 
   
        
    }
}

 