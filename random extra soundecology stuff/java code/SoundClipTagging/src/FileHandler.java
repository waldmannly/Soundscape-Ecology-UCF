
public class FileHandler {

	// make sure you can access all files and and check paths
	// this should contain the in between for the sse data files
	// and the wav player reading 
	
	String pathToSEEData= null;
	String pathToDataFiles = null;
	
	public FileHandler(){
		
	}
	
	public static String convertToPath(String name)
	{
		//read csv file of file names and stuff. 
		
		if (name.contains("S4A01979"))
			name ="/Volumes/D/Station1 Arboretum/" + name; // "D://Station1 Arboretum/" + name;
		else if  (name.contains("S4A04386"))
			name ="/Volumes/D/Station3 Union/" + name; //"D://Station3 Union/" + name;
		else if  (name.contains("S4A04376"))
			name = "/Volumes/D/Station2 Solar Farm/" + name;	 //"D://Station2 Solar Farm/" + name;	
		else 
			System.out.println("error"); 
		 
//			if (name.contains("S4A01979"))
//				name = "D://Station1 Arboretum/" + name;
//			else if  (name.contains("S4A04386"))
//				name = "D://Station3 Union/" + name;
//			else if  (name.contains("S4A04376"))
//				name = "D://Station2 Solar Farm/" + name;	
//			else 
//				System.out.println("error"); 
		return name; 
	}
	//convert file names to paths
	public String[] convertToPath()
	{
		if (pathToSEEData == null)
			return null; 
		
		//read csv file of file names and stuff. 
		String[] fileNames= null; 
		int error =0; 
		for (String name : fileNames)
		{
			if (name.contains("S4A01979"))
				name ="/Volumes/D/Station1 Arboretum/" + name; // "D://Station1 Arboretum/" + name;
			else if  (name.contains("S4A04386"))
				name ="/Volumes/D/Station3 Union/" + name; //"D://Station3 Union/" + name;
			else if  (name.contains("S4A04376"))
				name = "/Volumes/D/Station2 Solar Farm/" + name;	 //"D://Station2 Solar Farm/" + name;	
			else 
				error++; 
		}
		System.out.println("errors in convert: " +error);
		return fileNames; 
	}
}


//#reading in the file paths from a csv 
//fileTable <- read.csv(filesToAnalyze, header=T)
//
//
//wave_FilesArr<-oldData$FILENAME
//directoryArr<- rep("default", length(wave_FilesArr))
//for (i in 1:length(wave_FilesArr)) 
//{
//    if (grepl("S4A01979", wave_FilesArr[i], fixed=TRUE) )
//    {
//      directoryArr[i] = paste("D://Station1 Arboretum/" , wave_FilesArr[i] ,sep="")
//    }
//    else if (grepl("S4A04386", wave_FilesArr[i], fixed=TRUE))
//    {
//      directoryArr[i] = paste("D://Station3 Union/" , wave_FilesArr[i] ,sep="")
//    }
//    else if (grepl("S4A04376", wave_FilesArr[i], fixed=TRUE))
//    {
//      directoryArr[i] = paste("D://Station2 Solar Farm/" , wave_FilesArr[i] ,sep="")
//    }
//}