import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;


public class QuestionSelecterGUI extends JFrame
{
	//Variables 
	public static final int FRAME_WIDTH = 300; 
	public static final int FRAME_HIEGHT = 250; 
	private JButton exitButton; 
	private JButton confirmButton; 
	private JLabel directions;
	private JComboBox numberPanel; 
	private ActionListener listener; 
	private FlowLayout flow = new FlowLayout(FlowLayout.CENTER);
	private TestSelecterGUI testSelecter; 
	private TopicSelecterGUI topicSelecter; 
	private JRadioButton randomButton;
	private JRadioButton notRandomButton; 
	private int numberOfQuestions; 
	private String topicName; 
	private String testName; 
	private QuestionGUI questioner; 
	private boolean randomized; 
	private Sound sound = new Sound(); 
	private Timer timer;
	
	public QuestionSelecterGUI(String topicName1, String testName1, TopicSelecterGUI topicSelecter1, TestSelecterGUI testSelecter1)
	{
		this.setTitle("Question Selecter"); 
		this.setAlwaysOnTop(true);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(( dim.width- FRAME_WIDTH)/2 , (dim.height- FRAME_HIEGHT)/2);
		
		Container con = getContentPane();
		con.setLayout(flow);
		
		topicName = topicName1; 
		testName = testName1; 
		topicSelecter = topicSelecter1;
		testSelecter = testSelecter1; 
		
		directions = new JLabel("Select the number of questions you would like."); 
		add(directions); 
		
		class ChoiceListener implements ActionListener
		{
			public void actionPerformed(ActionEvent event) 
			{
				try {
					selectTopic(event);
				}
				catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		listener = new ChoiceListener(); 
		timer = new Timer(500, listener); 
		createControlPanel(); 
		setSize(FRAME_WIDTH, FRAME_HIEGHT); 
	}
	
	public void createControlPanel()
	{
		JPanel numberPanel = createComboBox(); 
		JPanel randomPanel = createRadioButtons();
		JPanel confirmationPanel = createButtons(); 
		
		JPanel controlPanel = new JPanel(); 
		controlPanel.setLayout(new GridLayout(3,1)); 
		controlPanel.add(numberPanel); 
		controlPanel.add(randomPanel); 
		controlPanel.add(confirmationPanel); 
		
		add(controlPanel, FlowLayout.CENTER);
	}
	
	public JPanel createComboBox()
	{
		numberPanel = new JComboBox(); 
		for (int i=0; i<50; i++)
			numberPanel.addItem(i+1);
		numberPanel.setEditable(true); 
	//	numberPanel.addActionListener(listener); 
		
		JPanel panel = new JPanel(); 
		panel.add(numberPanel); 
		return panel; 
	}
	
	public JPanel createRadioButtons()
	{
		randomButton = new JRadioButton("Randomized");
		notRandomButton = new JRadioButton("Regular");
		notRandomButton.setSelected(true);
		
		ButtonGroup group = new ButtonGroup(); 
		group.add(randomButton); 
		group.add(notRandomButton); 
		
		JPanel panel = new JPanel();
		panel.add(randomButton);
		panel.add(notRandomButton); 
		
		return panel; 
	}
	
	public JPanel createButtons()
	{
	exitButton = new JButton("Exit"); 
	exitButton.addActionListener(listener); 
	
	confirmButton = new JButton("Submit"); 
	confirmButton.addActionListener(listener); 

	ButtonGroup group = new ButtonGroup(); 
	group.add(confirmButton); 
	group.add(exitButton);
	
	JPanel panel = new JPanel(); 
	panel.add(confirmButton); 
	panel.add(exitButton); 

	panel.setBorder(new TitledBorder(new EtchedBorder(), "Press submit")); 
	return panel; 
	}
	
	public void selectTopic(ActionEvent event) throws IOException
	{
		Object source = event.getSource();
		numberOfQuestions =  (int) numberPanel.getSelectedItem(); 
		randomized = randomButton.isSelected(); 
		if (source instanceof JButton)
		{
			if (source == confirmButton && topicSelecter.count ==0)
			{
				questioner = new QuestionGUI(topicName, testName, numberOfQuestions, randomized, topicSelecter ,testSelecter, this); 
				questioner.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				questioner.setVisible(true); 
				this.setVisible(false); 
			}
			else if (source == confirmButton && topicSelecter.count > 0)
			{
				questioner.getFilesAndQuestions(topicName, testName, numberOfQuestions, randomized); 
				questioner.setVisible(true); 
				this.setVisible(false); 
			}
			else if(source == exitButton)
			{
				timer.start();
				sound.play("button-4.wav"); 
			}
		}
		else if (source instanceof Timer)
				System.exit(0);
	}
	public void storeQuestioner(QuestionGUI questioner1)
	{
		questioner = questioner1; 
	}
	public void storeNames(String topicName1, String testName1)
	{
		topicName = topicName1;
		testName = testName1;
	} 
}

