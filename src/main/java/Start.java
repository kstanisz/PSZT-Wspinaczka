import java.util.LinkedList;

public class Start 
{
	public static void main(String[] args)
	{
		ClimbingWall climbingWall= ClimbingWall.getInstance();
		//climbingWall.generatePoints();
		climbingWall.readPointsFromFile();
		climbingWall.printGraph();
		Climber c = new Climber(climbingWall);
		LinkedList<Move> moves = c.climb();
		
		for(Move m : moves)
			System.out.println(m);
		
		try 
			{
				Gui gui = new Gui();
				gui.setVisualisation(moves);
				
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
	}
}
