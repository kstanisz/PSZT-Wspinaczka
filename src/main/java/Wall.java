import java.awt.Color;

import java.awt.Graphics;
import java.util.Date;
import java.util.LinkedList;
import javax.swing.JComponent;


class Wall extends JComponent 
{	  
	
	private static final long serialVersionUID = 3L;
	private static Wall instance=null;
	int x,y;
	int x_start, y_start;
	private int rect_side = 12; //jednostka
	private int oval_radius = 7;
	
	private int point_move_radius = 5;
	
	private int board_width = 3;
	private int board_height = 100;
	
	private double x_move;
	private double y_move;
	
	private int a;
	private int b;
	
	
	ClimbingWall climbingWall= ClimbingWall.getInstance();
	Climber c = new Climber(climbingWall);
	LinkedList<Move> moves =  c.climb();
	
	Point current_top_right = null;
	Point current_top_left = null;
	Point current_bottom_right = null;
	Point current_bottom_left = null;
	
	Point[] start  = new Point[2];
	
	Boolean init = true;

				  
	public void paint(Graphics g) 
	{
		if(init)
		{
		g.setColor(Color.LIGHT_GRAY);
		drawWall(g);
		
		g.setColor(Color.BLACK);
		drawPoints(g);
		
				
		drawStartPoints(g);
		
		for (int index = 0; index < moves.size(); index++)
		{
			cleaner(g);
			
			drawMoves(g, index);
			
			if(current_top_right != null)
				drawTopRight(g);
			if(current_top_left != null)
				drawTopLeft(g);
			if(current_bottom_right != null)
				drawBottomRight(g);
			if(current_bottom_left != null)
				drawBottomLeft(g);
			
			long start = new Date().getTime();
			while(new Date().getTime() - start < 500L){;}
		}
		init = false;
		}
		g.setColor(Color.LIGHT_GRAY);
		drawWall(g);
		cleaner(g);
	}
	public void drawWall(Graphics g)
	{
		for (int i=5; i<=rect_side*board_height;i=i+rect_side)
			for (int j=10; j<rect_side*board_width; j=j+rect_side)
			{
				g.drawRect(i, j, rect_side, rect_side);
			}
		g.setColor(Color.BLACK);
		g.drawRect(5, 10, rect_side*board_height, rect_side*board_width);
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
	public void drawStartPoints(Graphics g)
	{
			for (int i=0; i<2 ; i++)
			{
				y_start = (int)ClimbingWall.getInstance().v.get(i).getX();
				x_start = (int)ClimbingWall.getInstance().v.get(i).getY();
				x_start = x_start*rect_side;
				y_start = y_start *rect_side;
					
				if(i == 0)
				{
					current_top_left = new Point(x_start, y_start);
					current_bottom_left = new Point(x_start, y_start);
				}
				if(i == 1)
				{
					current_top_right = new Point(x_start, y_start);
					current_bottom_right = new Point(x_start, y_start);
				}
			}	
	}
	public void drawMoves(Graphics g,int index)
	{
		
			x_move = moves.get(index).getPoint().getX();
			y_move = moves.get(index).getPoint().getY();
			
			b =(int)x_move*rect_side;
			a =(int)y_move*rect_side;
			 
			
			
			if(moves.get(index).getLeg().toString() == "TOP_RIGHT")
			{
				current_top_right = new Point(a,b);	
			}
			else if(moves.get(index).getLeg().toString() == "TOP_LEFT")
			{
				current_top_left = new Point(a,b);
			}
			else if(moves.get(index).getLeg().toString() == "BOTTOM_RIGHT")
			{
				current_bottom_right = new Point(a,b);
			}
			else if(moves.get(index).getLeg().toString() == "BOTTOM_LEFT")
			{
				current_bottom_left = new Point(a,b);
			}		
	}
	public void drawTopRight(Graphics g)
	{
		g.setColor(Color.RED);
		g.fillOval(5+(int)(current_top_right.getX())-2, 10+(int)(current_top_right.getY())-2, point_move_radius, point_move_radius);
	}
	public void drawTopLeft(Graphics g)
	{
		g.setColor(Color.GREEN);
		g.fillOval(5+(int)(current_top_left.getX())-2, 10+(int)(current_top_left.getY())-2, point_move_radius, point_move_radius);
	}
	public void drawBottomRight(Graphics g)
	{
		g.setColor(Color.YELLOW);
		g.fillOval(5+(int)(current_bottom_right.getX())-2, 10+(int)(current_bottom_right.getY())-2, point_move_radius, point_move_radius);
	}
	public void drawBottomLeft(Graphics g)
	{
		g.setColor(Color.BLUE);
		g.fillOval(5+(int)(current_bottom_left.getX())-2, 10+(int)(current_bottom_left.getY())-2, point_move_radius, point_move_radius);
	}
	public void cleaner(Graphics g)
	{	
		g.setColor(Color.BLACK);
		drawPoints(g);
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
/*class Clear
{
	/*void Clear(Graphics g)
	{	
		Gui.getInstance().c_button.addActionListener(new ActionListener() 
		  {
		       @Override
		        public void actionPerformed(ActionEvent e) 
		        {
		    	   ;
		        } 
		  }); 
		
	}
}*/