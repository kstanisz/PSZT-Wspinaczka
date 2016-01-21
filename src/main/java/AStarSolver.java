import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class AStarSolver {
	private AStarNode position;
	private AStarNode goal;
	private Comparator<AStarNode> comp;
	private AStarGraph graph;
	private double cost;
	private int depth = 0;
	private double[] rangeMin;
	
	public AStarSolver(AStarNode position, AStarNode goal, Comparator<AStarNode> comp, AStarGraph graph, double[] rangeMin) {
		this.position = position;
		this.goal = goal;
		this.comp = comp;
		this.graph = graph;
		this.rangeMin = rangeMin;
	}
	
	public LinkedList<AStarNode> solve() {
		PriorityQueue<AStarNode> frontier = new PriorityQueue<AStarNode>(11, comp);
		frontier.add(position);
		HashMap<AStarNode, AStarNode> came_from = new HashMap<AStarNode, AStarNode>();
		HashMap<AStarNode, Double> cost_so_far = new HashMap<AStarNode, Double>();
		HashMap<AStarNode, Integer> depth_so_far = new HashMap<AStarNode, Integer>();
		came_from.put(position, null);
		cost_so_far.put(position, new Double(0));
		depth_so_far.put(position, new Integer(0));
		while(!frontier.isEmpty()) {
			AStarNode current = frontier.poll();
			int current_depth = depth_so_far.get(current);
			if(current_depth > depth) 
				depth = current_depth;
			if(current.isEnd(goal)) {
				cost = cost_so_far.get(current);
				System.out.println("Cost: " + cost + " maxgleb: " + depth);
				return prepareMoveList(came_from, current);
			}
			
			List<AStarNode> nextNodes = graph.nextNodes(current);
			
			for (AStarNode node : nextNodes) {
					double new_cost = cost_so_far.get(current) + node.value();
					if(!cost_so_far.containsKey(node) || new_cost < cost_so_far.get(node)) {
						cost_so_far.put(node, new_cost);
						depth_so_far.put(node, new Integer(current_depth + 1));
						node.setPriority(new_cost + node.heuristic(goal, rangeMin));
						frontier.add(node);
						came_from.put(node, current);
					}
			}
		}
		cost = -1;
		System.out.println("Cost: " + cost + " maxgleb: " + depth);
		return null;
	}
	
	private LinkedList<AStarNode> prepareMoveList(HashMap<AStarNode, AStarNode> came_from, AStarNode current) {
		LinkedList<AStarNode> moves = new LinkedList<AStarNode>();
		while(current != null) {
			moves.addFirst(current);
			current = came_from.get(current);
		}
		return moves;
	}
	
	public double getCost() {
		return cost;
	}
	
	public double getDepth() {
		return depth;
	}
}
