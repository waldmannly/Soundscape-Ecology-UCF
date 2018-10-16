import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;
import javax.swing.*;


public class GUI extends JFrame
{
	//Final Variables 
	public final int HIGH =3; 
	public final int MID = 2; 
	public final int LOW = 1; 
	public static final int FRAME_WIDTH = 300; 
	public static final int FRAME_HIEGHT = 425; 
	
	//GUI variables
	private JButton exitButton; 
	private JButton confirmButton; 
	private JButton deleteButton;
	private ActionListener listener; 
	private JScrollPane addedScrollPane; 
	private JTextArea addedTextArea;
	private JTextArea addedTextArea1;
	private JTextArea addedTextArea2;
	private JComboBox numberPanel;
	private FlowLayout flow = new FlowLayout(FlowLayout.CENTER);
	private Timer timer; 
	
	//Queue variables
    private Comparator<Assignment> comparator = new AssignmentComparator();
    private PriorityQueue<Assignment> queue = new PriorityQueue<Assignment>(10, comparator);


	public GUI() throws IOException 
	{
		
		//fill queue with saved data 
		BufferedReader in = new BufferedReader(new FileReader("To-Do-List.txt"));
		//READ IN DATA
		in.readLine();
		in.readLine(); 
		String data = in.readLine(); 
		StringTokenizer tokenizer;
		String name; 
		String topic; 
		String priority; 
		while(data != null )
		{
			tokenizer = new StringTokenizer(data); 
			tokenizer.nextToken();
			name = tokenizer.nextToken();
			topic = tokenizer.nextToken();
			priority = tokenizer.nextToken();
			name = name.replace('~', ' ');
			topic = topic.replace('~', ' ');
			queue.add(new Assignment(name, topic, Integer.parseInt(priority))); 
			data = in.readLine();
		}
		in.close();
		
		
		this.setTitle("To-Do List Priority Queue"); 

		Container con = getContentPane();
		con.setLayout(flow);

		class ChoiceListener implements ActionListener, KeyListener
		{
			public void actionPerformed(ActionEvent event) 
			{
				try {
					selectTopic(event);
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
					if(event.getKeyChar() =='\t')
					{
						Object source = event.getSource();
						event.consume();
						if(source.equals(addedTextArea))
						{
							addedTextArea.setEnabled(false);
							timer = new Timer(1000, listener); 
							timer.start();
						}
						else if(source.equals(addedTextArea1))
						{
							addedTextArea1.setEnabled(false);
							timer = new Timer(100, listener); 
							timer.start();
						}
					}
					
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
		
		updateArea2();
	}

	public void createControlPanel()
	{
		add(createAddPanel1()); 
		JPanel confirmationPanel = createButtons(); 
		JPanel addPanel = createAddPanel(); 
		add(addPanel); 
		add(confirmationPanel);
	}

	public JPanel createAddPanel()
	{
		add(new JLabel("Add or Delete an Item"));

		addedTextArea = new JTextArea("");
		addedTextArea.setColumns(16);
		addedTextArea.setRows(1); 
		addedTextArea.setLineWrap(true); 
		addedTextArea.setWrapStyleWord(true);
		addedTextArea.setEditable(true);
		addedTextArea.setFont(new Font("Courier", 12, 12));
		addedTextArea.setBorder(BorderFactory.createLineBorder(Color.black));
		addedTextArea.addKeyListener((KeyListener) listener);
		
		addedTextArea1 = new JTextArea("");
		addedTextArea1.setColumns(14);
		addedTextArea1.setRows(1); 
		addedTextArea1.setLineWrap(true); 
		addedTextArea1.setWrapStyleWord(true);
		addedTextArea1.setEditable(true);
		addedTextArea1.setFont(new Font("Courier", 12, 12));
		addedTextArea1.setBorder(BorderFactory.createLineBorder(Color.black));
		addedTextArea1.addKeyListener((KeyListener) listener);
		
		numberPanel = new JComboBox(); 
		numberPanel.addItem("Low");
		numberPanel.addItem("Mid");
		numberPanel.addItem("High");
		numberPanel.setEditable(true); 

		JPanel panel = new JPanel(); 
		panel.add(addedTextArea1);
		panel.add(addedTextArea);
		panel.add(numberPanel); 

		return panel;
	}

	public JPanel createAddPanel1()
	{
		addedTextArea2 = new JTextArea("");
		addedTextArea2.setColumns(39);
		addedTextArea2.setRows(22); 
		addedTextArea2.setLineWrap(false); 
		addedTextArea2.setWrapStyleWord(false);
		addedTextArea2.setEditable(false);
		addedTextArea2.setFont(new Font("Courier", 12, 12));

		addedScrollPane = new JScrollPane(addedTextArea2, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		JPanel panel = new JPanel(); 
		panel.add(addedScrollPane);
		return panel;
	}

	public JPanel createButtons()
	{
		exitButton = new JButton("Exit"); 
		exitButton.addActionListener(listener); 

		deleteButton = new JButton("Delete"); 
		deleteButton.addActionListener(listener);

		confirmButton = new JButton("Add"); 
		confirmButton.addActionListener(listener); 

		ButtonGroup group = new ButtonGroup(); 
		group.add(confirmButton); 
		group.add(deleteButton);
		group.add(exitButton);

		JPanel panel = new JPanel(); 
		panel.add(confirmButton); 
		panel.add(deleteButton);
		panel.add(exitButton); 

		return panel; 
	}

	public void selectTopic(ActionEvent event) throws IOException
	{
		Object source = event.getSource();

		if (source instanceof JButton)
		{

			if (source == confirmButton && addedTextArea.getText().equals("") )
			{
				addedTextArea.setText("Insert information");
			}
			else if (source == confirmButton && addedTextArea1.getText().equals(""))
			{
				addedTextArea1.setText("Insert information");
			}
			else if (source == deleteButton)
			{
				//remove info from queue and update area2
				
				//find ass with same name, topic and priority
				Assignment ass = findAss(addedTextArea1.getText(),addedTextArea.getText(),numberPanel.getSelectedIndex()+1); 
				clearAreas();
				if (!(queue.remove(ass)))
				{ 
		        	addedTextArea1.setText("Info never");
		        	addedTextArea.setText("Inserted");
				}
				else
				{
					updateArea2(); 
				}
					
			}
			else if (source == confirmButton)
			{ 
				//CHECK FOR DUPLICATES
				Assignment ass = findAss(addedTextArea1.getText(),addedTextArea.getText(),numberPanel.getSelectedIndex()+1);
				if (ass == null)
				{
					//add info to queue and update area2
					ass = new Assignment(addedTextArea1.getText(),addedTextArea.getText(),numberPanel.getSelectedIndex()+1); 
		        	queue.add(ass);
		        	updateArea2(); 
		        	clearAreas();
				}
				else
				{
		        	addedTextArea1.setText("Info already");
		        	addedTextArea.setText("Inserted");
				}
		        
			}
			else if(source == exitButton)
			{
				//save list by writing to file
				PrintWriter out = new PrintWriter(new FileWriter("To-Do-List.txt"));
				String text=""; 
				String temp="";
				int count =1; 
				//heading format
				text = "#  Name          Topic         Priority\n---------------------------------------"; 
				
		        while (queue.size() != 0)
		        {
		        	Assignment ass = queue.remove(); 
		        	temp = String.format("\n%-2s %-14s%-16s%6s",count++,ass.getName().replace(' ', '~'),ass.getTopic().replace(' ', '~'),ass.getPriority());
		            text += temp; 
		        }
		        out.println(text);
				out.close();
			
				System.exit(0);
			}
		}
		else if (source instanceof Timer)
		{
			if(source.equals(timer))
			{
				addedTextArea.setEnabled(true);
				addedTextArea1.setEnabled(true);
				timer.stop(); 
			}
		}
	}

	private void updateArea2()
	{
		String text=""; 
		String temp=""; 
		PriorityQueue<Assignment> queue2 = new PriorityQueue<Assignment>(10, comparator);
		queue2.addAll(queue); 
		int count =1; 
		//heading format
		text = "#  Name          Topic         Priority\n---------------------------------------\n"; 
	 
        while (queue2.size() != 0)
        {
        	Assignment ass = queue2.remove(); 
        	temp = String.format("%-2s %-14s%-16s%6s\n",count++,ass.getName(),ass.getTopic(),ass.getPriority());
            text += temp;
        }
        addedTextArea2.setText(text); 
	}
	
	private Assignment findAss(String name, String topic, int priority)
	{
		PriorityQueue<Assignment> queue2 = new PriorityQueue<Assignment>(10, comparator);
		queue2.addAll(queue); 
        while (queue2.size() != 0)
        {
        	Assignment ass = queue2.remove();
       
        	if (ass.getName().equals(name) && ass.getTopic().equals(topic) && ass.getPriority() == priority)
        	{
        		return ass; 
        	}
        }
        return null; 
	}
	
	private void clearAreas()
	{
		addedTextArea1.setText("");
		addedTextArea.setText("");
	}
	
	private void keyTypedAction(KeyEvent event) throws Exception
	{
//	
		
	}
	
}


