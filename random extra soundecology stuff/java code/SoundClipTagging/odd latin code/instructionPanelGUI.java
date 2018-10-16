import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class instructionPanelGUI extends JFrame
{
	public static final int FRAME_WIDTH = 400; 
	public static final int FRAME_HIEGHT = 457; 
	private ActionListener listener; 
	private FlowLayout flow = new FlowLayout(FlowLayout.CENTER);
	private JButton confirmButton; 
	private JButton exitButton; 
	private JButton exitButton1; 
	private JTextArea instructions; 
	private Timer timer;
	private JScrollPane instructionsScrollPane;
	private Sound sound = new Sound();
	private JPanel buttonPanel; 
		
	public instructionPanelGUI()
	{
		this.setTitle("Latin Question Generator Menu"); 
		
		this.setAlwaysOnTop(true);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(( dim.width- FRAME_WIDTH)/2 , (dim.height- FRAME_HIEGHT)/2);

		Container con = getContentPane();
		con.setLayout(flow); 

		class ChoiceListener implements ActionListener
		{
			public void actionPerformed(ActionEvent event) 
			{
				Object source = event.getSource(); 
				
				if (source instanceof JButton && source == confirmButton)
				{
					JFrame topicSelecter = new TopicSelecterGUI(); 
					topicSelecter.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					topicSelecter.setVisible(true); 
					setVisibleFalse();
				}
				else if(source instanceof JButton && source == exitButton)
				{
					timer.start();
					sound.play("button-4.wav"); 
				}
				else if (source instanceof JButton && source == exitButton1)
				{
					setVisibleFalse();
				}
				else if (source instanceof Timer)
					System.exit(0);
			}
		}

		listener = new ChoiceListener(); 
		timer = new Timer(500, listener); 
		createControlPanel(); 
		setSize(FRAME_WIDTH, FRAME_HIEGHT); 
	}
	
	public void createControlPanel()
	{
		//instructions for the program
		instructions = new JTextArea("Welcome to the Latin Question Generator. The goal of this program is to teach mastery of at least one of the topics below. \n\t-- Customs \n\t-- Greek Literature \n\t-- Helenic History \n\t-- History of the Empire \n\nThis program is designed to allow students or teachers to produce customizable tests. The user can customize the test created by selecting from four different academic topics and three different academic tests. Tests can be modified so they are randomized, vary in length, and the questions can be mixed between tests (and even topics) to allow for variety.  \n\nThis program includes \n\t-- 4 topics \n\t-- 3 tests for each topic \n\t-- 600 questions \n\t-- 17 different sounds \n\t-- more than 2,350 lines of code \n\t-- an infinite number of customizable \t\t     test questions \n\t-- and much more. \n\nCREDITS: \n \t-- Background picture by Allison \t\t     Waldmann \n\t-- Sounds by freeloops.com,           \t\t     SoundBible.com, and SoundJay.com \n\t-- BackGroundPanel Code by Rob Camick\n\t-- Questions and Answers by FJCL.org \n\t-- Complier used: Eclipse \n\nPOST SCRIPTUM: Instructions can be found throughout the program by clicking on the Help tab then Instructions, in the upper left-hand corner "); 		
		instructions.setLineWrap(true); 
		instructions.setWrapStyleWord(true);
		instructions.setColumns(30);
		instructions.setRows(22); 
		instructions.setEditable(false);
		instructionsScrollPane = new JScrollPane(instructions, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		confirmButton = new JButton("Continue"); 
		confirmButton.addActionListener(listener);
		
		exitButton = new JButton("Exit"); 
		exitButton.addActionListener(listener);
		
		buttonPanel = new JPanel(); 
		buttonPanel.add(confirmButton);
		buttonPanel.add(exitButton);
		buttonPanel.setBorder(new TitledBorder(new EtchedBorder(), "Press continue")); 
		
		add(instructionsScrollPane);
		add(buttonPanel);

	}
	
	public void changeInstructionsText(JTextArea text)
	{
		buttonPanel.setVisible(false); 
		instructionsScrollPane.setVisible(false);
		
		this.setTitle("Instructions");
		instructions= text;

		instructionsScrollPane = new JScrollPane(instructions, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	
		exitButton1 = new JButton("Exit"); 
		exitButton1.addActionListener(listener);
		
		buttonPanel = new JPanel(); 
		buttonPanel.add(exitButton1);
		buttonPanel.setBorder(new TitledBorder(new EtchedBorder(), "Press exit")); 
		
		add(instructionsScrollPane);
		add(buttonPanel);
	}

	public void setVisibleFalse()
	{
		this.setVisible(false); 
	}
}
