import java.util.Random;

public class Point 
{
	private double x;
	private double y;
	private double difficulty;
	private Random generator = new Random();
	
	private static final double WIDTH=3.0;
	private static final double HEIGHT=100.0;
	private static final double MIN_DIFFICULTY= 1.0;
	
	Point()
	{
		this.x=getRandomX();
		this.y=getRandomY();
		this.difficulty= getRandomDifficulty();
	}
	
	Point(double x, double y)
	{
		this.x=x;
		this.y=y;
		this.difficulty= getRandomDifficulty();
	}
	
	public double getX()
	{
		return x;
	}
	
	private double getRandomX()
	{
		return generator.nextDouble()*WIDTH;
	}
	
	public double getY()
	{
		return y;
	}
	
	private double getRandomY()
	{
		return generator.nextDouble()*HEIGHT;
	}
	
	public double getDifficulty()
	{
		return difficulty;
	}
	
	// Zwraca liczbê z przedzia³u [1,2] 
	private double getRandomDifficulty()
	{
		return generator.nextDouble()+MIN_DIFFICULTY;
	}

	// Porownuje dwa punkty na podstawie wspolrzednych.
	@Override
	public boolean equals(Object other)
	{		
		if(other!=null && other instanceof Point)
		{
			Point point =(Point) other;
			return (this.getX()==point.getX() && this.getY()==point.getY());
		}
		
		return false;
	}
	
	@Override
	public String toString()
	{
		return ("x= "+this.getX()+" y= "+this.getY());
	}
}
