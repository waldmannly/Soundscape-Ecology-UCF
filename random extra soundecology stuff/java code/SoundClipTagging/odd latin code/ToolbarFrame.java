import java.awt.Dimension;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;


public class ToolbarFrame extends JFrame{
	private int count =0;
	private instructionPanelGUI instructions;
	    public ToolbarFrame() {
	        
	        initUI();
	    }

	    private void initUI() {

	        JMenuBar menubar = new JMenuBar();
	        ImageIcon icon = new ImageIcon("exit.png");

	        JMenu file = new JMenu("Help");
	       

	        JMenuItem eMenuItem = new JMenuItem("Instructions");
	        eMenuItem.setMnemonic(KeyEvent.VK_E);
	        eMenuItem.setToolTipText("See Instructions");
	        eMenuItem.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent event) {
	            	if (count>= 1)
	            	{
	            		instructions.setVisible(true);
	            	}
	            	else
	            	{
	            	count++;
	                 instructions = new instructionPanelGUI(); 
	                try 
	                {
						instructions.changeInstructionsText(getInstructions());
					} 
	                catch (IOException e) 
	                {
						e.printStackTrace();
					}
	                
	                Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	                instructions.setLocation(( dim.width- 400)/32 , (dim.height- 457)/2);
	                instructions.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	                instructions.setVisible(true); 
	            	}
	            }
	        });

	        file.add(eMenuItem);

	        menubar.add(file);

	        setJMenuBar(menubar);

	        setTitle("Latin Question Generator");
	        setSize(300, 200);
	        setLocationRelativeTo(null);
	        setDefaultCloseOperation(EXIT_ON_CLOSE);
	    }
	    private JTextArea getInstructions() throws IOException
	    {
	    	JTextArea text;
	    	FileReader reader = new FileReader("Instructions.txt");
			BufferedReader in = new BufferedReader(reader);
			StringTokenizer tokenizer;
			text = new JTextArea(in.readLine() + "\n" + in.readLine() + in.readLine() + "\n \n" + in.readLine() + "\n" + in.readLine() +"\n \n "+ in.readLine() + in.readLine() +"\n \n "+ in.readLine() +"\n \n "+ in.readLine() +"\n \n "+ in.readLine() +"\n \n "+ in.readLine() +"\n \n "+ in.readLine()); 
			text.setWrapStyleWord(true);
			text.setLineWrap(true); 
			text.setColumns(30);
			text.setRows(22); 
			text.setEditable(false);
	    	return text;
	    }
	}

