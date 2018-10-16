import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;


public class TopicSelecterGUI extends JFrame
{
	//Variables 
	public String[] topicoptions = new String[]{"Customs","Greek Literature", "Hellenic History", "History of the Empire", "Mix"}; 
	public static  String topicName= ""; 
	public static final int FRAME_WIDTH = 300; 
	public static final int FRAME_HIEGHT = 250; 
	private JButton exitButton; 
	private JButton confirmButton; 
	private JLabel directions;
	private JComboBox topicPanel; 
	private ActionListener listener; 
	private FlowLayout flow = new FlowLayout(FlowLayout.CENTER);
	private TestSelecterGUI testSelecter; 
	public int count = 0; 
	private MixSelecterGUI mixSelecter; 
	private QuestionGUI questioner;
	private Sound sound = new Sound(); 
	private Timer timer;
	
	public TopicSelecterGUI()
	{
		this.setTitle("Topic Selecter"); 
		this.setAlwaysOnTop(true);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(( dim.width- FRAME_WIDTH)/2 , (dim.height- FRAME_HIEGHT)/2);
		this.setAlwaysOnTop(true);
		Container con = getContentPane();
		con.setLayout(flow);
		
		directions = new JLabel("Select the topic you wish to study."); 
		add(directions); 
		
		class ChoiceListener implements ActionListener
		{
			public void actionPerformed(ActionEvent event) 
			{
				selectTopic(event);
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
		
		JPanel controlPanel = new JPanel(); 
		controlPanel.setLayout(new GridLayout(2,1)); 
		controlPanel.add(topicPanel); 
		controlPanel.add(confirmationPanel); 
		
		add(controlPanel, FlowLayout.CENTER);
	}
	
	public JPanel createComboBox()
	{
		topicPanel = new JComboBox(); 
		for (int i=0; i<topicoptions.length; i++)
			topicPanel.addItem(topicoptions[i]);
		topicPanel.setEditable(true); 
	//	topicPanel.addActionListener(listener); 
	
		JPanel panel = new JPanel(); 
		panel.add(topicPanel); 
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
	
	public void selectTopic(ActionEvent event)
	{
		Object source = event.getSource();
		topicName= (String) topicPanel.getSelectedItem(); 
		
		//selects topic 
		if (source instanceof JButton)
		{
			if (source == confirmButton && count ==0 && topicName.equals("Mix"))
			{
				testSelecter = new TestSelecterGUI(topicName,this); 
				testSelecter.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				testSelecter.setVisible(false); 
				mixSelecter = new MixSelecterGUI(this, testSelecter); 
				mixSelecter.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				mixSelecter.setVisible(true); 
				this.setVisible(false); 
			}
			else if (source == confirmButton && count >0 && topicName.equals("Mix") && mixSelecter == null)
			{
				mixSelecter = new MixSelecterGUI(this, testSelecter); 
				mixSelecter.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				mixSelecter.storeQuestioner(questioner); 
				mixSelecter.setVisible(true); 
				this.setVisible(false);
			}
			else if (source == confirmButton && count >0 && topicName.equals("Mix"))
			{
				mixSelecter.setVisible(true); 
				this.setVisible(false); 
			}
			else if (source == confirmButton && count ==0)
			{
				testSelecter = new TestSelecterGUI(topicName,this); 
				testSelecter.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				testSelecter.setVisible(true); 
				this.setVisible(false); 
			}
			else if (source == confirmButton && count > 0)
			{
				testSelecter.storeTopicName(topicName);
				testSelecter.setVisible(true); 
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
	public void addCount()
	{
		count++; 
	}
}
