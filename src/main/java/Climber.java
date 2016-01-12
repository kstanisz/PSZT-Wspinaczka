import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

public class Climber {
	private ClimbingWall wall;
	private Position position;
	private Point goal[];
	private SimpleDirectedWeightedGraph<Point, DefaultWeightedEdge> graph;
	
	public Climber(ClimbingWall wall) {
		this.wall = wall;
		Point[] starts = wall.getStartPoints();
		Position.setGoal(wall.getEndPoints());
		this.position = new Position(starts[0], starts[1], starts[0], starts[1]);
		this.graph = wall.getGraph();
	}
	
	public LinkedList<Move> climb() {
		PriorityQueue<Position> frontier = new PriorityQueue<Position>(11, PositionComparator.INSTANCE);
		frontier.add(position);
		HashMap<Position, Position> came_from = new HashMap<Position, Position>();
		HashMap<Position, Double> cost_so_far = new HashMap<Position, Double>();
		came_from.put(position, null);
		cost_so_far.put(position, new Double(0));
		int level = -1;
		while(!frontier.isEmpty()) {
			++level;
			Position current = frontier.poll();
			if(current.isEnd()) {
				return prepareMoveList(came_from, current);
			}
			
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
					if(i < 2) {
						if(level != 0 && !pos.isValid()) 
							continue;
					} else {
						if(!pos.isValid()) 
							continue;
					}
					double new_cost = cost_so_far.get(current) + pos.value();
					if(!cost_so_far.containsKey(pos) || new_cost < cost_so_far.get(pos)) {
						cost_so_far.put(pos, new_cost);
						pos.setPriority(new_cost + pos.heuristic());
						frontier.add(pos);
						came_from.put(pos, current);
						if(pos.heuristic() > pos.value() + current.heuristic())
							System.err.println("Nie zgadza sie heurystyka");
						if(current.heuristic() > current.value() + pos.heuristic())
							System.out.println("Nie zgadza sie heurystyka");
					}
				}
			}
		}
		return null;
	}
	
	private LinkedList<Move> prepareMoveList(HashMap<Position, Position> came_from, Position current) {
		LinkedList<Move> moves = new LinkedList<Move>();
		Position last = current;
		current = came_from.get(current);
		while(current != null) {
			moves.addFirst(Position.moveLimb(last, current));
			last = current;
			current = came_from.get(current);
			
		}
		return moves;
	}
}
