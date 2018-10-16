import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Sound {
  private Clip clip; 
  
  //add extra sounds appropriately 
  private String[] correctSounds= {"applause-8.wav"};
  private String[] incorrectSounds = {"fart-1.wav","fart-3.wav","fart-4.wav","fart-6.wav","fart-7.wav","fart-9.wav","fart-8.wav","fart-12.wav","fart-11.wav","fart-13.wav","fart-14.wav","fart-15.wav","fart-16.wav","fart-18.wav"};
  
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
		
		public void playCorrect()
		{
			int index =(int) (Math.random() * correctSounds.length);
			play(correctSounds[index]);
		}
		
		public void playIncorrect()
		{
			int index =(int) (Math.random() * incorrectSounds.length);
			play(incorrectSounds[index]);
		}
		
    public void play(String filename)
    {
        try
        {
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File(filename)));
            clip.start();
        }
        catch (Exception exc)
        {
            //exc.printStackTrace(System.out);
        }
    }
   
    public void end()
    {
    	try 
    	{
    		clip.close(); 
    	}
    	catch (Exception e)
    	{
    		//this will throw and exception if there is no speakers  or if there is a null pointer 
    	}
    }
   
}