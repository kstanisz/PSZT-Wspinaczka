import java.util.Random;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

public class ClimbingWall 
{
	private static ClimbingWall instance=null;
	//maksymalna liczba punktow
	private static final int MAX_POINTS=300;
	//minimalna liczba punktow
	private static final int MIN_POINTS=50;
	private static final double WIDTH=3.0;
	private static final double HEIGHT=100.0;
	
	private int numberOfPoints;
	private Random generator = new Random();
	private SimpleDirectedWeightedGraph<Point, DefaultWeightedEdge> graph;
	private Point[] startPoints= new Point[2];
	private Point[] endPoints= new Point[2];
	
	private ClimbingWall()
	{
		this.numberOfPoints=getRandomNumberOfPoints();
		this.graph= new SimpleDirectedWeightedGraph<Point, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		generatePoints();
	}
	
	public static ClimbingWall getInstance()
	{
		if(instance==null)
		{
			instance= new ClimbingWall();
		}
		
		return instance;
	}
	
	public SimpleDirectedWeightedGraph<Point, DefaultWeightedEdge> getGraph()
	{
		return graph;
	}
	
	public Point[] getStartPoints()
	{
		return startPoints;
	}
	
	public Point[] getEndPoints()
	{
		return endPoints;
	}
	
	// Generator ilosci wierzcholkow
	private int getRandomNumberOfPoints()
	{
		return generator.nextInt(MAX_POINTS-MIN_POINTS)+MIN_POINTS;
	}
	
	// Generujemy punkty 
	private void generatePoints()
	{
		// Generuje wierzcholki startowe
		generateStartPoints();
		
		//Generuje wierzcholki koncowe
		generateEndPoints();
		
		//Generujemy pozostale
		for(int i=0;i<(numberOfPoints-(startPoints.length+endPoints.length));i++)
		{
			Point point;
			do
			{
				point= new Point();
			
			// Zabepieczemy sie, zeby nie miec dwoch punktow o tych samych wspolrzednych
			}while(graph.containsVertex(point));
			
			graph.addVertex(point);
			
			//Dla dodanego wierzcholka dodaje krawedzie laczace go z innymi wierzcholkami, ktorych odleglosc <= 2
			for(Point other: graph.vertexSet())
			{
				if(point.equals(other))
					continue;
				
				//(x1-x2)^2
				double x= Math.pow(Math.abs(point.getX()-other.getX()), 2.0);
				//(y1-y2)^2
				double y= Math.pow(Math.abs(point.getY()-other.getY()), 2.0);
				
				if(Math.sqrt(x+y)<=2.0)
				{
					// Definiujemy krawedzie
					DefaultWeightedEdge e1=graph.addEdge(point, other);
					DefaultWeightedEdge e2=graph.addEdge(other, point);
					
					// Definiujemy wagi
					graph.setEdgeWeight(e1, other.getDifficulty());
					graph.setEdgeWeight(e2, point.getDifficulty());
				}
			}
		}
	}
	
	//Generowanie punktow startowych
	private void generateStartPoints()
	{	
		// Losowy punkt z przedialu 0.0 - 3.0
		double x1Start= generator.nextDouble()*WIDTH;
		// Na prawo o 1.0 lub na lewo o 1.0 od pierwszego
		double x2Start= x1Start>=WIDTH/2? x1Start-1.0 : x1Start+1.0;
		
		startPoints[0]= new Point(x1Start,0.0);
		startPoints[1]= new Point(x2Start,0.0);
		
		graph.addVertex(startPoints[0]);
		graph.addVertex(startPoints[1]);
		
		// Definiujemy krawedzie
		DefaultWeightedEdge e1Start= graph.addEdge(startPoints[0], startPoints[1]);
		DefaultWeightedEdge e2Start= graph.addEdge(startPoints[1], startPoints[0]);
		// Nadajemy wagi krawedziom
		graph.setEdgeWeight(e1Start, startPoints[1].getDifficulty());
		graph.setEdgeWeight(e2Start, startPoints[0].getDifficulty());
	}
	
	// Analogicznie do generowania punktow startowych
	private void generateEndPoints()
	{
		double x1End= generator.nextDouble()*WIDTH;
		double x2End= x1End>=WIDTH/2? x1End-1.0 : x1End+1.0;
		
		endPoints[0]= new Point(x1End,HEIGHT);
		endPoints[1]= new Point(x2End,HEIGHT);
		
		graph.addVertex(endPoints[0]);
		graph.addVertex(endPoints[1]);
		
		DefaultWeightedEdge e1End= graph.addEdge(endPoints[0], endPoints[1]);
		DefaultWeightedEdge e2End= graph.addEdge(endPoints[1], endPoints[0]);
		graph.setEdgeWeight(e1End, endPoints[1].getDifficulty());
		graph.setEdgeWeight(e2End, endPoints[0].getDifficulty());
	}
	
	public void printGraph()
	{
		for(DefaultWeightedEdge e: graph.edgeSet())
		{
			System.out.println(e);
		}
	}
}