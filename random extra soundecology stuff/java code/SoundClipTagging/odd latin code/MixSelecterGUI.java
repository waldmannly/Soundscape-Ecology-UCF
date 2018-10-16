import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;


public class MixSelecterGUI extends JFrame
{
	//Variables 
	public static final int FRAME_WIDTH = 490; 
	public static final int FRAME_HIEGHT = 250; 
	private JButton exitButton; 
	private JButton confirmButton; 
	private JButton addButton;
	private JLabel directions;
	private JComboBox topicPanel; 
	private JComboBox testPanel; 
	private JComboBox numberPanel; 
	private ActionListener listener; 
	private JScrollPane addedScrollPane; 
	private JTextArea addedTextArea;
	private FlowLayout flow = new FlowLayout(FlowLayout.CENTER);
	private TestSelecterGUI testSelecter; 
	private TopicSelecterGUI topicSelecter;
	private QuestionSelecterGUI questionSelecter; 
	private QuestionGUI questioner; 
	private boolean randomized = true; 
	private int numberOfQuestions; 
	private String topicName = "Mixed"; 
	private String testName = "Mixed"; 
	private ArrayList<QuestionAnswer> questions = new ArrayList<QuestionAnswer>(); 
	private Test test; 
	private Sound sound = new Sound(); 
	private Timer timer;
	
	public MixSelecterGUI(TopicSelecterGUI topicSelecter1, TestSelecterGUI testSelecter1)
	{
		this.setTitle("Multiple Test Selecter"); 
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(( dim.width- FRAME_WIDTH)/2 , (dim.height- FRAME_HIEGHT)/2);
		
		Container con = getContentPane();
		con.setLayout(flow);
		
		topicSelecter=topicSelecter1;
		testSelecter=testSelecter1;
		test= new Test(topicSelecter, testSelecter); 
		
		directions = new JLabel("Select the topics, tests, and number of questions you want to study."); 
		add(directions); 
		
		class ChoiceListener implements ActionListener
		{
			public void actionPerformed(ActionEvent event) 
			{
				try {
					selectTopic(event);
				} catch (IOException e) {
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
		JPanel topicPanel = createComboBox(); 
		JPanel confirmationPanel = createButtons(); 
		JPanel addPanel = createAddPanel(); 
		
		JPanel controlPanel = new JPanel(); 
		
		//I made it look better by getting rid of this grid crap
		/*controlPanel.setLayout(new GridLayout(3,1)); 
		controlPanel.add(topicPanel); 
		controlPanel.add(addPanel);
		controlPanel.add(confirmationPanel);
		
		add(controlPanel, FlowLayout.CENTER);
		*/
		
		add(topicPanel);
		add(addPanel); 
		add(confirmationPanel);
	}
	
	public JPanel createAddPanel()
	{
		addButton = new JButton("Add"); 
		addButton.addActionListener(listener); 
        
		addedTextArea = new JTextArea("");
		addedTextArea.setColumns(23);
		addedTextArea.setRows(5); 
		addedTextArea.setLineWrap(true); 
		addedTextArea.setWrapStyleWord(true);
		addedTextArea.setEditable(false);
		
		addedScrollPane = new JScrollPane(addedTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		JPanel panel = new JPanel(); 
		panel.add(addButton); 
		panel.add(addedScrollPane);
		return panel;
	}
	
	public JPanel createComboBox()
	{
		topicPanel = new JComboBox(); 
		//this is decreased by one to get the mix out of the options 
		for (int i=0; i<topicSelecter.topicoptions.length-1; i++)
			topicPanel.addItem(topicSelecter.topicoptions[i]);
		topicPanel.setEditable(true); 

		testPanel = new JComboBox(); 
		for (int i=0; i<testSelecter.testoptions.length; i++)
			testPanel.addItem(testSelecter.testoptions[i]);
		testPanel.setEditable(true); 
		
		numberPanel = new JComboBox(); 
		for (int i=0; i<50; i++)
			numberPanel.addItem(i+1);
		numberPanel.setEditable(true); 
		
		JPanel panel = new JPanel(); 
		panel.add(topicPanel); 
		panel.add(testPanel); 
		panel.add(numberPanel);
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

	//panel.setBorder(new TitledBorder(new EtchedBorder(), "Click submit when ready")); 
	return panel; 
	}
	
	public void selectTopic(ActionEvent event) throws IOException
	{
		Object source = event.getSource();
	//	topicName= (String) topicPanel.getSelectedItem(); 
		
		if (source instanceof JButton)
		{
			if (source == addButton)
			{
				//add to number of questions
				numberOfQuestions = numberOfQuestions + (int)numberPanel.getSelectedItem(); 
				String[] fileNames = test.convertToFileNames((String)topicPanel.getSelectedItem(), (String)testPanel.getSelectedItem());
				questions.addAll(test.getQuestions(fileNames[0], fileNames[1], (int)numberPanel.getSelectedItem(), randomized));
				String temp = addedTextArea.getText();
				addedTextArea.setText(temp +(String)topicPanel.getSelectedItem() +" " + (String)testPanel.getSelectedItem() + " Test with "+ (int)numberPanel.getSelectedItem()+ " question(s). \n");
			}
			else if (source == confirmButton && addedTextArea.getText().equals(""))
			{
				JOptionPane.showMessageDialog(null, "Please add at least one test to the Mixed Test Selecter.", "Warning", 0, null);
			}
			else if (source == confirmButton && topicSelecter.count ==0 )
			{
				questions = test.scambleMixedQuestions(questions, randomized);
				questionSelecter = new QuestionSelecterGUI(topicName, testName, topicSelecter ,testSelecter); 
				questionSelecter.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				questionSelecter.setVisible(false);
				questioner = new QuestionGUI(topicName, testName, numberOfQuestions, randomized, topicSelecter ,testSelecter ,questionSelecter);
				questioner.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				questioner.storeMixSelecter(this, test); 
				questioner.storeQuestions(questions);
				questioner.setVisible(true); 
				questionSelecter.storeQuestioner(questioner);
				testSelecter.storeQuestionSelecter(questionSelecter);
				this.setVisible(false); 
			}
			else if (source == confirmButton && topicSelecter.count > 0)
			{
				questions = test.scambleMixedQuestions(questions, randomized); 
				questioner.storeQuestions2(questions); 
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
	public void restart()
	{
		questions.clear();
		addedTextArea.setText("");
		numberOfQuestions = 0;
	}
}
