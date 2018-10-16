
import java.io.IOException;
import java.util.concurrent.TimeUnit;



public class main {

	public static void main(String[] args) throws IOException, InterruptedException {
	
		
		//file handler 
		
		// sse data handler
		
		//prolly call file handler again
		
		// wav player... 
			// play wavs in a row -- prolly use timer -- 
			//when ever the tag button is pressed track--prooly in gui code 
		
		//write wav on timer
		
//		WavPlayer player = new WavPlayer(); 
//		//String fileToPlay = "D://Station1 Arboretum/S4A01979_20170602_100000.wav"; 
//		String fileToPlay = "N:/soundscape data 3 days/Station 1 Arboretum/S4A01979_20170602_210000.wav";
//		player.play(fileToPlay, 6);
////		TimeUnit.SECONDS.sleep(3);
//		player.end();
//		player.play(fileToPlay, 8);
////		TimeUnit.SECONDS.sleep(5);
//		player.end();
//		
//		//if marked at the end of the time period then write info to file, and copy wav. 
//		//keep track of # of seconds into clip that was tagged. 
//		
//		WavWriter writer = new WavWriter(); 
//		String path = "C:/Users/evan/Desktop/testing.txt";
		//writer.testCopy(path);
		
		
		Settings set = new Settings(); 
		 set.setAlwaysOnTop(true);
	 	 set.setLocation(0, 0);
	 	 set.setVisible(false);
	 	 
	 	 //initialize settings 
	 	 
		GUI gui = new GUI(set); 
	 	 gui.setAlwaysOnTop(true);
	 	 gui.setLocation(0, 0);
	 	 gui.setVisible(true);
	 	 
	}

}




//https://stackoverflow.com/questions/24104313/how-to-delay-in-java
//public static void main(String[] args) {
//    final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
//    executorService.scheduleAtFixedRate(App::myTask, 0, 1, TimeUnit.SECONDS);
//}
//
//private static void myTask() {
//    System.out.println("Running");
//}