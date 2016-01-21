import java.util.Comparator;


public class AStarNodeComparator implements Comparator<AStarNode> {
	public static final AStarNodeComparator INSTANCE = new AStarNodeComparator();

	  private AStarNodeComparator() {}

	  public int compare(AStarNode data1, AStarNode data2) {
		  return Double.valueOf(data1.getPriority()).compareTo(data2.getPriority());
	  }

	  @Override
	  public boolean equals(Object other) {
	    return other == AStarNodeComparator.INSTANCE;
	  }

	  private Object readResolve() {
	    return INSTANCE;
	  }
}

