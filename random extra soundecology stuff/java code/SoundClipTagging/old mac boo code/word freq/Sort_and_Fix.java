import java.util.ArrayList;
import java.util.List;


public class Sort_and_Fix {

	
	public void alphaSortList(List<Word> list)
	{
		if (list.size() <= 1) {
			return;
		}
		int middle = list.size() / 2;
		alphaSortList(list.subList(0, middle));
		alphaSortList(list.subList(middle, list.size()));
		ArrayList<Word> bList = new ArrayList<Word>();
		int l = 0;
		int r = middle;
		while (l < middle && r < list.size()) {
			if (list.get(l).getWord().compareTo(list.get(r).getWord()) < 0) {
				bList.add(list.get(l++));
			} else {
				bList.add(list.get(r++));
			} // end if/else
		} // end while
		while (l < middle) {
			bList.add(list.get(l++));
		}
		while (r < list.size()) {
			bList.add(list.get(r++));
		}
		list.clear();
		for (Word x : bList) {
			list.add(x);
		}
	}


	//merge sort off the internet by Bill Kraynek
	public void mSortList(List<Word> list) 
	{
		if (list.size() <= 1) {
			return;
		}
		int middle = list.size() / 2;
		mSortList(list.subList(0, middle));
		mSortList(list.subList(middle, list.size()));
		ArrayList<Word> bList = new ArrayList<Word>();
		int l = 0;
		int r = middle;
		while (l < middle && r < list.size()) {
			if (list.get(l).getCount() >= list.get(r).getCount()) {
				bList.add(list.get(l++));
			} else {
				bList.add(list.get(r++));
			} // end if/else
		} // end while
		while (l < middle) {
			bList.add(list.get(l++));
		}
		while (r < list.size()) {
			bList.add(list.get(r++));
		}
		list.clear();
		for (Word x : bList) {
			list.add(x);
		}
	}

	public String fixWord(String in)
	{
		char[] checkFor = {'.', ',' , '"', '?', '!',':',';','*', ')','(', '_' , '[',']','{','}', '“','”','’','‘','<','>' };

		//checks to see if there is an apostrophe at the beginning 
		while (in.indexOf('\'') == 0)
		{
			in = in.substring(1); 
		}

		//checks to see if there is dashes (M and N) on beginning or end
		char[] dashes = {'-','-','-','-','-','-' };
		for (int i=0; i< dashes.length; i++)
		{

			int index = in.lastIndexOf(dashes[i]);
			//System.out.println(in + " " +index +"  " + in.length());
			while ( index == (in.length()-1) || index == 0)
			{
				if (index == -1)
					break; 
				else if (index ==0 )
					in = in.substring(1);
				else //if (index == in.length()-1)
					in = in.substring(0,index); 

				index = in.lastIndexOf(dashes[i]);
			}
		}

		//checks all punctuation that doesnt have strange positional rules 
		for (int i=0; i < checkFor.length; i++)
		{
			int index = in.indexOf(checkFor[i]);
			while (index != -1)
			{
				if (index == 0)
				{
					in = in.substring(index+1); 
				}
				else if ( index == in.length()-1)
				{
					in = in.substring(0, index); 
				}
				else 
				{
					in = in.substring(0, index) + in.substring(index+1); 
				}

				index = in.indexOf(checkFor[i]);
			}
		}
		return in;
	}
}
