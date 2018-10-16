import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;


public class TestSelecterGUI extends JFrame
{
	//Variables 
	public String[] testoptions = new String[]{"Regional 2013","Regional 2012", "Regional 2011"}; 
	public static String testName= ""; 
	public static final int FRAME_WIDTH = 300; 
	public static final int FRAME_HIEGHT = 250; 
	private JButton exitButton; 
	private JButton confirmButton; 
	private JLabel directions;
	private JComboBox testPanel; 
	private ActionListener listener; 
	private FlowLayout flow = new FlowLayout(FlowLayout.CENTER);
	private String topicName;
	private TopicSelecterGUI topicSelecter; 
	private QuestionSelecterGUI questionSelecter; 
	private Sound sound = new Sound(); 
	private Timer timer;
	
	public TestSelecterGUI(String topicName1, TopicSelecterGUI topicSelecter1)
	{
		
		this.setTitle("Test Selecter"); 
		topicSelecter = topicSelecter1; 
		this.setAlwaysOnTop(true);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(( dim.width- FRAME_WIDTH)/2 , (dim.height- FRAME_HIEGHT)/2);
		
		topicName = topicName1; 
		Container con = getContentPane();
		con.setLayout(flow);
		
		directions = new JLabel("Select the test you wish to study."); 
		add(directions); 
		
		class ChoiceListener implements ActionListener
		{
			public void actionPerformed(ActionEvent event)
			{
					try {
						selectTest(event);
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
		JPanel testPanel = createComboBox(); 
		JPanel confirmationPanel = createButtons(); 
		
		JPanel controlPanel = new JPanel(); 
		controlPanel.setLayout(new GridLayout(2,1)); 
		controlPanel.add(testPanel); 
		controlPanel.add(confirmationPanel); 
		
		add(controlPanel, FlowLayout.CENTER);
	}
	
	public JPanel createComboBox()
	{
		testPanel = new JComboBox(); 
		for (int i=0; i<testoptions.length; i++)
			testPanel.addItem(testoptions[i]);
		testPanel.setEditable(true); 
	//	testPanel.addActionListener(listener); 
		
		JPanel panel = new JPanel(); 
		panel.add(testPanel); 
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
	
	public void selectTest(ActionEvent event) throws IOException
	{
		Object source = event.getSource();
		testName= (String) testPanel.getSelectedItem(); 
		
		//selects topic 
		if (source instanceof JButton)
		{
			if (source == confirmButton && topicSelecter.count ==0)
			{
				questionSelecter = new QuestionSelecterGUI(topicName, testName, topicSelecter ,this); 
				questionSelecter.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				questionSelecter.setVisible(true); 
				this.setVisible(false); 
			}
			else if (source == confirmButton && topicSelecter.count > 0)
			{
				questionSelecter.storeNames(topicName, testName); 
				questionSelecter.setVisible(true); 
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
	public void storeQuestionSelecter(QuestionSelecterGUI questionSelecter1)
	{
		questionSelecter = questionSelecter1; 
	}
	public void storeTopicName(String topicName1)
	{
		topicName = topicName1;
	}
}
