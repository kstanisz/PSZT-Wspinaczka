import java.util.Comparator;

public class PositionComparator implements Comparator<Position> {
	public static final PositionComparator INSTANCE = new PositionComparator();

	  private PositionComparator() {}

	  public int compare(Position data1, Position data2) {
	    return Double.valueOf(data1.priority).compareTo(data2.priority);
	  }

	  @Override
	  public boolean equals(Object other) {
	    return other == PositionComparator.INSTANCE;
	  }

	  private Object readResolve() {
	    return INSTANCE;
	  }
}
