import java.util.ArrayList;


public class QuestionAnswer 
{
	private String question= "";
	private String answer ="";
	private String topic =""; 
	private String fullAnswer =""; 
	private String questionPart =""; 
	
	public QuestionAnswer()
	{
		
	}
	public void addTopic(String topic1)
	{
		topic = topic1;
	}
	public String getTopic()
	{
		return topic; 
	}
	
	public void addfullAnswer(String fullAnswer1)
	{
		fullAnswer = fullAnswer1;
	}
	
	public String getfullAnswer()
	{
		return fullAnswer; 
	}
	
	public void addQuestionPart(String questionPart1)
	{
		questionPart = questionPart1; 
	}
	
	public String getQuestionPart()
	{
		return questionPart; 
	}
	public void add(String question1, String answer1, String fullAnswer1)
	{
		question =question1;
		answer =answer1; 
		fullAnswer =fullAnswer1;
	}

	public void add(String question1, String answer1)
	{
		question =question1;
		answer =answer1; 

	}

	public String getAnswer()
	{
		return answer; 
	}
	
	public String getQuestion()
	{
		return question;
	}
	
}
