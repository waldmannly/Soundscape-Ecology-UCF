import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.*;

public class GUI extends JFrame
{
	//Final Variables 
	public static final int FRAME_WIDTH = 500; 
	public static final int FRAME_HIEGHT = 400; 
	
	//GUI variables
	public JFrame settingsFrame; 
	private JButton exitButton; 
	private JButton tagButton; 
	private JButton pauseButton;
	private JButton settingsButton;
	private ActionListener listener; 
	private JScrollPane addedScrollPane; 
	private JTextArea addedTextArea;
	private JComboBox numberPanel;
	private FlowLayout flow = new FlowLayout(FlowLayout.CENTER);
	private Timer timer; 
	
	private ArrayList<String[]> SSEdata;
	private int current =0; 
	private long currentStartedAtTime; 
	private String currentFilePath; 
	private WavPlayer player; 
	private PrintWriter out; 
	private SSEDataHandler SSEdh; 
	
	private boolean isPaused = false; 
	private long timePassed; 

	public GUI(Settings set) throws IOException 
	{
		settingsFrame = set; 
		
		this.setTitle("Soundscape Ecology Audio Tagging"); 
		
		Container con = getContentPane();
		con.setLayout(flow);

		class ChoiceListener implements ActionListener, KeyListener
		{
			public void actionPerformed(ActionEvent event) 
			{
				try {
					try {
						selectTopic(event);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			public void keyTyped(KeyEvent event)
			{
				try{
					keyTypedAction(event);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			public void keyPressed(KeyEvent event)
			{
				try{
//					if(event.getKeyChar() =='\t')
//					{
						Object source = event.getSource();
						event.consume();
//						if(source.equals(addedTextArea))
//						{
//							addedTextArea.setEnabled(false);
//							timer = new Timer(1000, listener); 
//							timer.start();
//						}
//						else if(source.equals(addedTextArea1))
//						{
////							addedTextArea1.setEnabled(false);
//							timer = new Timer(100, listener); 
//							timer.start();
//						}
//					}
					
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			public void keyReleased(KeyEvent event)
			{
				try{

				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		listener = new ChoiceListener(); 
		createControlPanel(); 
		setSize(FRAME_WIDTH, FRAME_HIEGHT); 
		addedTextArea.setText("Welcome"); 
		
		//check for continued data??? 
		
		
		
		updateArea("Reading data from index data...");
		
        File log = new File("storedTags.txt");
        BufferedReader in = new BufferedReader(new FileReader(log)); 
        String temp=""; 
        String temp2 ="";
        while (temp != null)
        {
        	temp2 = temp; 
        	temp = in.readLine(); 
		}
        StringTokenizer tokenizer = new StringTokenizer(temp2);
        tokenizer.nextToken(); 
        tokenizer.nextToken(); 
        try {
        	current = Integer.parseInt(tokenizer.nextToken()); 
        }
        catch (Exception e) {
        	System.out.println("Could not find a past stopping point...");
        	e.printStackTrace();
        }
        in.close();
        out = new PrintWriter(new FileWriter(log, true));
		
		SSEdh = new SSEDataHandler(); 
		SSEdata = SSEdh.getDataList(null); 
		
		//load very first sound file... 
		try {
			player = new WavPlayer();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	
		currentFilePath = FileHandler.convertToPath(SSEdata.get(++current)[0]); 
		player.load(currentFilePath, Integer.parseInt(SSEdata.get(current)[13].trim()));
		
		updateArea("Starting soundscape data shortly...");
		
		timer = new Timer(500, listener); 
		timer.start();
	}

	public void createControlPanel()
	{
		add(createAddPanel1()); 
		JPanel confirmationPanel = createButtons(); 
//		JPanel addPanel = createAddPanel(); 
//		add(addPanel); 
		add(confirmationPanel);
		JPanel pan = new JPanel(); 
		pan.setBounds(new Rectangle(150, 150 ));
		settingsButton = new JButton("Settings"); 
		settingsButton.addActionListener(listener);
		settingsButton.setEnabled(false);
		pan.add(settingsButton);  
		add(pan); 
	}

	public JPanel createAddPanel1()
	{
		addedTextArea = new JTextArea("");
		addedTextArea.setColumns(65);
		addedTextArea.setRows(17); 
		addedTextArea.setLineWrap(false); 
		addedTextArea.setWrapStyleWord(false);
		addedTextArea.setEditable(false);
		addedTextArea.setFont(new Font("Courier", 12, 12));

		addedScrollPane = new JScrollPane(addedTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		JPanel panel = new JPanel(); 
		panel.add(addedScrollPane);
		return panel;
	}

	public JPanel createButtons()
	{
		exitButton = new JButton("Exit"); 
		exitButton.addActionListener(listener); 

		pauseButton = new JButton("Pause/Unpause"); 
		pauseButton.addActionListener(listener);

		tagButton = new JButton("Tag"); 
		tagButton.addActionListener(listener); 

		ButtonGroup group = new ButtonGroup(); 
		group.add(tagButton); 
		group.add(pauseButton);
		group.add(exitButton);

		JPanel panel = new JPanel(); 
		panel.add(tagButton); 
		panel.add(pauseButton);
		panel.add(exitButton); 

		return panel; 
	}

	public void selectTopic(ActionEvent event) throws IOException, InterruptedException
	{
		Object source = event.getSource();

		if (source instanceof JButton)
		{
			if (source == pauseButton)
			{
			
				if (isPaused)
				{
					isPaused = false; 
					pauseButton.setEnabled(false);
					settingsButton.setEnabled(false);
					updateArea("\tResuming " + SSEdata.get(current)[0] +" for " + (5000 - (int)(timePassed*1000))/1000 + " seconds" );
					player.playLoaded();
					currentStartedAtTime = System.currentTimeMillis()/1000; 
					
					timer = new Timer(5000 - (int)(timePassed*1000), listener); 
					timer.start();
					
					currentFilePath = FileHandler.convertToPath(SSEdata.get(++current)[0]); 
					player.load(currentFilePath, Integer.parseInt(SSEdata.get(current)[13].trim()));
					
				
				}
				else 
				{
					long pausedAtTime = (System.currentTimeMillis())/1000; 
					timePassed = pausedAtTime - currentStartedAtTime ; 
					settingsButton.setEnabled(true);
					timer.removeActionListener(listener);
					timer.stop();
					player.end();
					
					TimeUnit.MILLISECONDS.sleep(100); 
					updateArea("\tPausing "+ SSEdata.get(current)[0] + " at " +(Integer.parseInt(SSEdata.get(current)[13].trim()) + (int)timePassed));
					isPaused = true; 
					
					player.resumeLoad((Integer.parseInt(SSEdata.get(current)[13].trim()) + (int)timePassed));
//					currentFilePath = FileHandler.convertToPath(SSEdata.get(current)[0]); 
//					player.(currentFilePath, (Integer.parseInt(SSEdata.get(current)[13].trim()) + (int)timePassed) );
				}
				
				
				//end the player
				//you can get the time that has past. store it
				//load up the current file at the time that it started at plus the past time
				
					
			}
			else if (source == tagButton)
			{ 
				long taggedTime = System.currentTimeMillis()/1000; 
				updateArea("tagging "+SSEdata.get(current)[0] +" at "+ (taggedTime-currentStartedAtTime+Integer.parseInt(SSEdata.get(current)[13].trim()) )+ " index: "+ SSEdata.get(current)[5] +" with value: " + SSEdata.get(current)[11] );
				//write that this file was tagged (and note the file before it) 
		        out.println(SSEdata.get(current)[0]+ " index: "+ SSEdata.get(current)[5] +" with value: " + SSEdata.get(current)[11] + " tagged at "+ (currentStartedAtTime - taggedTime+Integer.parseInt(SSEdata.get(current)[13].trim())));
		        
			}
			else if(source == exitButton)
			{
				//save and system.exit(0)
				out.println("STOPPED AT: " + current +"  "+  SSEdata.get(current)[0]); 
				out.close();
				player.end();
				System.exit(0);
			}
			else if(source == settingsButton)
			{
				settingsFrame.setVisible(true);
				settingsFrame.setAlwaysOnTop(true);
			}
			
		}
		else if (source instanceof Timer)
		{
			if(source.equals(timer))
			{
				//we can use this to continuously play soundscape recordings. 
				//play one that was previously loaded 
				
				//load up one so that there is less of a wait. 
				timer.stop(); 
				player.end();
				TimeUnit.MILLISECONDS.sleep(100);
				pauseButton.setEnabled(true);
				//String fileToPlay = "D://Station1 Arboretum/S4A01979_20170602_100000.wav"; 
//				"N:/soundscape data 3 days/Station 1 Arboretum/S4A01979_20170602_210000.wav";
				player.playLoaded();
				currentStartedAtTime = System.currentTimeMillis()/1000; 
				updateArea(current+": Playing " + SSEdata.get(current)[0]);
				timer = new Timer(5000, listener); 
				timer.start();
//				TimeUnit.SECONDS.sleep(3);
				currentFilePath = FileHandler.convertToPath(SSEdata.get(++current)[0]); 
				player.load(currentFilePath, Integer.parseInt(SSEdata.get(current)[13].trim()));
				
				
				
			
			}
		}
	}

	private void updateArea(String input)
	{
		String text = addedTextArea.getText();
        addedTextArea.setText(text + "\n"+ input); 
	}
	
	private void keyTypedAction(KeyEvent event) throws Exception
	{
//	
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
//	public JPanel createAddPanel()
//	{
//		add(new JLabel("Add or Delete an Item"));
//
//		addedTextArea = new JTextArea("");
//		addedTextArea.setColumns(16);
//		addedTextArea.setRows(1); 
//		addedTextArea.setLineWrap(true); 
//		addedTextArea.setWrapStyleWord(true);
//		addedTextArea.setEditable(true);
//		addedTextArea.setFont(new Font("Courier", 12, 12));
//		addedTextArea.setBorder(BorderFactory.createLineBorder(Color.black));
//		addedTextArea.addKeyListener((KeyListener) listener);
//		
//		addedTextArea1 = new JTextArea("");
//		addedTextArea1.setColumns(14);
//		addedTextArea1.setRows(1); 
//		addedTextArea1.setLineWrap(true); 
//		addedTextArea1.setWrapStyleWord(true);
//		addedTextArea1.setEditable(true);
//		addedTextArea1.setFont(new Font("Courier", 12, 12));
//		addedTextArea1.setBorder(BorderFactory.createLineBorder(Color.black));
//		addedTextArea1.addKeyListener((KeyListener) listener);
//		
//		numberPanel = new JComboBox(); 
//		numberPanel.addItem("Low");
//		numberPanel.addItem("Mid");
//		numberPanel.addItem("High");
//		numberPanel.setEditable(true); 
//
//		JPanel panel = new JPanel(); 
//		panel.add(addedTextArea1);
//		panel.add(addedTextArea);
//		panel.add(numberPanel); 
//
//		return panel;
//	}
}


