import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.JTextComponent;


public class QuestionGUI extends JFrame 
{
	private ActionListener listener; 
	private FlowLayout flow = new FlowLayout(FlowLayout.CENTER);
	public static final int FRAME_WIDTH = 525; 
	public static final int FRAME_HIEGHT = 250; 
	private JTextArea question =new JTextArea(""); 
	private JButton exitButton; 
	private JButton aButton; 
	private JButton bButton; 
	private JButton cButton; 
	private JButton dButton; 
	public int questionNumber =0; 
	public int totalQuestions;
	private Test test;
	private String[] fileNames; 
	private ArrayList<QuestionAnswer> questions = new ArrayList<QuestionAnswer>(); 
	private ResultGUI resultGUI = new ResultGUI(this);
	private TestSelecterGUI testSelecter; 
	private TopicSelecterGUI topicSelecter; 
	public int totalQuestionsInTest; 
	private QuestionSelecterGUI questionSelecter; 
	private MixSelecterGUI mixSelecter; 
	
	
	public QuestionGUI(String topicName1 , String testName1, int numberOfQuestions1,boolean random, TopicSelecterGUI topicSelecter1, TestSelecterGUI testSelecter1, QuestionSelecterGUI questionSelecter1) throws IOException
	{
		this.setAlwaysOnTop(true);
		topicSelecter = topicSelecter1; 
		testSelecter = testSelecter1;
		questionSelecter = questionSelecter1; 
		
		if (!topicName1.equals(testName1))
		{
			this.setTitle(topicName1 + " "+testName1 +" Test"); 
			resultGUI.setTitle(topicName1 + " "+testName1 +" Test"); 
			test = new Test(topicSelecter, testSelecter); 
			fileNames = test.convertToFileNames(topicName1, testName1); 
			questions= test.getQuestions(fileNames[0], fileNames[1], numberOfQuestions1, random); 
			totalQuestions = questions.size();
			resultGUI.storeTotalQuestionNumber(totalQuestions); 
			totalQuestionsInTest = questions.size(); 
		}
	
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(( dim.width- FRAME_WIDTH)/2 , (dim.height- FRAME_HIEGHT)/2);
		
		resultGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container con = getContentPane();
		con.setLayout(flow);
	
		class ChoiceListener implements ActionListener
		{
			public void actionPerformed(ActionEvent event) 
			{
				Object source = event.getSource();
				if(source instanceof JButton)
					try {
						compareAnswers(source);
					}
					catch (IOException e) {
						e.printStackTrace();
						System.out.println("ERROR IOexception"); 
					}
			}
		}
		
		listener = new ChoiceListener(); 
		question.setLineWrap(true); 
		question.setWrapStyleWord(true);
		
		if (!topicName1.equals(testName1))
		{
			createQuestion(); 
			createControlPanel(); 
		}
		setSize(FRAME_WIDTH, FRAME_HIEGHT); 
	}
	
	public void createQuestion()
	{

		JPanel panel = new JPanel(); 
		question.setText(questions.get(questionNumber).getQuestion()); 
		question.setColumns(40);
		question.setEditable(false); 
		panel.add(question);
		panel.setBorder(BorderFactory.createLineBorder(Color.black));
		add(panel); 
		questionNumber++; 
	}
	
	public void repopulateQuestion()
	{	
		question.setText(questions.get(questionNumber).getQuestion()); 
		questionNumber++; 
	}
	
	public void studyAgain() throws IOException
	{
		
		questions = test.studyAgain(); 
		totalQuestions = questions.size();
		questionNumber=0; 
		resultGUI.nextQuestion(); 
		
	}
	
	public void createControlPanel()
	{
		JPanel buttonPanel = createButtons(); 
		add(buttonPanel, FlowLayout.CENTER);
	}
	
