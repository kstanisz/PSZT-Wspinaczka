import java.util.Random;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

public class ClimbingWall 
{
	private static ClimbingWall instance=null;
	private static final int boundOfPoints=50;
	
	private int numberOfPoints;
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
	
	private int getRandomNumberOfPoints()
	{
		Random generator = new Random();
		return generator.nextInt(boundOfPoints);
	}
	
	private void generatePoints()
	{
		// Generuje wierzcholki startowe
		generateStartPoints();
		
		//Generuje wierzcholki koncowe
		generateEndPoints();
		
		for(int i=0;i<numberOfPoints;i++)
		{
			// Generuje wierzcholki
			Point point;
			do
			{
				point= new Point();
				
			}while(graph.containsVertex(point));
			
			graph.addVertex(point);
			
			//Dla dodanego wierzcholka dodaje krawedzie laczace go z innymi wierzcholkami, ktorych odleglosc <= 2
			for(Point other: graph.vertexSet())
			{
				if(point.equals(other))
					continue;
				
				double x= Math.pow(Math.abs(point.getX()-other.getX()), 2.0);
				double y= Math.pow(Math.abs(point.getY()-other.getY()), 2.0);
				
				if(Math.sqrt(x+y)<=2.0)
				{
					DefaultWeightedEdge e1=graph.addEdge(point, other);
					DefaultWeightedEdge e2=graph.addEdge(other, point);
					
					graph.setEdgeWeight(e1, other.getDifficulty());
					graph.setEdgeWeight(e2, point.getDifficulty());
				}
			}
		}
	}
	
	private void generateStartPoints()
	{
		Random generator= new Random();
		double x1Start= generator.nextDouble()*3.0;
		double x2Start= x1Start>=2.0? x1Start-1.0 : x1Start+1.0;
		
		startPoints[0]= new Point(x1Start,0.0);
		startPoints[1]= new Point(x2Start,0.0);
		
		graph.addVertex(startPoints[0]);
		graph.addVertex(startPoints[1]);
		
		DefaultWeightedEdge e1Start= graph.addEdge(startPoints[0], startPoints[1]);
		DefaultWeightedEdge e2Start= graph.addEdge(startPoints[1], startPoints[0]);
		graph.setEdgeWeight(e1Start, startPoints[1].getDifficulty());
		graph.setEdgeWeight(e2Start, startPoints[0].getDifficulty());
	}
	
	private void generateEndPoints()
	{
		Random generator= new Random();
		double x1End= generator.nextDouble()*3;
		double x2End= x1End>=2.0? x1End-1.0 : x1End+1.0;
		
		endPoints[0]= new Point(x1End,100.0);
		endPoints[1]= new Point(x2End,100.0);
		
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