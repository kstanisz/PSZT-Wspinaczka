import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JComponent;

class Wall extends JComponent
{	  
	private static final long serialVersionUID = 1L;
	ClimbingWall climbingWall= ClimbingWall.getInstance();
	AStarSolver c;
	LinkedList<Move> moves = new LinkedList<Move>();

	private int point_move_radius = 30;
	private int rect_side = 200; //jednostka
	private int oval_radius = 20;
	
	private int board_width = 3;
	private int board_height = 100;
	
	private int left_margin = 5;
	private int top_margin = 10;
	private int index=0;
	
	int x_start, y_start;
	private boolean stopped=true;
	
	Point current_top_right = null;
	Point current_top_left = null;
	Point current_bottom_right = null;
	Point current_bottom_left = null;
	
	private double x_move;
	private double y_move;
	private int x_average,y_average;
	
	public Wall()
	{	
		Point[] starts = climbingWall.getStartPoints();
		Point[] ends = climbingWall.getEndPoints();
		AStarSolver c = new AStarSolver(new Position(starts[1], starts[1],  starts[0], starts[0]), 
								new Position(ends[0], ends[1], ends[0], ends[1]), AStarNodeComparator.INSTANCE, climbingWall);
		List<AStarNode> positions = c.solve();
		
		Position last = null;
		for(AStarNode node : positions) {
			if(last == null)
				last = (Position)node;
			else {
				moves.add(Position.moveLimb((Position)node, last));
				last = (Position)node;
			}
		}
		for(Move m : moves) 
			System.out.println(m);
		
		setStartPoints();	
	}
		
	public void setIndex(int index)
	{
		this.index=index;
	}
	
	public final int getMovesLisSize()
	{
		return moves.size();
	}
	
	public void setStopped(boolean stopped)
	{
		this.stopped=stopped;
	}
	
	public final boolean isStopped()
	{
		return stopped;
	}
	
	public void paint(Graphics g) 
	{		
		g.setColor(Color.LIGHT_GRAY);
		drawWall(g);
			
		g.setColor(Color.BLACK);
		drawPoints(g);
		
		cleaner(g);		
		
		synchronized(this)
		{
			drawMoves(g, index);
							
			if(current_top_right != null)
				drawTopRight(g);
			if(current_top_left != null)
				drawTopLeft(g);
			if(current_bottom_right != null)
				drawBottomRight(g);
			if(current_bottom_left != null)
				drawBottomLeft(g);
			
			g.setColor(new Color(153,0,153));
			drawSpider(g);
		}

	}
	
	public void drawWall(Graphics g)
	{
		g.setColor(Color.WHITE);
		g.fillRect(left_margin, top_margin, rect_side*board_height, rect_side*board_width);

		for (int i=left_margin; i<=rect_side*board_height;i+=rect_side)
		{
			for (int j=top_margin; j<rect_side*board_width; j=j+rect_side)
			{
				g.setColor(Color.LIGHT_GRAY);
				g.drawRect(i, j, rect_side, rect_side);
			}
		}
		g.setColor(Color.BLACK);
		g.drawRect(left_margin, top_margin, rect_side*board_height, rect_side*board_width);
	}
	
	public void drawPoints(Graphics g)
	{
		for(int i=0; i < ClimbingWall.getInstance().v.size(); i++) 
		{
			int y = (int)ClimbingWall.getInstance().v.get(i).getX();
			int x = (int)ClimbingWall.getInstance().v.get(i).getY();
			double difficulty = ClimbingWall.getInstance().v.get(i).getDifficulty();
			x=left_margin+x*rect_side;
			y=top_margin+y*rect_side;
			
			if( difficulty >= 1.0  && difficulty < 2.0)
			{
				g.setColor(new Color(192, 192, 192)); //light gray
			}
			else if( difficulty >= 2.0  && difficulty < 3.0)
			{
				g.setColor(new Color(128, 128, 128));
			}
			else if( difficulty >= 3.0  && difficulty < 4.0)
			{
				g.setColor(new Color(64, 64, 64));
			}
			else if( difficulty >= 4.0  && difficulty < 5.0)
			{
				g.setColor(new Color(32, 32, 32)); //dark gray
			}
			else if( difficulty >= 5.0)
			{
				g.setColor(Color.BLACK);
			}
	
			g.fillOval(x-oval_radius/2,y-oval_radius/2, oval_radius, oval_radius);
		}  
	}
	
