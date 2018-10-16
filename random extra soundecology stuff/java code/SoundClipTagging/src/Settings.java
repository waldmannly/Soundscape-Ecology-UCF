import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class Settings extends JFrame {


		//Final Variables 
		public static final int FRAME_WIDTH = 500; 
		public static final int FRAME_HIEGHT = 400; 
		
		//GUI variables
		public JFrame settingsFrame; 
		private JButton applyButton; 
		private JButton extraButton; 
		private JButton dataPathChooserButton;
		private JButton settingsButton;
		private ActionListener listener; 
		private JComboBox numberPanel;
		private FlowLayout flow = new FlowLayout(FlowLayout.CENTER);
		private JTextField siteOneField; 
		private JTextField siteTwoField; 
		private JTextField siteThreeField; 
		private JTextField recorderOneField; 
		private JTextField recorderTwoField; 
		private JTextField recorderThreeField; 
		private JCheckBox wordTaggingCheck; 
		private JCheckBox loggingCheck; 
		private JCheckBox extraCheck;
		private JLabel pathDataLabel; 


		public Settings()
		{
			this.setTitle("Settings"); 
			
			Container con = getContentPane();
			con.setLayout(flow);

			class ChoiceListener implements ActionListener, KeyListener
			{
				public void actionPerformed(ActionEvent event) 
				{
					try {
						try {
							selectTopic(event);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				@Override
				public void keyPressed(KeyEvent arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void keyReleased(KeyEvent arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void keyTyped(KeyEvent arg0) {
					// TODO Auto-generated method stub
					
				}
			}
			listener = new ChoiceListener(); 
			createControlPanel(); 
			setSize(FRAME_WIDTH, FRAME_HIEGHT); 
	

		}

		public void createControlPanel()
		{
		
			JPanel confirmationPanel = createButtons(); 
//			JPanel addPanel = createAddPanel(); 
//			add(addPanel); 
			createPathPanel();
			
			pathDataLabel = new JLabel("Path to data file: Path data label"); 
			add(pathDataLabel); 
			
			add(confirmationPanel);
			
			
			loggingCheck = new JCheckBox("Enable logging", true);
			wordTaggingCheck = new JCheckBox("Enable word tagging", true);
			extraCheck = new JCheckBox("Enable extra", true);
			JPanel checkPanel = new JPanel(); 
			checkPanel.add(loggingCheck); 
			checkPanel.add(wordTaggingCheck);
			checkPanel.add(extraCheck); 
			add(checkPanel); 
			
			add(applyButton); 
			
		}


		public void createPathPanel()
		{
			int width = 15; 
			
			JPanel siteOnePanel = new JPanel(); 
			JPanel siteTwoPanel = new JPanel(); 
			JPanel siteThreePanel = new JPanel(); 
			
			recorderOneField = new JTextField("recorder one"); 
			recorderOneField.setColumns(width);
			JLabel label1 = new JLabel("==>");
			siteOneField = new JTextField("path one"); 
			siteOneField.setColumns(width);
			siteOnePanel.add(recorderOneField); 
			siteOnePanel.add(label1); 
			siteOnePanel.add(siteOneField);
			
			recorderTwoField = new JTextField("recorder two");
			recorderTwoField.setColumns(width);
			JLabel label2 = new JLabel("==>");
			siteTwoField = new JTextField("path two"); 
			siteTwoField.setColumns(width);
			siteTwoPanel.add(recorderTwoField); 
			siteTwoPanel.add(label2); 
			siteTwoPanel.add(siteTwoField);
			
			recorderThreeField = new JTextField("recorder three"); 
			recorderThreeField.setColumns(width);
			JLabel label3 = new JLabel("==>");
			siteThreeField = new JTextField("path three");
			siteThreeField.setColumns(width);
			siteThreePanel.add(recorderThreeField);
			siteThreePanel.add(label3); 
			siteThreePanel.add(siteThreeField);
			
			add(siteOnePanel); 
			add(siteTwoPanel); 
			add(siteThreePanel); 
		
		}
		
		public JPanel createButtons()
		{
			applyButton = new JButton("Apply and Close"); 
			applyButton.addActionListener(listener); 

			dataPathChooserButton = new JButton("Choose new data file"); 
			dataPathChooserButton.addActionListener(listener);

			extraButton = new JButton("Extra"); 
			extraButton.addActionListener(listener); 

			ButtonGroup group = new ButtonGroup(); 
			group.add(extraButton); 
			group.add(dataPathChooserButton);

			JPanel panel = new JPanel(); 
			panel.add(extraButton); 
			panel.add(dataPathChooserButton);
			

			return panel; 
		}

		public void selectTopic(ActionEvent event) throws IOException, InterruptedException
		{
			Object source = event.getSource();

			if (source instanceof JButton)
			{
	
				if (source == extraButton)
				{ 
				    
				}
				else if(source == dataPathChooserButton)
				{
					//save and system.exit(0)
				
				}
				else if(source == applyButton)
				{
					//save changes and make frame invisible
//					save changes to a text file so that you can read them in on start up
					this.setVisible(false);
				
				}
				
			}

			
		}
	
}
