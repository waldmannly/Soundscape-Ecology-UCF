import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.JFileChooser;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;


public class main {

	//FINAL VARIABLEs THAT SHOULD BE CHANGED IF PROGRAM CHANGES.
	public static final int SHOW_EVERY_WORD_WITH_COUNT_GREATER_THAN = 2; // 0 shows all
	public static final int SHOW_EVERY_WORD_WITH_NUM_LETTERS_GREATER_THAN = 1; // 0 shows all

	public static final boolean COUNT_FREQUENCY_FILES =false; 
	public static final boolean COUNT_ONLY_PHRASES = false;
	public static final boolean COUNT_PHRASES = false;
	public static final boolean CHECK_FOR_TOSSUP = false; 
	public static final boolean CHECK_ONLY_ANSWERS = false; 


	//formating finals
	public static final int RANK_SPACES = 4; 
	public static final int WORD_SPACES = 20; 
	public static final int FREQ_SPACES = 9; 
	public static final int BETW_SPACES =3;

	//global variables 
	public static PrintWriter out; 
	public static Sort_and_Fix sortFix; 
	public static HashTable_and_List myList ; 
	public static int count;
	public static ArrayList<String> commonWords; 
	public static int totalFiles =0; 
	public static int totalWords = 0; 
	public static int numberOfFilesReadFrom =0; 
	public static int progress = 0; 
	public static int LIST_SIZE;


	public static void listFilesForFolderToCount(final File folder) throws IOException
	{
		for (final File fileEntry : folder.listFiles()) 
		{
			if (fileEntry.isDirectory()) {
				listFilesForFolderToCount(fileEntry);
			} 
			else 
			{
				totalFiles++; 
				//gets the names of individual files in the folder
				String fileName = fileEntry.getName();
				//gets the path of individual files
				String filePath = fileEntry.getAbsolutePath();

				if (filePath.substring(filePath.length()-4).equals("docx"))
				{
					numberOfFilesReadFrom ++; 
					try 
					{
						FileInputStream fis = new FileInputStream(filePath);
						XWPFDocument xdoc=new XWPFDocument(OPCPackage.open(fis));
						List<XWPFParagraph> paragraphList =  xdoc.getParagraphs();

						for (XWPFParagraph paragraph: paragraphList)
						{ 
							StringTokenizer tokenizer = new StringTokenizer(paragraph.getText());
							totalWords += tokenizer.countTokens();	
						}
					} 
					catch(Exception ex) 
					{
						ex.printStackTrace();
					} 
				}

				else if (filePath.substring(filePath.length()-3).equals("doc") && !(COUNT_FREQUENCY_FILES))
				{
					numberOfFilesReadFrom ++; 
					try{
						POIFSFileSystem fis = new POIFSFileSystem(new FileInputStream(filePath));
						HWPFDocument wdDoc = new HWPFDocument(fis);
						String text = wdDoc.getDocumentText();
						StringTokenizer tokenizer = new StringTokenizer(text);
						totalWords += tokenizer.countTokens();	
					}
					catch(Exception ex) 
					{
						ex.printStackTrace();
					} 
				}

				else if (filePath.substring(filePath.length()-3).equals("pdf") && !(COUNT_FREQUENCY_FILES))
				{		
					numberOfFilesReadFrom ++; 
					try{
						PdfReader reader = new PdfReader(filePath); 
						String page ;
						for (int i=1; i< reader.getNumberOfPages()+1; i++)
						{
							page = PdfTextExtractor.getTextFromPage(reader, i); 
							StringTokenizer tokenizer = new StringTokenizer(page);
							totalWords += tokenizer.countTokens();	
						}
					}
					catch(Exception ex) 
					{
						ex.printStackTrace();
					} 
				}
				else if(COUNT_FREQUENCY_FILES && filePath.substring(filePath.length()-14).equals(" Frequency.txt"))
				{
					BufferedReader in = new BufferedReader(new FileReader(filePath));
					String input = in.readLine(); 
					while(input != null)
					{
						StringTokenizer tokenizer = new StringTokenizer(input);
						totalWords += tokenizer.countTokens();
						input = in.readLine();
					}
					in.close(); 
				}

				else if(filePath.substring(filePath.length()-3).equals("txt") && !(filePath.substring(filePath.length()-14).equals(" Frequency.txt") && !(COUNT_FREQUENCY_FILES)))
				{
					BufferedReader in = new BufferedReader(new FileReader(filePath));
					String input = in.readLine(); 
					while(input != null)
					{
						StringTokenizer tokenizer = new StringTokenizer(input);
						totalWords += tokenizer.countTokens();
						input = in.readLine();
					}
					in.close(); 
				} 
			}
		}
	}


