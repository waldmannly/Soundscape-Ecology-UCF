
public class Assignment 
{

	private String name; 
	private String topic; 
	private int priority; 
	
	public Assignment(String name1, String topic1, int priority1)
	{
		name = name1; 
		topic = topic1; 
		priority = priority1; 
	}
	
	int getPriority()
	{
		return priority; 
	}
	
	String getName()
	{
		return name; 
	}
	
	String getTopic()
	{
		return topic; 
	}
	
}
