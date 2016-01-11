import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ScrollPane;
import java.awt.Toolkit;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;



public class Gui extends JFrame
{
	private static final long serialVersionUID = 1L;
	private static Gui instance=null;
	
	Toolkit tk = Toolkit.getDefaultToolkit();
    Dimension d = tk.getScreenSize();
	
	private int width = 600;
	private int high = 800;
	private int width_button = 100;
	private int high_button = 60;
	private static int width_of_stat = 200;
	public JButton c_button;
	

	/**
	 * Create the application.
	 */
	public Gui() 
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() 
	{
		 JFrame frame = new JFrame("Wspinaczka");
         frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
         frame.setSize(d.width,350);
         frame.setLocationRelativeTo(null);
         
         
         ScrollPane myContainer = new ScrollPane();
         frame.add(myContainer, BorderLayout.CENTER);
         
         Component  wall = new Wall();
         myContainer.add(wall);
         myContainer.setBackground(Color.WHITE);
         

		  JPanel panel = new JPanel();
		  
		  JPanel statistics = new JPanel();
		 statistics.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		  statistics.setLayout(new BoxLayout(statistics, BoxLayout.X_AXIS));
		  
		 
		  panel.setBackground(Color.WHITE);
		  panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true));
		  
		  
		  statistics.setBackground(Color.WHITE);
		  statistics.setBounds(0, high-(high-40), width_of_stat, 50);

		  
		  JButton button_start = new JButton("Start");
		  JButton button_clear = new JButton("Clear");
		  c_button = button_clear;

		  button_start.setBounds((width-width_button)/2,high-150,width_button,high_button);
		  
		  panel.add(button_start);
		  panel.add(button_clear);
		    
		  
		  frame.add(panel, BorderLayout.SOUTH);
		  frame.add(statistics,  BorderLayout.NORTH);

		  
		  
		  EmptyBorder border = new EmptyBorder(10, 10, 10, 20);
		  
		  

		    Box box = Box.createVerticalBox();
		    JLabel label_liczba_pktow = new JLabel("Liczba punktów: "+ ClimbingWall.getInstance().getnumberOfPoints());
		    label_liczba_pktow.setAlignmentX(Component.LEFT_ALIGNMENT);
		    label_liczba_pktow.setBorder(border);
		    box.add(label_liczba_pktow);

		    JLabel label_glebokosc = new JLabel("Głębokość: ");
		    label_glebokosc.setAlignmentX(Component.LEFT_ALIGNMENT);
		    label_glebokosc.setBorder(border);
		    box.add(label_glebokosc);

		    JLabel label_koszt_dotarcia = new JLabel("Najlepszy koszt dotarcia: ");
		    label_koszt_dotarcia.setAlignmentX(Component.LEFT_ALIGNMENT);
		    label_koszt_dotarcia.setBorder(border);
		    box.add(label_koszt_dotarcia);
		  
		    statistics.add(box);
		  		  
		  
		  frame.setVisible(true);
		  
		  
		
		 /* button_start.addActionListener(new ActionListener() 
		  {
		       @Override
		        public void actionPerformed(ActionEvent e) 
		        {
					;
		       	} 
		   }); */
		   
	}	
	public static Gui getInstance()
	{
		if(instance==null)
		{
			instance= new Gui();
		}
		
		return instance;
	}
}
	