	public static void listFilesForFolderToAdd(final File folder) throws IOException {
		for (final File fileEntry : folder.listFiles()) 
		{
			if (fileEntry.isDirectory()) {
				listFilesForFolderToAdd(fileEntry);
			} 
			else 
			{

				//gets the names of individual files in the folder
				String fileName = fileEntry.getName();

				//gets the path of individual files
				String filePath = fileEntry.getAbsolutePath();

				//	System.out.println(filePath);

				if (filePath.substring(filePath.length()-4).equals("docx"))
				{
					boolean hitTossups  = !CHECK_FOR_TOSSUP; 
					try 
					{
						FileInputStream fis = new FileInputStream(filePath);
						XWPFDocument xdoc=new XWPFDocument(OPCPackage.open(fis));
						List<XWPFParagraph> paragraphList =  xdoc.getParagraphs();

						for (XWPFParagraph paragraph: paragraphList)
						{ 
							if (hitTossups==false)
							{
								StringTokenizer tokenizer = new StringTokenizer(paragraph.getText());
								while (tokenizer.hasMoreTokens())
								{
									String token = tokenizer.nextToken();
									if (sortFix.fixWord(token).equalsIgnoreCase("Tossups") || sortFix.fixWord(token).equalsIgnoreCase("Tossup"))
										hitTossups = true;

									//System.out.println("Executing"); 
								}

							}
							else 
								myList.addToHash(paragraph.getText());
						}
					} 

					catch(Exception ex) 
					{
						ex.printStackTrace();
					} 
					if (hitTossups == false)
						System.out.println("Tossups error in " + filePath);

				}
				else if (filePath.substring(filePath.length()-3).equals("doc") && !(COUNT_FREQUENCY_FILES))
				{
					try{
						POIFSFileSystem fis = new POIFSFileSystem(new FileInputStream(filePath));
						HWPFDocument wdDoc = new HWPFDocument(fis);
						String text = wdDoc.getDocumentText();
						//System.out.println(text);
				
						myList.addToHash(text);
						//						StringTokenizer tokenizer = new StringTokenizer(text);
						//						System.out.println("pre here"); 
						//						while (tokenizer.hasMoreTokens())
						//						{
						//							if (sortFix.fixWord(tokenizer.nextToken()).equalsIgnoreCase("Tossups") || sortFix.fixWord(tokenizer.nextToken()).equalsIgnoreCase("Tossup") )
						//							{
						//								System.out.println("Here");
						//								String leftover=null; 
						//								while (tokenizer.hasMoreTokens())
						//								{
						//									leftover += " " +tokenizer.nextToken();
						//									System.out.println(leftover);
						//								}
						//								
						//								if(leftover != null)
						//									myList.addToHash(leftover);
						//							}
						//							System.out.println("here2");
						//						}
					}
					catch(Exception ex) 
					{
						ex.printStackTrace();
					} 
				}
				else if (filePath.substring(filePath.length()-3).equals("pdf") && !(COUNT_FREQUENCY_FILES))
				{		
					boolean hitTossups  = !CHECK_FOR_TOSSUP; 
					try{
						PdfReader reader = new PdfReader(filePath); 
						String page ;

						for (int i=1; i< reader.getNumberOfPages()+1; i++)
						{
							page = PdfTextExtractor.getTextFromPage(reader, i); 
							if (hitTossups==false)
							{
								StringTokenizer tokenizer = new StringTokenizer(page);
								while (tokenizer.hasMoreTokens())
								{
									String token = tokenizer.nextToken();
									if (sortFix.fixWord(token).equalsIgnoreCase("Tossups") || sortFix.fixWord(token).equalsIgnoreCase("Tossup"))
									{
										hitTossups = true;
										String leftover=null; 
										while (tokenizer.hasMoreTokens())
										{
											leftover += " " +tokenizer.nextToken();
										}

										if(leftover != null)
											myList.addToHash(leftover);
										//System.out.println(leftover); 
									}
									//System.out.println("Executing"); 
								}

							}
							else 
							{
								//System.out.println(page); 
								myList.addToHash(page);
							}
						}
					}
					catch(Exception ex) 
					{
						ex.printStackTrace();
					} 
					if (hitTossups == false)
						System.out.println("Tossups error in " + filePath);
				}
				else if(COUNT_FREQUENCY_FILES && filePath.substring(filePath.length()-14).equals(" Frequency.txt"))
				{
					BufferedReader in = new BufferedReader(new FileReader(filePath));
					String input = in.readLine(); 
					while(input != null)
					{
						System.out.println(input);
						input = in.readLine(); 
					}
					in.close(); 
				}

				else if(filePath.substring(filePath.length()-3).equals("txt") && !(filePath.substring(filePath.length()-14).equals(" Frequency.txt") && !(COUNT_FREQUENCY_FILES)))
				{
					BufferedReader in = new BufferedReader(new FileReader(filePath));
					String input = in.readLine(); 
					while(input != null)
					{
						myList.addToHash(input);
						input = in.readLine(); 
					}
					in.close();
				} 
			}

		}
	}
	

	public static boolean wordIsNotCommon(String wordStrand)
	{	
		StringTokenizer tokenizer = new StringTokenizer(wordStrand); 
		while (tokenizer.hasMoreTokens())
		{
			String word = tokenizer.nextToken();
			for (String c: commonWords)
			{
				if (c.equalsIgnoreCase(word))
					return false;
			}
		}
		return true; 
	}

