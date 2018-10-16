import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;


public class ResultGUI extends JFrame
{
	public static final int FRAME_WIDTH = 525; 
	public static final int FRAME_HIEGHT = 250; 
	private JFrame popUpWindow; 
	private JButton exitButton; 
	private JButton answerButton; 
	private JButton confirmButton; 
	private JButton leaveButton;
	private JLabel directions;
	private JPanel buttonPanel; 
	private ActionListener listener;
	private FlowLayout flow = new FlowLayout(FlowLayout.CENTER);
	public int correct = 0; 
	private int incorrect = 0;
	private int question = 0;
	private int percent = 0; 
	private QuestionGUI questioner; 
	private JLabel totalCorrect = new JLabel("Total correct: "+ correct +".");
	private JLabel totalIncorrect = new JLabel("Total incorrect: "+ incorrect +".");
	private JLabel totalQuestion = new JLabel("Total questions: "+ question +".");
	private JLabel totalPercent = new JLabel("Percent correct: "+ percent +"%.");
	private JTextArea popUpTextArea;
	private JScrollPane popUpScrollPane; 
	private StudyAgainGUI studyAgainGUI; 
	private Sound sound = new Sound(); 
	private Timer timer = new Timer(500, listener); 
	private int oldcorrect = 0; 
	private int total =0; 
	private String fullAnswerVariable =""; 
	private String questionPartVariable =""; 
	