	public JPanel createButtons()
	{
		exitButton = new JButton("Exit"); 
		exitButton.addActionListener(listener); 
		
		aButton = new JButton("A"); 
		aButton.addActionListener(listener); 
		
		bButton = new JButton("B"); 
		bButton.addActionListener(listener); 
		
		cButton = new JButton("C"); 
		cButton.addActionListener(listener); 
		
		dButton = new JButton("D"); 
		dButton.addActionListener(listener); 
		
		ButtonGroup group = new ButtonGroup(); 
		group.add(aButton); 
		group.add(bButton); 
		group.add(cButton); 
		group.add(dButton); 
		group.add(exitButton);
		
		JPanel panel = new JPanel(); 
		panel.add(aButton); 
		panel.add(bButton); 
		panel.add(cButton); 
		panel.add(dButton); 
		panel.add(exitButton); 

		panel.setBorder(new TitledBorder(new EtchedBorder(), "Select an answer")); 
		return panel; 
	}
	
	public void compareAnswers(Object source) throws IOException
	{
		boolean correctAnswer= false; 
		if (source == exitButton)
			resultGUI.quitEarly(); 
		else if (source == aButton)
		{
			if ("A".equals(questions.get(questionNumber-1).getAnswer()))
				correctAnswer = true; 
			else
				correctAnswer = false; 
		}
		else if (source == bButton)
		{
			if ("B".equals(questions.get(questionNumber-1).getAnswer()))
				correctAnswer = true; 
			else
				correctAnswer = false; 
		}
		else if (source == cButton)
		{
			if ("C".equals(questions.get(questionNumber-1).getAnswer()))
				correctAnswer = true; 
			else
				correctAnswer = false; 
		}
		else if (source == dButton)
		{
			if ("D".equals(questions.get(questionNumber-1).getAnswer()))
				correctAnswer = true; 
			else
				correctAnswer = false; 
		}
		else 
		{
			System.out.println("ERROR"); 
			correctAnswer = false; 
		}
		
		if (!correctAnswer)
			test.printOutQuestion(questions.get(questionNumber-1)); 
		
		resultGUI.displayPanel(correctAnswer,questions.get(questionNumber-1).getAnswer(), questions.get(questionNumber-1).getfullAnswer(),questions.get(questionNumber-1).getQuestionPart());
		this.setVisible(false); 
		
	}
	
	public void storeMixSelecter(MixSelecterGUI mixSelecter1, Test test1)
	{
		mixSelecter = mixSelecter1;
		test = test1;
	}
	
	public void storeQuestions2(ArrayList<QuestionAnswer> questions1)
	{
		questions = questions1; 
		totalQuestions = questions.size();
		totalQuestionsInTest = questions.size();
		this.setTitle("Mixed Test"); 
		resultGUI.setTitle("Mixed Test"); 
		resultGUI.storeTotalQuestionNumber(totalQuestions); 
		repopulateQuestion(); 
		System.out.println(totalQuestions);
	}
	public void storeQuestions(ArrayList<QuestionAnswer> questions1)
	{
		questions = questions1; 
		totalQuestions = questions.size();
		totalQuestionsInTest = questions.size(); 
		resultGUI.storeTotalQuestionNumber(totalQuestions); 
		createQuestion();
		createControlPanel();
		this.setTitle("Mixed Test"); 
		resultGUI.setTitle("Mixed Test"); 
	}
	public void getFilesAndQuestions(String topicName1, String testName1, int numQuestions, boolean random) throws IOException
	{
		fileNames = test.convertToFileNames(topicName1, testName1); 
		questions= test.getQuestions(fileNames[0], fileNames[1], numQuestions, random); 
		totalQuestions = questions.size();
		totalQuestionsInTest = questions.size(); 
		resultGUI.storeTotalQuestionNumber(totalQuestions); 
		repopulateQuestion(); 
	}
	
	public void restart()
	{
		resultGUI.resetNames(); 
		questionNumber = 0; 
		topicSelecter.storeQuestioner(this);
		topicSelecter.setVisible(true); 
		topicSelecter.addCount(); 
		if (mixSelecter != null)
			mixSelecter.restart(); 
	}
}
