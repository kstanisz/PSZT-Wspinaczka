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
	
	public String toString() {
		switch (leg) {
			case TOP_LEFT :
				return "Lewa reka na " + point.toString();
			case TOP_RIGHT :
				return "Prawa reka na " + point.toString();
			case BOTTOM_LEFT :
				return "Lewa noga na " + point.toString();
			case BOTTOM_RIGHT :
				return "Prawa noga na " + point.toString();
		}
		return "cos sie wydarzy³o z³ego :( ";
	}
}
