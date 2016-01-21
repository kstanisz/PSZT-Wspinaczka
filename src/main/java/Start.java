import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Start 
{
	public static void main(String[] args)
	{
		ClimbingWall climbingWall= ClimbingWall.getInstance();
		climbingWall.readPointsFromFile();
		
		try 
			{
				Gui gui = new Gui();
				
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
	}
}
