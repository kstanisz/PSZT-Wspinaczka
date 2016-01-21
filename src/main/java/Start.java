public class Start 
{
	public static void main(String[] args)
	{
		ClimbingWall climbingWall= ClimbingWall.getInstance();
		//climbingWall.readPointsFromFile();
		climbingWall.generatePoints();
		
		try 
			{
				Gui gui = new Gui();
				gui.setVisualisation();
				
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
	}
}