	public static void printOutSubList(ArrayList<Word> subList)
	{
		for (Word x : subList)
		{
			count++; 

			//did this for formating 
			String rank = (count) +""; 
			String word = x.getWord(); 
			String freq = x.getCount()+"";
			out.print(rank); 
			for (int r=0; r< (RANK_SPACES-rank.length()+ BETW_SPACES);r++)
				out.print(" ");
			out.print(word);
			for (int r=0; r< (WORD_SPACES-word.length()+ BETW_SPACES + FREQ_SPACES -freq.length());r++)
				out.print(" ");
			out.println(freq);	

		}
	}

	public static void main(String[] args) throws IOException 
	{
		ProgressBar bar = new ProgressBar(0,7); 
		bar.setVisible(true);
		
		BufferedReader in = new BufferedReader(new FileReader("common words.txt"));

		commonWords = new ArrayList<String>(); 
		StringTokenizer tokenizer;
		String input = in.readLine(); 
		while(input != null)
		{
			tokenizer = new StringTokenizer(input);
			while(tokenizer.hasMoreTokens())
			{
				commonWords.add(tokenizer.nextToken());
			}
			input = in.readLine(); 
		}
		//System.out.println(commonWords.size());
		in.close();
		

		
		String pathOfFolder=null; 
		String pathOfTextFile=null;
		File folder=null;
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnVal = chooser.showOpenDialog(null);
		if(returnVal == JFileChooser.APPROVE_OPTION) 
		{
			//stores path 
			pathOfFolder = chooser.getSelectedFile().getAbsolutePath();
			//makes a file
			folder = new File(pathOfFolder); //"/Users/Evan/Desktop/2015 ACF REGIONALS"

			// sets path of file to write 
			pathOfTextFile = pathOfFolder + "/"+ folder.getName() +" Frequency.txt"; 
			bar.progressPlusOne();
			listFilesForFolderToCount(folder);
			
			bar.progressPlusOne();
			
			LIST_SIZE = totalWords; 
			myList = new HashTable_and_List(LIST_SIZE, SHOW_EVERY_WORD_WITH_NUM_LETTERS_GREATER_THAN,SHOW_EVERY_WORD_WITH_COUNT_GREATER_THAN,COUNT_PHRASES, COUNT_ONLY_PHRASES);  
			sortFix = new Sort_and_Fix(); 
			listFilesForFolderToAdd(folder);
			
			bar.progressPlusOne();
		}
		//add all the Word objects from the listNodes into the list
		ArrayList<Word> list = myList.toArrayList(); 
		
		bar.progressPlusOne();
		
		//write to a txt in the folder(creates one if necessary) 
		out = new PrintWriter(new FileWriter(pathOfTextFile)); 
		out.println("Folder Path: " + pathOfFolder+"\tFolder Name: " + folder.getName());
		out.println("Number of files read: "+ numberOfFilesReadFrom +"\tNumber of words examined: " + totalWords +"\tNumber of unique words: " + list.size());
		out.println("Only words with more than " +SHOW_EVERY_WORD_WITH_NUM_LETTERS_GREATER_THAN + " letter(s) were examined and only words that appeared more than "+SHOW_EVERY_WORD_WITH_COUNT_GREATER_THAN +" times are shown."); 
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		Date date = new Date();
		out.println("Time Written: "+ dateFormat.format(date) + "\t Format: MM/dd/yyyy HH:mm:ss");
		out.println();
		//print out the words in order
		out.println("Rank   Word                   Frequency");
		out.println("----   --------------------   ---------");//4   3spaces 20   3spaces 9
		
//		code to check the efficiency of hashing and of size
		int[] count1 = myList.checkListFullness(); 
		System.out.println("Num filled: "+ count1[0]+ "   list size: " + LIST_SIZE + "    Num in one list: " + count1[1]); 
		System.out.println("percent filled: " + ((double)count1[0]/(double)LIST_SIZE )* 100);
		
		bar.progressPlusOne();
	
		//sort the arrayList according to frequency
		sortFix.mSortList(list); 
	
		bar.progressPlusOne();
		
		ArrayList<Word> subList = new ArrayList<Word>();	
		count =0; 
		int currentFreq=-1; 
		for (Word x : list)
		{
			//x.getCount() > SHOW_EVERY_WORD_WITH_COUNT_GREATER_THAN  && 
			if (wordIsNotCommon(x.getWord())) //excludes word that are less than final's value. 
			{
				if (currentFreq != x.getCount())
				{
					currentFreq = x.getCount();
					//sort list alphabetically by each frequency group 
					sortFix.alphaSortList(subList);
					printOutSubList(subList);
					subList.clear(); 
					subList.add(x); 
				}
				else 
					subList.add(x); 
			}
			//fixes "fence post problem" 
			sortFix.alphaSortList(subList);
			printOutSubList(subList);
			subList.clear();
		}
		bar.progressPlusOne();
		System.out.println("Done."); 
		out.close(); 
	}

}
