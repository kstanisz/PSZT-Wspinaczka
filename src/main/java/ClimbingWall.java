import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

public class ClimbingWall implements AStarGraph
{
	private static ClimbingWall instance=null;
	//maksymalna liczba punktow
	private static final int MAX_POINTS=2000;
	//minimalna liczba punktow
	private static final int MIN_POINTS=50;
	private static final double WIDTH=3.0;
	private static final double HEIGHT=100.0;
	private static final int PRECISION=2;
	private static final String FILE_NAME="input.txt";
	
	private int numberOfPoints;
	private Random generator = new Random();
	private SimpleDirectedWeightedGraph<Point, DefaultWeightedEdge> graph;
	private Point[] startPoints= new Point[2];
	private Point[] endPoints= new Point[2];
	
	Vector<Point> v = new Vector<Point>(); //vector zawierajacy wszystkie punkty
	
	private ClimbingWall()
	{
		this.numberOfPoints=getRandomNumberOfPoints();
		this.graph= new SimpleDirectedWeightedGraph<Point, DefaultWeightedEdge>(DefaultWeightedEdge.class);
	}
	
	// Generator ilosci wierzcholkow
	private int getRandomNumberOfPoints()
	{
		return generator.nextInt(MAX_POINTS-MIN_POINTS)+MIN_POINTS;
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
		
	// Generujemy punkty 
	public void generatePoints()
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
			setEdgesToThePoint(point);
		}
	}
	
	//Generowanie punktow startowych
	private void generateStartPoints()
	{	
		// Losowy punkt z przedialu 0.0 - 3.0
		BigDecimal firstX = new BigDecimal(generator.nextDouble()*WIDTH).setScale(PRECISION,RoundingMode.HALF_UP);
		double x1Start= firstX.doubleValue();
		
		startPoints[0]= new Point(x1Start,0.0);
		startPoints[1]= new Point(x1Start,1.0);
		
		graph.addVertex(startPoints[0]);
		graph.addVertex(startPoints[1]);
		
		setEdgesBetweenPoints(startPoints[0], startPoints[1]);
	}
	
	// Analogicznie do generowania punktow startowych
	private void generateEndPoints()
	{
		BigDecimal firstX = new BigDecimal(generator.nextDouble()*WIDTH).setScale(PRECISION,RoundingMode.HALF_UP);
		double x1End= firstX.doubleValue();
		double x2End= x1End>=WIDTH/2? x1End-1.0 : x1End+1.0;
		
		endPoints[0]= new Point(x1End,HEIGHT);
		endPoints[1]= new Point(x2End,HEIGHT);
		
		graph.addVertex(endPoints[0]);
		graph.addVertex(endPoints[1]);
		
		setEdgesBetweenPoints(endPoints[0], endPoints[1]);
	}
	
	// Dodajemy krawedzie miedzy dwoma danymi punktami
	private void setEdgesBetweenPoints(Point firstPoint,Point secondPoint)
	{
		//Definiujemy krawedzie
		DefaultWeightedEdge firstEdge= graph.addEdge(firstPoint, secondPoint);
		DefaultWeightedEdge secondEdge= graph.addEdge(secondPoint, firstPoint);

		//Nadajemy wagi krawedziom
		if(firstEdge!=null)
			graph.setEdgeWeight(firstEdge, secondPoint.getDifficulty());
		if(secondEdge!=null)
			graph.setEdgeWeight(secondEdge, firstPoint.getDifficulty());
	}
	
	//Dla dodanego wierzcholka dodaje krawedzie laczace go z innymi wierzcholkami, ktorych odleglosc <= 2
	private void setEdgesToThePoint(Point point)
	{
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
				setEdgesBetweenPoints(point, other);
			}
		}
	}
	
	// Odczytywanie punktow z pliku
	public void readPointsFromFile()
	{
		try 
		{
		    BufferedReader in = new BufferedReader(new FileReader(FILE_NAME)); 
		    
		    String line;
		    double[] pointInfo;
		    
		    // Odczytujemy punkty startowe
		    for(int i=0;i<startPoints.length;i++)
		    {
		    	line = in.readLine();
		    	pointInfo=getPointInfoFromLine(line);
		    	startPoints[i]=createPointAndAddVertex(pointInfo);
		    	setEdgesToThePoint(startPoints[i]);
		    	
		    	v.addElement(startPoints[i]);
		    }
		    
		    //Odczytujemy punkty koncowe 
		    for(int i=0;i<endPoints.length;i++)
		    {		    	
		    	line = in.readLine();
		    	pointInfo=getPointInfoFromLine(line);
		    	endPoints[i]=createPointAndAddVertex(pointInfo);
		    	setEdgesToThePoint(endPoints[i]);
		    	
		    	v.addElement(endPoints[i]);
		    }
		    
		    //Odczytujemy pozostale punkty
		    while ((line = in.readLine()) != null && !line.isEmpty())
		    {
		    	pointInfo=getPointInfoFromLine(line);
		    	Point point=createPointAndAddVertex(pointInfo);
		    	setEdgesToThePoint(point);
		    	
		    	v.addElement(point);
		    }
		
		    in.close();
		} catch (IOException e) 
		{
			System.out.println("Blad odczytu pliku "+FILE_NAME+": "+e);
		}
		
		numberOfPoints=graph.vertexSet().size();
	}
	
	// Odczytujemy z linii dane punktu (x,y,difficulty)
	private double[] getPointInfoFromLine(String line)
	{
		String[] splittedLine = line.split(" ");
		return new double[] { Double.parseDouble(splittedLine[0]), Double.parseDouble(splittedLine[1]), Double.parseDouble(splittedLine[2]) };
	}
	
	// Tworzymy punkt i dodajemy wierzcholek na podstawie informacji z linii
	private Point createPointAndAddVertex(double[] pointInfo)
	{
		Point point= new Point(pointInfo[0],pointInfo[1],pointInfo[2]);
		graph.addVertex(point);
		return point;
	}
	
	public void printGraph()
	{
		for(DefaultWeightedEdge e: graph.edgeSet())
		{
			System.out.println(e);
		}
	}
	public int getnumberOfPoints()
	{
		return this.numberOfPoints;
	}

	public List<AStarNode> nextNodes(AStarNode position) {
		List<AStarNode> nextNodes = new LinkedList<AStarNode>();
		Position current = (Position)position;
		Point limbs[] = {current.arm0, current.arm1, current.leg0, current.leg1 };
		for (int i = 0; i < 4; ++i) {
			for (DefaultWeightedEdge edge : graph.outgoingEdgesOf(limbs[i]) ) {
				Point p = graph.getEdgeTarget(edge);
				Position pos = null;
				switch(i) {
					case 0 : pos = new Position(p, current.arm1, current.leg0, current.leg1);
							break;
					case 1 : pos = new Position(current.arm0, p, current.leg0, current.leg1);
							break;
					case 2 : pos = new Position(current.arm0, current.arm1, p, current.leg1);
							break;
					case 3 : pos = new Position(current.arm0, current.arm1, current.leg0, p);
							break;
				}
				if(pos.isValid()) 
					nextNodes.add(pos);	
			}
		}
		return nextNodes;
	}
}
