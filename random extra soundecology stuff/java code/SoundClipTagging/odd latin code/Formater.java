	import java.io.BufferedReader;
	import java.io.FileReader;
	import java.io.FileWriter;
	import java.io.IOException;
	import java.io.PrintWriter;
	import java.util.ArrayList;
	import java.util.StringTokenizer;
	
public class Formater 
{
	public Formater(String QuestionFile, String KeyFile ) throws IOException
	{
		formatKey(KeyFile); 
		formatQuestion(QuestionFile); 
	}

	public void preformatQuestion(String fileName) throws IOException 
	{
		FileReader reader = new FileReader(fileName);
		FileWriter writer = new FileWriter("Long.in");
		BufferedReader in = new BufferedReader(reader);
		PrintWriter out = new PrintWriter(writer);
		String topic = in.readLine();
		int next1 = topic.indexOf("1. ");
		String next;

		if (next1 != -1) {
			next = topic.substring(next1);
			topic = topic.substring(0, next1);
		}
		else
			next = in.readLine();

		out.println(topic);
		StringTokenizer tokenizer;
		while (next != null) 
		{
			tokenizer = new StringTokenizer(next);
			while (tokenizer.hasMoreTokens()) 
			{
				out.print(tokenizer.nextToken() + " ");
			}
			next = in.readLine();
		}
		out.close();
		
	}

	public void formatQuestion(String fileName) throws IOException 
	{
		preformatQuestion(fileName);

		FileReader reader = new FileReader("Long.in");
		FileWriter writer = new FileWriter("Questions.out");
		BufferedReader in = new BufferedReader(reader);
		PrintWriter out = new PrintWriter(writer);
		String topic = in.readLine();
		int next1 = topic.indexOf("1. ");
		String next;

		if (next1 != -1) {
			next = topic.substring(next1);
			topic = topic.substring(0, next1);
		}
		else
			next = in.readLine();

		out.println(topic);

		ArrayList<String> question = new ArrayList<String>();
		int num = 2;
		String token;
		StringTokenizer tokenizer = new StringTokenizer(next);
		while (tokenizer.hasMoreTokens()) {
			token = tokenizer.nextToken();

			if (token.equals(num + ".")) 
			{
				for (int i = 0; i < question.size(); i++)
					out.print(question.get(i));
				
				out.println();
				question.clear();
				num++;
			}

			question.add(token + " ");
		}

		// prints the last question
		for (int i = 0; i < question.size(); i++) 
		{
			out.print(question.get(i));
		}
		out.close();
	}
	
	public void formatKey(String fileName) throws IOException
	{
		FileReader reader = new FileReader(fileName);
		FileWriter writer = new FileWriter("FormatedKey.out");
		BufferedReader in = new BufferedReader(reader); 
		PrintWriter out= new PrintWriter(writer); 
		
		String title = in.readLine(); 
		out.println(title); 
		String allKey=in.readLine(); 
		StringTokenizer tokenizer = new StringTokenizer(allKey);
		while (tokenizer.hasMoreTokens())
		{
			tokenizer.nextToken();
			out.println(tokenizer.nextToken());
		}
		out.close();
	}

}