	public void setStartPoints()
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
				else if(i == 1)
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
						 
			
			if(moves.get(index).getLeg().toString() == "TOP_RIGHT")
			{
				current_top_right = new Point((int)y_move*rect_side,(int)x_move*rect_side);	
			}
			else if(moves.get(index).getLeg().toString() == "TOP_LEFT")
			{
				current_top_left = new Point((int)y_move*rect_side,(int)x_move*rect_side);
			}
			else if(moves.get(index).getLeg().toString() == "BOTTOM_RIGHT")
			{
				current_bottom_right = new Point((int)y_move*rect_side,(int)x_move*rect_side);
			}
			else if(moves.get(index).getLeg().toString() == "BOTTOM_LEFT")
			{
				current_bottom_left = new Point((int)y_move*rect_side,(int)x_move*rect_side);
			}		
	}
	
	public void drawTopRight(Graphics g)
	{
		g.setColor(Color.RED);
		g.fillOval(left_margin+(int)(current_top_right.getX())-point_move_radius/2, top_margin+(int)(current_top_right.getY())-point_move_radius/2, point_move_radius, point_move_radius);
	}
	
	public void drawTopLeft(Graphics g)
	{
		g.setColor(Color.GREEN);
		g.fillOval(left_margin+(int)(current_top_left.getX())-point_move_radius/2, top_margin+(int)(current_top_left.getY())-point_move_radius/2, point_move_radius, point_move_radius);
	}
	
	public void drawBottomRight(Graphics g)
	{
		g.setColor(Color.CYAN);
		g.fillOval(left_margin+(int)(current_bottom_right.getX())-point_move_radius/2, top_margin+(int)(current_bottom_right.getY())-point_move_radius/2, point_move_radius, point_move_radius);
	}
	
	public void drawBottomLeft(Graphics g)
	{
		g.setColor(Color.BLUE);
		g.fillOval(left_margin+(int)(current_bottom_left.getX())-point_move_radius/2, top_margin+(int)(current_bottom_left.getY())-point_move_radius/2, point_move_radius, point_move_radius);
	}
	
	public void drawSpider(Graphics g)
	{
		x_average = (int)((current_top_right.getX() + current_top_left.getX() + current_bottom_right.getX() + current_bottom_left.getX())/4);
		y_average = (int)((current_top_right.getY() + current_top_left.getY() + current_bottom_right.getY() + current_bottom_left.getY())/4);
				
		
	    ((Graphics2D) g).setStroke(new BasicStroke(3)); //grubosc linii

	    //legs
		g.setColor(Color.RED);
		g.drawLine(left_margin+x_average+12, top_margin+y_average+8, left_margin+(int)current_top_right.getX()-2, top_margin+(int)current_top_right.getY()-2);

		g.setColor(Color.GREEN);
		g.drawLine(left_margin+x_average+12, top_margin+y_average-8, left_margin+(int)current_top_left.getX()-2, top_margin+(int)current_top_left.getY()-2);

		g.setColor(Color.CYAN);
		g.drawLine(left_margin+x_average-8, top_margin+y_average+8, left_margin+(int)current_bottom_right.getX()-2, top_margin+(int)current_bottom_right.getY()-2);

		g.setColor(Color.BLUE);
		g.drawLine(left_margin+x_average-8, top_margin+y_average-8, left_margin+(int)current_bottom_left.getX()-2, top_margin+(int)current_bottom_left.getY()-2);
		
		
		g.setColor(new Color(153,0,153)); //purple
		g.fillOval(left_margin+x_average-10 , top_margin+y_average-12, oval_radius+20, oval_radius+20 ); //body
		
		 ((Graphics2D) g).setStroke(new BasicStroke(1));
		 
	}
	
	public void cleaner(Graphics g)
	{	
		drawWall(g);
		g.setColor(Color.BLACK);
		drawPoints(g);
	}
}