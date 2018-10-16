package combineFiles;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;

public class main {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		//		FileReader reader = new FileReader("file"); 
		//		BufferedReader in = new BufferedReader(reader); 
		//		combining multiple files with one entry into one csv 
		//		String data = "";
		//		String header = ""; 
		//		for (int i=4; i<=96; i++)
		//		{
		//			String path ="/Users/Evan/Desktop/tenMinutes/rms/dataACIrun10MIN"+ i +".csv";
		//			FileReader reader = new FileReader(path); 
		//			BufferedReader in = new BufferedReader(reader); 
		//			header = in.readLine(); 
		//			data += "\n" +in.readLine();
		//			in.close(); 
		//		}
		//		String total = header+ data; 
		//		
		//
		//		FileWriter writer = new FileWriter("/Users/Evan/Desktop/tenMinutesMedianACI.csv"); 
		//		PrintWriter out = new PrintWriter(writer); 
		//		
		//		out.print(total);
		//		out.close(); 
		//		

		//		//averaging every ten entries of a sorted data file 
		//String path1 ="/Users/Evan/Desktop/96 trials 4 30 second samples.csv";

		for (int run=1; run <=10; run++)
		{
			//MAKE SURE THE CSV IS SORTED BY FILE NAME BEFORE YOU RUN... else it doesnt do the right thing.... 
			//shit... guess i am writing this sort function for csv files in java... 
			// i think i did it... 

			//variable for amount of samples that need the median taken of them... 
			int samples =1;

			String path1 ="/Users/Evan/Desktop/real data analysis/10 runs of aci with 1 30-second samples/dataACIrun1samplesTrialRUN"+run+".csv";
			FileReader reader1 = new FileReader(path1); 
			BufferedReader in = new BufferedReader(reader1); 
			//this is the SORTED csv LOCATION... 
			String path2 ="/Users/Evan/Desktop/real data analysis/10 runs of aci with 1 30-second samples/SORTED dataACIrun1samplesTrialRUN"+run+".csv";

			String headerFirst = in.readLine(); 
			List<List<String>> Llp = new ArrayList<List<String>>();
			//read in whole csv file...
			String input1 = in.readLine(); 
			while (input1 != null)
			{

				String[] rowArr = input1.split(",");  
				List<String> rowList = Arrays.asList(rowArr); 

				Llp.add(rowList);
				input1 = in.readLine(); 
			}


			//close buffered reader and sort the data by first column field.
			in.close();
			Collections.sort(Llp, new Comparator<List<String>>() {//https://stackoverflow.com/questions/24744670/how-to-sort-data-in-a-csv-file-using-a-particular-field-in-java
				public int compare(List<String> o1, List<String> o2) {
					try {                      
						return o1.get(0).compareTo(o2.get(0));
					} catch (IndexOutOfBoundsException e) {
						return 0;
					}
				}
			});


			//write data to path2 SORTED file 
			FileWriter writer1 = new FileWriter(path2);  
			PrintWriter out1 = new PrintWriter(writer1); 

			out1.print(headerFirst +"\n");
			for (List<String> line : Llp)
			{
				for (String entry : line)
					out1.print(entry + ",");
				out1.println(); 
			}
			out1.close(); 

			/*
			 * end of sorting... beginning of median taking... 
			 */
			

			//path2 declared above 
			FileReader reader2 = new FileReader(path2); 
			in = new BufferedReader(reader2); 

			String header1 = in.readLine(); //read in header first ... 
			String input = in.readLine(); 
			//		System.out.println(input); //test to make sure i am in the right place 
			String fileInfo =""; 
			String dataFile=""; 
			while (input != null)
			{
				double[] sumR = new double[samples]; //4 to 10 this is the # of samples 
				double[] sumL =new double[samples]; 
				//			double sumQ =0; 

				for (int l=0; l<=(samples-1); l++)  // 3 to 9 this is the # of samples minus 1 
				{
					fileInfo =""; 
					StringTokenizer tokenizer = new StringTokenizer(input,","); 
					for (int r=0; r<10; r++) //this is the heading of the aci... 11 in is where the left channel is... 
					{
						fileInfo += tokenizer.nextToken() + ","; 
					}
					sumR[l] = Double.parseDouble(tokenizer.nextToken()) ; //11 is left channel
					sumL[l] = Double.parseDouble(tokenizer.nextToken()); //12 is right channel 
					//				sumQ += Double.parseDouble(tokenizer.nextToken()); //left over from RMS 

					input = in.readLine(); 
				}
				System.out.println(sumR + "  " + sumL);
				//have to sort to take the medians 
				Arrays.sort(sumL);
				Arrays.sort(sumR);

				double medianR = calculateMedian(sumR); //these should be medians 
				double medianL = calculateMedian(sumL); 
				//			double avgQ = sumQ/10; //left over shit 

				dataFile += fileInfo + medianR + "," + medianL + "\n";  
			}

			FileWriter writer = new FileWriter("/Users/Evan/Desktop/real data analysis/processed data to get the median for each sample size/1 samples of 30-second median/median ACI  1 30-second samples RUN "+run+".csv"); 
			PrintWriter out = new PrintWriter(writer); 

			out.print(header1 +"\n" + dataFile);
			out.close(); 
			

		}
	}

	/* TESTING CODE FOR MEDIAN 
	double[] testing = {1.0,2,5,-1,-2,8,9,18,-99, 4}; 
	Arrays.sort(testing);
	System.out.println(calculateMedian(testing)); 
	System.out.print(Arrays.toString(testing));
	 */
	//this code was taken from somewhere ... 
	public static double calculateMedian(double[] sortedArr)
	{	
		double median = 0;

		// If our array's length is even, then we need to find the average of the two centered values
		if (sortedArr.length % 2 == 0)
		{
			int indexA = (sortedArr.length - 1) / 2;
			int indexB = sortedArr.length / 2;

			median = ((double) (sortedArr[indexA] + sortedArr[indexB])) / 2;
		}
		// Else if our array's length is odd, then we simply find the value at the center index
		else  
		{
			int index = (sortedArr.length - 1) / 2;
			median = sortedArr[ index ];
		}


		return median;	
	}

}
