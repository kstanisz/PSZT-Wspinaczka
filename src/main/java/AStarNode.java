public interface AStarNode {
	
	public int hashCode();
	
	public boolean equals(Object o);
	
	public boolean isEnd(AStarNode end);
	
	public double value();
	
	public double heuristic(AStarNode goal);
	
	public void setPriority(double priority);
	
	public double getPriority();

}
