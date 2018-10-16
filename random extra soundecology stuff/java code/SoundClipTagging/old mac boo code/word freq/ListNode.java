
public class ListNode {
	private Word value;
	private ListNode next; 
	
	public ListNode(Word initValue, ListNode initNext)
	{
		value = initValue; 
		next = initNext; 
	}
	
	public Word getValue()
	{
		return value; 
	}
	
	public ListNode getNext()
	{
		return next; 
	}
	
	public void setValue (Word theNextValue)
	{
		value = theNextValue; 
	}
	
	public void setNext(ListNode theNewNext)
	{
		next = theNewNext; 
	}
}
