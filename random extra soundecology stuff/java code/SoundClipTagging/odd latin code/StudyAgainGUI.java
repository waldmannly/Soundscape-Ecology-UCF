import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;


public class StudyAgainGUI extends JFrame
{
	public static final int FRAME_WIDTH = 250; 
	public static final int FRAME_HIEGHT = 125; 
	private JButton yesButton; 
	private JButton noButton;
	private JButton leaveButton;
	private JLabel directions;
	private JPanel buttonPanel; 
	private JPanel totalPanel; 
	private ActionListener listener;
	private FlowLayout flow = new FlowLayout(FlowLayout.CENTER);
	private QuestionGUI questioner; 
	private Sound sound = new Sound(); 
	private Timer timer;
	
	public StudyAgainGUI(QuestionGUI questioner1)
	{
		questioner = questioner1;
		
		this.setTitle("Study Again");
		this.setAlwaysOnTop(true);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(( dim.width- FRAME_WIDTH)/2 , (dim.height- FRAME_HIEGHT)/2);
	
		Container con = getContentPane();
		con.setLayout(flow);
		directions = new JLabel("Do you want to study this test again?"); 
		add(directions);
		
		class ChoiceListener implements ActionListener
		{
			public void actionPerformed(ActionEvent event)
			{
				Object source = event.getSource();
		
				if (source instanceof JButton && source == yesButton && directions.getText().equals("Do you want to study this test again?"))
				{
					try 
					{
						questioner.studyAgain(); 
						setFalse(); 
					}
					catch (IOException e) 
					{
						e.printStackTrace();
					}
				}
				else if(source instanceof JButton && source == yesButton && directions.getText().equals("Do you want to study a different test?"))
					restartProgram();
				else if(source instanceof JButton && source == noButton && directions.getText().equals("Do you want to study this test again?"))
					differentTest(); 
				else if(source instanceof JButton && source == noButton && directions.getText().equals("Do you want to study a different test?"))
					exit(); 
				else if(source instanceof JButton && source == leaveButton )
				{
					timer.start();
					sound.play("button-4.wav"); 
				}
			
			else if (source instanceof Timer)
					System.exit(0);
			}
		}
		
		listener = new ChoiceListener(); 
		timer = new Timer(500, listener); 
		createButtons(); 
		setSize(FRAME_WIDTH, FRAME_HIEGHT); 
	}
	
	public void createButtons()
	{
		yesButton = new JButton("Yes"); 
		yesButton.addActionListener(listener); 
		
		noButton = new JButton("No"); 
		noButton.addActionListener(listener); 
		
		leaveButton = new JButton("Exit"); 
		leaveButton.addActionListener(listener); 
		
		ButtonGroup group = new ButtonGroup(); 
		group.add(yesButton);
		group.add(noButton); 
		
		buttonPanel = new JPanel(); 
		//buttonPanel.setPreferredSize(new Dimension(FRAME_WIDTH/2 ,65));
		buttonPanel.add(yesButton); 
		buttonPanel.add(noButton); 
		buttonPanel.setBorder(new EtchedBorder()); 
		add(buttonPanel);  
	}
	
	public void differentTest()
	{
		directions.setText("Do you want to study a different test?");
	}
	
	public void exit()
	{
		directions.setText("The program is closing. Goodbye");
		buttonPanel.setVisible(false); 
		JPanel panel = new JPanel(); 
		panel.setBorder(new EtchedBorder());
		panel.add(leaveButton);
		add(panel); 
		leaveButton.setVisible(true);
	}
	
	public void restartProgram()
	{
		questioner.restart(); 
	}
	
	public void setFalse()
	{
		this.setVisible(false); 
	}
	public void resetNames()
	{
		directions.setText("Do you want to study this test again?"); 
	}
}
