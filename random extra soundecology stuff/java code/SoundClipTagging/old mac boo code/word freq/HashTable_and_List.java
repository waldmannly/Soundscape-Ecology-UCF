import java.util.ArrayList;
import java.util.StringTokenizer;


public class HashTable_and_List {
	public ListNode[] myList; 
	public final int LIST_SIZE;
	public Sort_and_Fix sortFix; 
	public final int SHOW_EVERY_WORD_WITH_NUM_LETTERS_GREATER_THAN; 
	public int SHOW_EVERY_WORD_WITH_COUNT_GREATER_THAN; 
	public boolean COUNT_PHRASES; 
	public boolean COUNT_ONLY_PHRASES; 


	public HashTable_and_List(int listSize, int numLetters ,int numCount, boolean countPhrases, boolean countPhrasesOnly)
	{
		COUNT_PHRASES = countPhrases;
		COUNT_ONLY_PHRASES = countPhrasesOnly; 
		LIST_SIZE = listSize; 
		SHOW_EVERY_WORD_WITH_NUM_LETTERS_GREATER_THAN = numLetters; 
		SHOW_EVERY_WORD_WITH_COUNT_GREATER_THAN = numCount; 
		myList = new ListNode[LIST_SIZE];
		sortFix = new Sort_and_Fix(); 
	}

	public void addToHash(String parameter) 
	{
		//insets nodes into the array according to the hash code and if the word is already in the 
		//list then it increments the count inside the word object. 

		String input =parameter;

		String[] phraseTracker3  = new String[3]; 
		String[] phraseTracker2 = new String[2]; 
		int thingsInTracker3=0; 
		int thingsInTracker2 =0; 
		//		boolean complete2 = false; 
		//		boolean complete3 = false; 
		//		int phraseHash; 
		String phrase; 

		StringTokenizer tokenizer = new StringTokenizer(input); 
		while (tokenizer.hasMoreTokens())
		{
			String word = tokenizer.nextToken();

			word = word.toUpperCase(); //hash code is different for word that have different capitalizations
			word = sortFix.fixWord(word);//removes things like periods, commas and quotes


			if (word.length() > SHOW_EVERY_WORD_WITH_NUM_LETTERS_GREATER_THAN)
			{
				if (COUNT_PHRASES)
				{
					if (phraseTracker3[0] != null&& phraseTracker3[1] != null && phraseTracker3[2] != null)
					{
						String word3 = phraseTracker3[0] + " " +phraseTracker3[1] + " " + phraseTracker3[2]; 
						int hash3 = word3.hashCode(); 
						if (hash3 < 0)
							hash3 = -hash3; 

						hash3 = hash3 % LIST_SIZE; 
						Word wordObj3 =  new Word(word3); 
						//add triple word to hash 
						addToList(wordObj3, word3, hash3); 
					}
					if (thingsInTracker3== 3)
					{
						thingsInTracker3 = 0; 
					}
					if (phraseTracker2[0] != null && phraseTracker2[1] != null)
					{
						String word2 = phraseTracker2[0] + " " +phraseTracker2[1] ;  
						int hash2 = word2.hashCode(); 
						if (hash2 < 0)
							hash2 = -hash2; 

						hash2 = hash2 % LIST_SIZE; 
						Word wordObj2 =  new Word(word2); 
						// add double word to hash 
						addToList(wordObj2, word2, hash2); 
					}
					if (thingsInTracker2 ==2)
					{
						thingsInTracker2 = 0; 
					}
					
//					if (thingsInTracker3 ==2)
//					{
//						phraseTracker3[thingsInTracker3] = word; 
//					}
//					else if(thingsInTracker3 == 1)
//					{
//						phraseTracker3[0] = phraseTracker3[thingsInTracker3]; 
//						phraseTracker3[thingsInTracker3] = phraseTracker3[2]; 
//						phraseTracker3[2] = word; 
//					}
//					else //thingsInTracker == 0 
//					{
						phraseTracker3[0] = phraseTracker3[1]; 
						phraseTracker3[1] = phraseTracker3[2]; 
						phraseTracker3[2] = word; 
//					}
					
//					if (thingsInTracker2 == 1)
//						phraseTracker2[thingsInTracker2] = word; 
//					else //thingsInTracker == 0 
//					{
//						phraseTracker2[1] = phraseTracker2[thingsInTracker2]; 
//						phraseTracker2[thingsInTracker2] = word; 
//					}
						phraseTracker2[0] = phraseTracker2[1]; 
						phraseTracker2[1] = word; 
						
						

					thingsInTracker2++; 
					thingsInTracker3++; 
				}
				if (!COUNT_ONLY_PHRASES)
				{
					int hash = word.hashCode(); 
					if (hash < 0)
						hash = -hash; 

					hash = hash % LIST_SIZE; 

					Word wordObj = new Word(word); 

					//add singular word to hash 
					addToList(wordObj, word, hash);
				}
			}
		}
	}
	private void addToList(Word wordObj, String word, int hash)
	{
		ListNode first = myList[hash];
		ListNode follower = first; 
		ListNode next = first; 
		boolean foundIt = false; 

		while (next != null)
		{
			if (next.getValue().getWord().equalsIgnoreCase(word)) 
			{
				next.getValue().increaseCount(); 
				foundIt = true; 
				break; 
			}
			else 
			{
				follower = next; 
				next = next.getNext(); 
			}
		}

		if (!foundIt)
		{
			ListNode newNode = new ListNode(wordObj, null); 
			if (first == null)
			{
				myList[hash] = newNode; 
			}
			else 
			{
				follower.setNext(newNode);
			}
		}
	}

	public ArrayList<Word> toArrayList()
	{
		ArrayList<Word> list = new ArrayList<Word>(); 
		ListNode next; 
		for (int i=0; i< LIST_SIZE; i++)
		{
			next = myList[i]; 
			while (next != null)
			{
				if (next.getValue().getCount() > SHOW_EVERY_WORD_WITH_COUNT_GREATER_THAN )
					list.add(next.getValue()); 
				next = next.getNext(); 
			}
		}	

		return list; 
	}

	public int[] checkListFullness()
	{
		int[] count = {0,0}; 
		int max=0; 
		int temp = 0;
		double total =0; 
		double avg =0;
		ListNode next; 
		for (int i=0; i< LIST_SIZE; i++)
		{
			next = myList[i];
			if (next != null)
				count[0]++; 

			while (next != null)
			{
				temp++; 
				next = next.getNext();
			}

			if (temp > max)
				max = temp;
			total += temp; 

			temp=0;
		}	
		avg = total/(double)count[0];
		System.out.println(avg);
		count[1] = max;
		return count; 
	}

}
