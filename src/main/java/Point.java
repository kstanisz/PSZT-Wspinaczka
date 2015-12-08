import java.util.Random;

public class Point 
{
	private double x;
	private double y;
	private double difficulty;
	private Random generator = new Random();
	
	private static final double boundX=3.0;
	private static final double boundY=100.0;
	private static final double boundRandom= 1.0;
	
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
		return generator.nextDouble()*boundX;
	}
	
	public double getY()
	{
		return y;
	}
	
	private double getRandomY()
	{
		return generator.nextDouble()*boundY;
	}
	
	public double getDifficulty()
	{
		return difficulty;
	}
	
	// Zwraca liczbê z przedzia³u [1,2] 
	private double getRandomDifficulty()
	{
		return generator.nextDouble()+boundRandom;
	}

	@Override
	public boolean equals(Object other)
	{		
		if(other!=null && other instanceof Point)
		{
			Point point =(Point) other;
			if(this.getX()==point.getX() && this.getY()==point.getY())
				return true;
		}
		
		return false;
	}
	
	@Override
	public String toString()
	{
		return ("x= "+this.getX()+" y= "+this.getY());
	}
}
