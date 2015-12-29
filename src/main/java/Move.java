public class Move 
{
	private Point point;
	private Leg leg;
	
	Move(Point point, Leg leg)
	{
		this.point=point;
		this.leg=leg;
	}
	
	public Point getPoint()
	{
		return point;
	}
	
	public Leg getLeg()
	{
		return leg;
	}	
}
