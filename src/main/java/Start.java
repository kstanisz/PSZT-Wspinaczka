public class Start 
{
	public static void main(String[] args)
	{
		ClimbingWall climbingWall= ClimbingWall.getInstance();
		//climbingWall.generatePoints();
		climbingWall.readPointsFromFile();
		climbingWall.printGraph();
	}
}
