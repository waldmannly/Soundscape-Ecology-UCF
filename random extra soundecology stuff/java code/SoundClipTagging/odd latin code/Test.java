import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Test
{
	//change in the testSelecter and topicSelecter GUIs
	private String[] topicoptions;
	private String[] testoptions;
	private int timesStudied = 0;  
	private TestSelecterGUI testSelecter; 
	private TopicSelecterGUI topicSelecter; 
	
	public Test(TopicSelecterGUI topicSelecter1, TestSelecterGUI testSelecter1)
	{
		testSelecter = testSelecter1;
		topicSelecter = topicSelecter1; 
		topicoptions = topicSelecter1.topicoptions; 
		testoptions = testSelecter1.testoptions; 
	}

	public String[] convertToFileNames(String topicName1,String testName1)
	{
		String[] selections = new String[2]; 
		selections[0] = topicName1+ " "+testName1 + ".in"; 
		selections[1] = topicName1+ " "+testName1 + " Key.in"; 
		return selections; 
	}
	
	public ArrayList<QuestionAnswer> getQuestions(String QuestionFile, String KeyFile, int numQuestions, boolean randomized) throws IOException
	{
		Formater format = new Formater(QuestionFile, KeyFile); 
		
		FileReader reader = new FileReader("Questions.out");
		FileReader reader1= new FileReader("FormatedKey.out");
		FileWriter writer = new FileWriter("StudyQuestionsGL.out");
		BufferedReader in = new BufferedReader(reader); 
		BufferedReader key = new BufferedReader(reader1);
		PrintWriter out= new PrintWriter(writer); 
		
		//topic should be the first line. 
		String topic = in.readLine(); 
		String topickey = key.readLine(); 
		
		String next = in.readLine(); 
		int endQ;
		int bdot;
		int cdot; 
		int ddot; 
		String answer = key.readLine(); 
		ArrayList<QuestionAnswer> questions = new ArrayList<QuestionAnswer>(); 
		int num = 1;
		while (next != null)
		{
			//removes numbers from the questions
			num++;
			if(num<10)
				next = next.substring(2);
			else if (num > 9)
				next = next.substring(3);
			else if (num > 99)
				next = next.substring(4);
			
			endQ = next.indexOf('?')+2; 
			if (endQ == 1)
				endQ =next.indexOf(':') +2; 
			if (endQ== 1)
			{
				endQ = next.lastIndexOf("a. ") ; 
				if (endQ == -1)
					endQ = next.lastIndexOf("A. ") ; 
			}
			//assuming there will always be an A and a B 
			bdot = next.indexOf(" b."); 
			if (bdot == -1)
				bdot = next.indexOf(" B. "); 
			
			cdot = next.indexOf(" c. "); 
			if (cdot == -1)
				cdot = next.indexOf(" C. "); 
			
			ddot = next.indexOf(" d."); 
			if (ddot == -1)
				ddot = next.indexOf(" D. "); 
		
			//checks for different amount of answer choices 
			StringTokenizer tokenizer = new StringTokenizer(answer);
			String token = tokenizer.nextToken();
			QuestionAnswer questionanswer = new QuestionAnswer(); 
			
			if ((cdot ==-1) && (ddot ==-1))
			{
				questionanswer.add(next.substring(0, endQ) + "\n " + next.substring(endQ,bdot)+ "\n"+next.substring(bdot), token); 
				questions.add(questionanswer);
			}
			else if ((cdot != -1) && (ddot ==-1))
			{
				questionanswer.add(next.substring(0, endQ) + "\n " + next.substring(endQ,bdot)+ "\n"+next.substring(bdot,cdot) +"\n"+ next.substring(cdot), token); 
				questions.add(questionanswer);
			}
			else if ((cdot != -1) && (ddot!=-1))
			{
				/*
				try
				{
					System.out.println(next.substring(0, endQ) + "\n "+ next.substring(endQ,bdot)+ "\n"+next.substring(bdot,cdot) +"\n"+ next.substring(cdot,ddot) + "\n"+ next.substring(ddot)); 
				}
				catch (StringIndexOutOfBoundsException E )
				{
					System.out.println("STRING INDEX OUT OF BOUNDS \n"+ num +"\n" + next);
				}
				 */
				questionanswer.add(next.substring(0, endQ) + "\n "+ next.substring(endQ,bdot)+ "\n"+next.substring(bdot,cdot) +"\n"+ next.substring(cdot,ddot) + "\n"+ next.substring(ddot), token);
				questions.add(questionanswer); 
			} 
			else
			{
				System.out.println("ERROR     :    " + next +"  b:"+bdot+ "  c:" + cdot + "  d:"+ ddot);
				break;
			}
			
			questionanswer.addQuestionPart(next.substring(0, endQ));
			
			//gives questionanswer the full answer
			if (questionanswer.getAnswer().equalsIgnoreCase("a"))
				questionanswer.addfullAnswer(next.substring(endQ+3,bdot));
			else if (questionanswer.getAnswer().equalsIgnoreCase("b"))
				questionanswer.addfullAnswer(next.substring(bdot+3,cdot));
			else if(questionanswer.getAnswer().equalsIgnoreCase("c"))
				questionanswer.addfullAnswer(next.substring(cdot+3,ddot));
			else if (questionanswer.getAnswer().equalsIgnoreCase("d"))
				questionanswer.addfullAnswer(next.substring(ddot+3));
			
			answer = key.readLine(); 
			next =in.readLine(); 
		}		
		
		//scramble questions and make the number of questions = numQuestions
		if (randomized)
		{
			int index; 
			QuestionAnswer temp = new QuestionAnswer();
			for (int i=0; i< questions.size(); i++)
			{
				index = (int) (Math.random() *questions.size()); 
				temp = questions.get(index); 
				questions.set(index,questions.get(i)); 
				questions.set(i, temp);
			}
		}
		
		questions.subList(numQuestions, questions.size()).clear(); 
		return questions; 
	}
	
	public void printOutQuestion(QuestionAnswer thing) throws IOException
	{
		BufferedReader in;
		FileReader reader;
		if (timesStudied%2 ==0)
		{
			reader = new FileReader("StudyQuestionsGL.out");
			in= new BufferedReader(reader); 
		}
		else
		{
			reader = new FileReader("StudyQuestions2.out");
			in= new BufferedReader(reader); 
		}
		String next = in.readLine();
		
		ArrayList<String> alreadyIn = new ArrayList<String>();
		while(next!=null)
		{
			alreadyIn.add(next); 
			next = in.readLine();
		}
		alreadyIn.add(next); 
		 
		in.close();
		
		for (int i=0; i<alreadyIn.size(); i++)
		{
			if (alreadyIn.get(i)==null)
				alreadyIn.remove(i);
		}
			
			
		FileWriter writer;
		PrintWriter out;
		if (timesStudied%2 ==0)
		{
			writer = new FileWriter("StudyQuestionsGL.out");
			out= new PrintWriter(writer);  
		}
		else
		{
			writer = new FileWriter("StudyQuestions2.out");
			out= new PrintWriter(writer);  
		}
		
		for (int i=0; i <alreadyIn.size(); i++)
		{
			if (alreadyIn.get(i) != null)
			out.println(alreadyIn.get(i)); 
		}
		StringTokenizer tokenizer = new StringTokenizer(thing.getQuestion());
		out.println(thing.getQuestion());
		String token = tokenizer.nextToken();
		out.println("The answer to this question "/*+ token.substring(0, token.length()-1)*/ +" is "+ thing.getAnswer() +". "+  thing.getfullAnswer() + "\n");
		out.close();
	}
	
	public ArrayList<QuestionAnswer> studyAgain() throws IOException
	{
		BufferedReader in;
		PrintWriter out;
		timesStudied++; 
		int count=0;
		if (timesStudied%2 == 1)
		{
			FileReader reader = new FileReader("StudyQuestionsGL.out");
			FileWriter writer = new FileWriter("StudyQuestions2.out");
			in = new BufferedReader(reader); 
			out= new PrintWriter(writer); 
		}
		else
		{
			FileReader reader = new FileReader("StudyQuestions2.out");
			FileWriter writer = new FileWriter("StudyQuestionsGL.out");
			in = new BufferedReader(reader); 
			out= new PrintWriter(writer); 
		}

		String question =""; 
		String token = ""; 
		String fullAnswer="";
		String questionPart =""; 
		ArrayList<QuestionAnswer> questions = new ArrayList<QuestionAnswer>(); 
		
		question = in.readLine();
		questionPart = question; 
		question = question+"\n";
		while (question != null && token != null)
		{
			for (int i=0; i< 3; i++)
			{
				question = question + in.readLine() + "\n"; 
			}
			question = question + in.readLine();
			System.out.println(question);
			
				token = in.readLine();
		System.out.println(token); 

			if (token == null)
				break; 
			StringTokenizer tokenizer = new StringTokenizer(token); 
			
			String answer = null; 
			while (answer ==  null)
			{
				token = tokenizer.nextToken(); 
				
				if(token.equalsIgnoreCase("a."))
					answer = "A"; 
				else if (token.equalsIgnoreCase("b."))
					answer = "B"; 
				else if(token.equalsIgnoreCase("c."))
					answer = "C"; 
				else if (token.equalsIgnoreCase("d."))
					answer = "D"; 
				else if(token.equalsIgnoreCase("a"))
					answer = "A"; 
				else if (token.equalsIgnoreCase("b"))
					answer = "B"; 
				else if(token.equalsIgnoreCase("c"))
					answer = "C"; 
				else if (token.equalsIgnoreCase("d"))
					answer = "D"; 
		
			}
			// give fullanswer a value by using all the tokens remaining. 
			while (tokenizer.hasMoreTokens())
			{
				fullAnswer = fullAnswer + " " + tokenizer.nextToken(); 
			}
			QuestionAnswer questionanswer = new QuestionAnswer(); 
			questionanswer.add(question,answer,fullAnswer); 
			questionanswer.addQuestionPart(questionPart); 
			questions.add(questionanswer); 
			//space?
			in.readLine();
			question = in.readLine(); 
			fullAnswer = ""; 
		}
		return questions; 
	}
	
	public ArrayList<QuestionAnswer> scambleMixedQuestions(ArrayList<QuestionAnswer> questions, boolean randomized) throws IOException
	{
		//scramble questions and make the number of questions = numQuestions
		if (randomized)
		{
			int index; 
			QuestionAnswer temp = new QuestionAnswer();
			for (int i=0; i< questions.size(); i++)
			{
				index = (int) (Math.random() *questions.size()); 
				temp = questions.get(index); 
				questions.set(index,questions.get(i)); 
				questions.set(i, temp);
			}
		}
		
		return questions; 
	}
}
