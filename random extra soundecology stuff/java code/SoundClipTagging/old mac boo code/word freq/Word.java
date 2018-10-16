public class Word 
{
	private String word; 
	private int count; 
	public Word(String word1)
	{
		word = word1; 
		count =1; 
	}
	public void increaseCount()
	{
		count++;
	}
	public int getCount() {return count;} 
	public String getWord() {return word;} 
}
