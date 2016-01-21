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
	
	public AStarSolver(AStarNode position, AStarNode goal, Comparator<AStarNode> comp, AStarGraph graph) {
		this.position = position;
		this.goal = goal;
		this.comp = comp;
		this.graph = graph;
	}
	
	public LinkedList<AStarNode> solve() {
		int counter = 0;
		PriorityQueue<AStarNode> frontier = new PriorityQueue<AStarNode>(11, comp);
		frontier.add(position);
		HashMap<AStarNode, AStarNode> came_from = new HashMap<AStarNode, AStarNode>();
		HashMap<AStarNode, Double> cost_so_far = new HashMap<AStarNode, Double>();
		came_from.put(position, null);
		cost_so_far.put(position, new Double(0));
		while(!frontier.isEmpty()) {
			++counter;
			AStarNode current = frontier.poll();
			if(current.isEnd(goal)) {
				System.out.println(cost_so_far.get(current) + " " + counter);
				return prepareMoveList(came_from, current);
			}
			
			if(counter%1000 == 0)
				System.out.println(cost_so_far.size());
			
			List<AStarNode> nextNodes = graph.nextNodes(current);
			
			for (AStarNode node : nextNodes) {
					double new_cost = cost_so_far.get(current) + node.value();
					if(!cost_so_far.containsKey(node) || new_cost < cost_so_far.get(node)) {
						cost_so_far.put(node, new_cost);
						node.setPriority(new_cost + node.heuristic(goal));
						frontier.add(node);
						came_from.put(node, current);
					}
			}
		}
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
}
