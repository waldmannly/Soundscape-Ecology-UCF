import java.io.File;
import java.io.IOException;
import java.nio.file.Files;


public class WavWriter {

	
//		write the chosen wav files to a chosen directory file
	
	public void testCopy(String path) throws IOException
	{
		copyFile(new File(path), new File(path.substring(0,path.length()-4)+" java copy " + path.substring(path.length()-4))); 
	}
	
	public static void copyFile( File from, File to ) throws IOException {
		System.out.println("copy from: " +from.toPath()+" to: "+to.toPath()); 
	    Files.copy( from.toPath(), to.toPath() );
	}
}