	public ResultGUI(QuestionGUI questioner1)
	{
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(( dim.width- FRAME_WIDTH)/2 , (dim.height- FRAME_HIEGHT)/2);
		this.setAlwaysOnTop(true);
		popUpWindow = new JFrame();
		popUpWindow.setDefaultCloseOperation(HIDE_ON_CLOSE);
		popUpWindow.setSize((FRAME_WIDTH/3) *2 +10 , 80); 
		popUpWindow.setLocation(( (dim.width- (FRAME_WIDTH/3)*2))/2 , (dim.height- FRAME_HIEGHT)/2 - FRAME_HIEGHT/2);
		popUpWindow.getContentPane().setLayout(flow);
		popUpTextArea = new JTextArea(""); 
		popUpTextArea.setColumns(28); 
		popUpTextArea.setRows(2); 
		//popUpTextArea.setHorizontalAlignment(JLabel.CENTER);
		popUpTextArea.setLineWrap(true);
		popUpTextArea.setWrapStyleWord(true);
		popUpTextArea.setEditable(false);
		popUpScrollPane = new JScrollPane(popUpTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		popUpWindow.add(popUpScrollPane);
		popUpWindow.setVisible(false); 
		
		questioner = questioner1; 
		Container con = getContentPane();
		con.setLayout(flow);
		
		directions = new JLabel(" ");
		answerButton = new JButton("");
		answerButton.setVisible(false); 
		add(directions);
		add(answerButton); 
		
		studyAgainGUI= new StudyAgainGUI(questioner); 
		studyAgainGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		class ChoiceListener implements ActionListener
		{
			public void actionPerformed(ActionEvent event)
			{
				Object source = event.getSource();
				
				if (source instanceof JButton && source != answerButton)
					sound.end();
				
				if (source instanceof JButton && source == confirmButton && confirmButton.getText().equals( "Study again?"))
					studyAgain();
				else if (source instanceof JButton && source == confirmButton && directions.getText().equals("You have finished the "+ getTitle() +". Your results are below."))
					studyDifferentTest();
				else if (source instanceof JButton && source == answerButton)
					popUpFullAnswer();
				else if(source instanceof JButton && source == confirmButton)
					nextQuestion();
				else if(source instanceof JButton && source == exitButton)
					quitEarly(); 
				else if(source instanceof JButton && source == leaveButton)
				{
					timer.start();
					sound.play("button-4.wav"); 
				}
				else if (source instanceof Timer)
					System.exit(0);
				
				
			}
		}
		
		listener = new ChoiceListener(); 
		
		timer.addActionListener(listener); 
		
		JPanel panel = new JPanel(); 
		JPanel insidePanel = new JPanel(); 
		
		insidePanel.add(totalCorrect); 
		insidePanel.add(totalIncorrect); 
		insidePanel.add(totalQuestion); 
		insidePanel.add(totalPercent);
		panel.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HIEGHT/3 + 12));
		insidePanel.setPreferredSize(new Dimension(190, FRAME_HIEGHT));
		add(panel); 
		panel.add(insidePanel); 
		createButtons(); 
		setSize(FRAME_WIDTH, FRAME_HIEGHT); 
	}
	
	public void createButtons()
	{
	exitButton = new JButton("Exit"); 
	exitButton.addActionListener(listener); 
	
	confirmButton = new JButton("Next"); 
	confirmButton.addActionListener(listener); 
	
	leaveButton = new JButton("Exit"); 
	leaveButton.addActionListener(listener); 
	
	answerButton.addActionListener(listener); 
	
	ButtonGroup group = new ButtonGroup(); 
	group.add(confirmButton); 
	group.add(exitButton);
	
	buttonPanel = new JPanel(); 
	buttonPanel.setPreferredSize(new Dimension(FRAME_WIDTH/2 ,65));
	buttonPanel.add(confirmButton); 
	buttonPanel.add(exitButton); 
	buttonPanel.setBorder(new TitledBorder(new EtchedBorder(), "Press next")); 
	add(buttonPanel);  
	}
	
	public void displayPanel(boolean correctAnswer, String letterAnswer , String fullAnswer, String questionPart)
	{
		fullAnswerVariable = fullAnswer;
		questionPartVariable = questionPart; 
		answerButton.setText(letterAnswer+".");
		answerButton.setVisible(true); 
		confirmButton.setText("Next");
		question++; 
		if (correctAnswer)
		{
			correct++; 
			directions.setText("The answer you submitted was correct. The correct answer is " ); 
			sound.playCorrect(); 
		} 
		else
		{
			incorrect++; 
			directions.setText("The answer you submitted was incorrect. The correct answer is "); 
			sound.playIncorrect();
		}
		percent = ((correct *100 )/ question); 
		totalPercent.setText("Percent correct: "+ percent +"%.");
		totalCorrect.setText("Total correct: "+ correct +".");
		totalIncorrect.setText("Total incorrect: "+ incorrect +".");
		totalQuestion.setText("Total number of questions: "+ question +".");
		this.setVisible(true); 
	}
	
	public void nextQuestion()
	{
		popUpWindow.setVisible(false); 
		answerButton.setVisible(false); 
		
		// totalcorrect for all test == total of the all the orginal tests with out the study again totals added  
		if (correct == total)
		{
			endOfTest(); 
			return; 
		}
		else if (questioner.questionNumber >= questioner.totalQuestions)
		{
			showEnd();
			return;
		}
		
		questioner.repopulateQuestion(); 
		questioner.setVisible(true); 
		this.setVisible(false); 
	}
	
	public void quitEarly()
	{
		answerButton.setVisible(false); 
		popUpWindow.setVisible(false); 
		buttonPanel.setVisible(false); 
		directions.setText("You have quit the test.");
		JPanel panel = new JPanel(); 
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Press exit")); 
		panel.add(leaveButton);
		add(panel); 
		leaveButton.setVisible(true);
		this.setVisible(true);
		questioner.setVisible(false); 

	}
	
	public void showEnd()
	{
		directions.setText("You have finished the test. Your results are below.");
		percent = ((correct *100 )/ question); 
		buttonPanel.setBorder(new TitledBorder(new EtchedBorder(), "Choose an option."));
		confirmButton.setText("Study again?"); 
		totalPercent.setText("Percent correct: "+ percent +"%.");
		totalCorrect.setText("Total correct: "+ correct +".");
		totalIncorrect.setText("Total incorrect: "+ incorrect +".");
		totalQuestion.setText("Total number of questions: "+ question +".");
		this.setVisible(true); 
		questioner.setVisible(false);
	}
	
	public void studyAgain()
	{
		this.setVisible(false); 
		studyAgainGUI.setVisible(true); 
	}
	
	public void studyDifferentTest()
	{
		studyAgainGUI.differentTest(); 
		this.setVisible(false); 
		studyAgainGUI.setVisible(true); 
	}
	
	public void storeTotalQuestionNumber(int temptotal)
	{
		total = total + temptotal; 
	}
	
	public void endOfTest()
	{
		directions.setText("You have finished the "+ getTitle() +". Your results are below.");
		percent = ((correct *100 )/ question); 
		buttonPanel.setBorder(new TitledBorder(new EtchedBorder(), "Choose an option."));
		confirmButton.setText("Study another test?"); 
		totalPercent.setText("Percent correct: "+ percent +"%.");
		totalCorrect.setText("Total correct: "+ correct +".");
		totalIncorrect.setText("Total incorrect: "+ incorrect +".");
		totalQuestion.setText("Total number of questions: "+ question +".");
		this.setVisible(true); 
		questioner.setVisible(false);
	}
	public void popUpFullAnswer()
	{
    popUpTextArea.setText(questionPartVariable + " -- " + fullAnswerVariable);
		popUpWindow.setVisible(true); 
	}
	public void resetNames()
	{
		oldcorrect = correct +oldcorrect; 
		confirmButton.setText("Next");
		buttonPanel.setBorder(new TitledBorder(new EtchedBorder(), "Press next")); 
		studyAgainGUI.resetNames(); 
	}
}
