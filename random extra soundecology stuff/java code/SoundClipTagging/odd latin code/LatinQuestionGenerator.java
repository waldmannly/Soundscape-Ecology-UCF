import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.imageio.ImageIO;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class LatinQuestionGenerator 
{
	public static void main(String[] args)throws Exception
	{
		//Background 
		ToolbarFrame background = new ToolbarFrame(); 
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		background.setSize(dim.width, dim.height);
		BackgroundPanel contentPane = new BackgroundPanel(ImageIO.read(new File("landscape.jpg")),BackgroundPanel.SCALED, 0.0f, 0.0f);
		contentPane.setLayout(new BorderLayout());
		background.setContentPane(contentPane);
		background.setVisible(true); 
		
		//checks to see if there is a sound card/mixer
		Sound sound = new Sound(); 
		sound.testSpeakers(); 
	
		//Start of Program 
		JFrame instructionPanel = new instructionPanelGUI(); 
		instructionPanel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		instructionPanel.setVisible(true); 
		

	
		/***********************************************************************************
		 * NOTES: 																																				 *															 *
		 *  - RATING SCALE BASED ON TEST AND PERCENT (10 DIFFERENT RANKS) 	
		 *  - more correct sounds 					 															 *																			 *
		 ***********************************************************************************/
		
	}
}
