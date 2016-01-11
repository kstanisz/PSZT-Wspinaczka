import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JComponent;


class Wall extends JComponent 
{	  
	
	private static final long serialVersionUID = 3L;
	private static Wall instance=null;
	int x,y;
	private int rect_side = 13;
	private int oval_radius = 5;
				  
	public void paint(Graphics g) 
	{
		g.setColor(Color.LIGHT_GRAY);
		drawWall(g);
		
		g.setColor(Color.BLACK);
		drawPoints(g);
		
		g.drawLine(5+0*rect_side, 10+2*rect_side, 5+2*rect_side, 10+3*rect_side); // (2,0): (3,2)
		g.drawLine(5+0*rect_side, 10+3*rect_side, 5+1*rect_side, 10+2*rect_side); // (3,0):(2,1)
	}
	public void drawWall(Graphics g)
	{
		for (int i=5; i<=1300;i=i+13)
			for (int j=10; j<39; j=j+13)
			{
				g.drawRect(i, j, rect_side, rect_side);
			}
		g.setColor(Color.BLACK);
		g.drawRect(5, 10, 1300, 39);
	}
	public void drawPoints(Graphics g)
	{
		for(int i=0; i < ClimbingWall.getInstance().v.size(); i++) 
		{
			y = (int)ClimbingWall.getInstance().v.get(i).getX();
			x = (int)ClimbingWall.getInstance().v.get(i).getY();
			x=x*rect_side;
			y=y*rect_side;
			g.fillOval(5+x-2, 10+y-2, oval_radius, oval_radius);
		}  
	}
	public static Wall getInstance()
	{
		if(instance==null)
		{
			instance= new Wall();
		}
		
		return instance;
	}
}
class Clear
{
	void clear(Graphics g)
	{	
		/*Gui.getInstance().c_button.addActionListener(new ActionListener() 
		  {
		       @Override
		        public void actionPerformed(ActionEvent e) 
		        {
		    	   ;
		        } 
		  }); 
		*/
	}
}
