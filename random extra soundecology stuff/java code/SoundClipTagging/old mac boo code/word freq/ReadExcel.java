import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

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
    	
		String pathOfFolder=null; 
		String pathOfTextFile=null;
		File folder=null;
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int returnVal = chooser.showOpenDialog(null);
		if(returnVal == JFileChooser.APPROVE_OPTION) 
		{
			//stores path 
			pathOfFolder = chooser.getSelectedFile().getAbsolutePath();

		
		}
    	
        File excel =  new File (pathOfFolder);
        FileInputStream fis = new FileInputStream(excel);
        XSSFWorkbook wb = new XSSFWorkbook(fis);
        XSSFSheet ws = wb.getSheet("Sheet1");

        int rowNum = ws.getLastRowNum() + 1;
        int colNum = ws.getRow(0).getLastCellNum();
        String [][] data = new String [rowNum] [colNum];

        for(int i = 0; i <rowNum; i++)
        {
            XSSFRow row = ws.getRow(i);
            
                for (int j = 0; j < colNum; j++){
                    XSSFCell cell = row.getCell(j);
                    String value = cell.toString();
                    data[i][j] = value;
                    System.out.print (" " + value);
                }
                System.out.println();
        }
        
      

        
        
   
        
    }
}

 