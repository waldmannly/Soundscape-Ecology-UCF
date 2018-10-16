import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.JOptionPane;

public class WavPlayer {
	private Clip clip; 
	private Clip loadedClip; 
	private Clip loadingClip;
	
	public WavPlayer() throws LineUnavailableException{
		loadingClip = AudioSystem.getClip(); 
		loadedClip = AudioSystem.getClip(); 
	}
	
	public void testSpeakers()
	{
		String filename = "startup.wav";
		try
		{

			clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(new File(filename)));
			clip.start();
		}
		catch (Exception exc)
		{
			JOptionPane.showMessageDialog(null, "For the best experience, we recommend that you use speakers or use headphones.", "Warning", 0, null);
			//	System.out.println("Plug in speakers or headphones. ");
			//exc.printStackTrace(System.out);
		}
	}

	public void load(String filename, int secondStart)
	{
		try
		{
			try{
			loadingClip.open(AudioSystem.getAudioInputStream(new File(filename))); 
			} 
			catch (Exception exc){
				System.out.println(exc);
				loadingClip.flush();
				loadingClip.open(AudioSystem.getAudioInputStream(new File(filename))); 
			}
			loadingClip.setMicrosecondPosition(secondStart * 1000000);
		
		}
		catch (Exception exc)
		{
			//exc.printStackTrace(System.out);
		}
	}
	
	public void resumeLoad(int secondResume)
	{
		loadingClip.setMicrosecondPosition(secondResume * 1000000);
		//System.out.println(loadingClip.toString());
	}
	
	public void playLoaded()
	{
		Clip temp = loadedClip; 
		loadedClip = loadingClip; 
		loadedClip.start();
		loadingClip = temp; 
	}
	
	
	
//	public void play(String filename, int secondStart)
//	{
//		try
//		{
//			clip = AudioSystem.getClip();
//			clip.open(AudioSystem.getAudioInputStream(new File(filename))); 
//			clip.setMicrosecondPosition(secondStart * 1000000);
//			clip.start();
//		
//		}
//		catch (Exception exc)
//		{
//			//exc.printStackTrace(System.out);
//		}
//	}

	public void end()
	{
		try 
		{
			loadedClip.close(); 
			loadedClip.flush();
		}
		catch (Exception e)
		{
			//this will throw and exception if there is no speakers  or if there is a null pointer 
		}
	}

}



