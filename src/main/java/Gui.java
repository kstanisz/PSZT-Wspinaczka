import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ScrollPane;
import java.awt.Toolkit;
import java.util.LinkedList;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;



public class Gui extends JFrame
{
	private static final long serialVersionUID = 1L;
	private static Gui instance=null;
	
	Toolkit tk = Toolkit.getDefaultToolkit();
    Dimension d = tk.getScreenSize();
	
	private int width = 600;
	private int height = 800;
	private int width_button = 100;
	private int height_button = 60;
	private static int width_of_stat = 200;
	
	private JFrame mainFrame;
	private JPanel statisticsPanel;
	private JPanel buttonPanel;
	private JButton button_start;
	private JButton button_clear;
	private JLabel label_liczba_pktow;
	private JLabel label_glebokosc;
	private JLabel label_koszt_dotarcia;
	
	public Gui() 
	{
		initialize();
	}

	private void initialize() 
	{		 
		
		// G��wny JFrame
		mainFrame=setMainFrame();
        
		//??
        ScrollPane myContainer = new ScrollPane();   
        mainFrame.add(myContainer, BorderLayout.CENTER);
        
        
        //?
	//	JScrollPane myContainer = new JScrollPane(new Wall());     
	//	myContainer.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	//	
	//	 mainFrame.add(myContainer, BorderLayout.CENTER);
        
        // Sciana
        Component  wall = new Wall();
        myContainer.add(wall);
        myContainer.setBackground(Color.WHITE);
             
		buttonPanel=setButtonPanel();
		mainFrame.add(buttonPanel, BorderLayout.SOUTH);
		statisticsPanel=setStatisticsPanel();
		mainFrame.add(statisticsPanel,  BorderLayout.NORTH);

		  
		Box box = Box.createVerticalBox();
		label_liczba_pktow=setLabelLiczbaPunktow();
		box.add(label_liczba_pktow);
		label_glebokosc=setLabelGlebokosc();
		box.add(label_glebokosc);
		label_koszt_dotarcia=setLabelKosztDotarcia();
		box.add(label_koszt_dotarcia);
		  
		statisticsPanel.add(box);  
		mainFrame.setVisible(true);
		   
	}	
	public static Gui getInstance()
	{
		if(instance==null)
		{
			instance= new Gui();
		}
		
		return instance;
	}
	
	private JFrame setMainFrame()
	{
		JFrame mainFrame = new JFrame("Wspinaczka");
		mainFrame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(d.width,350);
		mainFrame.setLocationRelativeTo(null);
            
        return mainFrame;
	}
	
	private JPanel setStatisticsPanel()
	{
		JPanel statistics = new JPanel();
		statistics.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		statistics.setLayout(new BoxLayout(statistics, BoxLayout.X_AXIS));
		statistics.setBackground(Color.WHITE);
		statistics.setBounds(0, height-(height-40), width_of_stat, 50);
		
		return statistics;
	}
	
	private JPanel setButtonPanel()
	{
		JPanel buttonPanel = new JPanel(); 		  
		buttonPanel.setBackground(Color.WHITE);
		buttonPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true));
		   
		button_start = new JButton("Start");
		button_clear = new JButton("Clear");

		button_start.setBounds((width-width_button)/2,height-150,width_button,height_button);  
		buttonPanel.add(button_start);
		buttonPanel.add(button_clear);
		
		return buttonPanel;
	}
	
	private JLabel setLabelLiczbaPunktow()
	{
		EmptyBorder border = new EmptyBorder(10, 10, 10, 20);
		JLabel label_liczba_pktow = new JLabel("Liczba punkt�w: "+ ClimbingWall.getInstance().getnumberOfPoints());
		label_liczba_pktow.setAlignmentX(Component.LEFT_ALIGNMENT);
		label_liczba_pktow.setBorder(border);
		
		return label_liczba_pktow;
	}
	
	private JLabel setLabelGlebokosc()
	{
		EmptyBorder border = new EmptyBorder(10, 10, 10, 20);
		JLabel label_glebokosc = new JLabel("G��boko��: ");
		label_glebokosc.setAlignmentX(Component.LEFT_ALIGNMENT);
		label_glebokosc.setBorder(border);
		
		return label_glebokosc;
	}
	
	private JLabel setLabelKosztDotarcia()
	{
		EmptyBorder border = new EmptyBorder(10, 10, 10, 20);
		JLabel label_koszt_dotarcia = new JLabel("Najlepszy koszt dotarcia: ");
		label_koszt_dotarcia.setAlignmentX(Component.LEFT_ALIGNMENT);
		label_koszt_dotarcia.setBorder(border);
		
		return label_koszt_dotarcia;
	}
	
	public void setVisualisation(LinkedList <Move> moves)
	{
		
	}
}
	

