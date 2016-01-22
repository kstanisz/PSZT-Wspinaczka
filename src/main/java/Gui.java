import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
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
	
    private int index=0;
    
	private int width = d.width;
	private int height = d.height;
	private int width_button = 100;
	private int height_button = 60;
	private static int width_of_stat = 200;
	private static final int DELAY_TIME=500;
	
	private JFrame mainFrame;
	private JScrollPane wallScrollPane;
	private JPanel statisticsPanel;
	private JPanel buttonPanel;
	private JButton button_start;
	private JButton button_stop;
	private JLabel label_liczba_pktow;
	private JLabel label_glebokosc;
	private JLabel label_koszt_dotarcia;
	private Wall wallPanel;
		
	private Gui(){}
	
	public void setVisualisation(List<AStarNode> positions, double cost, double depth) 
	{		
		mainFrame=setMainFrame();
                
		wallScrollPane=setWallPanel(positions);        
		mainFrame.add(wallScrollPane, BorderLayout.CENTER);
		buttonPanel=setButtonPanel();
		mainFrame.add(buttonPanel, BorderLayout.SOUTH);
		statisticsPanel=setStatisticsPanel();
		mainFrame.add(statisticsPanel,  BorderLayout.NORTH);

		Box box = Box.createVerticalBox();
		label_liczba_pktow=setLabelLiczbaPunktow();
		box.add(label_liczba_pktow);
		label_glebokosc=setLabelGlebokosc(depth);
		box.add(label_glebokosc);
		label_koszt_dotarcia=setLabelKosztDotarcia(cost);
		box.add(label_koszt_dotarcia);
		  
		statisticsPanel.add(box); 
		mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		mainFrame.setVisible(true);
		
		runVisualisation();   
	}
	
	public void runVisualisation()
	{
		TimerTask task = new TimerTask() 
		{
		      public void run() 
		      {
		    	  synchronized(wallPanel)
		    	  {
		    		  if(index<wallPanel.getMovesLisSize() && !wallPanel.isStopped())
		    		  {
		    			  wallPanel.setIndex(index);
		    			  index++;   
		    		  }
		    	  }
		        wallPanel.repaint();
		      }
		};
		Timer timer = new Timer();
		timer.schedule(task, 0, DELAY_TIME);
	}
		
	public static Gui getInstance()
	{
		if(instance==null)
		{
			instance= new Gui();
		}
		
		return instance;
	}
	
	private JScrollPane setWallPanel(List<AStarNode> positions)
	{
		
		wallPanel= new Wall(positions);
		wallPanel.setPreferredSize(new Dimension(20000+10,1000));
		wallPanel.setBackground(Color.WHITE);
		
        JScrollPane wallScrollPane = new JScrollPane(wallPanel);
        wallScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        wallScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        
        return wallScrollPane;
	}
	
	private JFrame setMainFrame()
	{
		JFrame mainFrame = new JFrame("Wspinaczka");
		mainFrame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
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
		button_stop = new JButton("Stop");
		button_start.setBounds((width-width_button)/2,height-150,width_button,height_button); 
		
		button_start.addActionListener( new ActionListener()
		{
		    public void actionPerformed(ActionEvent e)
		    {
		        wallPanel.setStopped(false);
		    }
		});
		
		button_stop.addActionListener( new ActionListener()
		{
		    public void actionPerformed(ActionEvent e)
		    {
		        wallPanel.setStopped(true);
		    }
		});
		
		buttonPanel.add(button_start);
		buttonPanel.add(button_stop);
		
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
	
	private JLabel setLabelGlebokosc(double depth)
	{
		EmptyBorder border = new EmptyBorder(10, 10, 10, 20);
		JLabel label_glebokosc = new JLabel("G��boko��: "+depth);
		label_glebokosc.setAlignmentX(Component.LEFT_ALIGNMENT);
		label_glebokosc.setBorder(border);
		
		return label_glebokosc;
	}
	
	private JLabel setLabelKosztDotarcia(double cost)
	{
		EmptyBorder border = new EmptyBorder(10, 10, 10, 20);
		JLabel label_koszt_dotarcia = new JLabel("Najlepszy koszt dotarcia: "+cost);
		label_koszt_dotarcia.setAlignmentX(Component.LEFT_ALIGNMENT);
		label_koszt_dotarcia.setBorder(border);
		
		return label_koszt_dotarcia;
	}
}