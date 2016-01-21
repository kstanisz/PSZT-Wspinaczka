import java.util.List;

public class Start 
{
	public static void main(String[] args)
	{
		ClimbingWall climbingWall= ClimbingWall.getInstance();
		//climbingWall.readPointsFromFile();
		climbingWall.generatePoints();
		
		Point[] starts = climbingWall.getStartPoints();
		Point[] ends = climbingWall.getEndPoints();
		AStarSolver solver= new AStarSolver(new Position(starts[1], starts[1],  starts[0], starts[0]), 
								new Position(ends[0], ends[1], ends[0], ends[1]), AStarNodeComparator.INSTANCE, climbingWall);
		System.out.println("Algorytm A* rozwiazuje problem...");
		List<AStarNode> positions = solver.solve();
		
		if(positions!=null)
		{
			try 
			{
				Gui gui = Gui.getInstance();
				gui.setVisualisation(positions,solver.getCost(),solver.getDepth());
				
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
		else
		{
			System.out.println("Algorytm A* nie znalazl drogi od punktow startowych do koncowych! Liczba punktow: "+climbingWall.getGraph().vertexSet().size());
		}
	}
}
