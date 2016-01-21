public class Position implements AStarNode {
	Point arm0;
	Point arm1;
	Point leg0;
	Point leg1;
	private double maxStep = 2.0;
	double priority = Double.MAX_VALUE;
	
	public Position(Point arm0, Point arm1, Point leg0, Point leg1){
		this.arm0 = arm0;
		this.arm1 = arm1;
		this.leg0 = leg0;
		this.leg1 = leg1;
	}
	
	public void setPriority(double priority) {
		this.priority = priority;
	}
	
	public double getPriority() {
		return priority;
	}
	
	public boolean isValid() {
		Point p[] = {arm0, arm1, leg0, leg1};
		for (int i = 0; i < 4; ++i)
			for (int j = i+1; j < 4; ++j)
				if(Math.sqrt(
						(p[i].getX()-p[j].getX())*(p[i].getX()-p[j].getX())+(p[i].getY()-p[j].getY())*(p[i].getY()-p[j].getY())
								) > 2)
					return false;
		return Math.min(arm0.getY(), arm1.getY()) > Math.max(leg0.getY(),  leg1.getY());
	}
	
	public int hashCode() {
		int result = 0;
		Point tab[] = new Point[4];
		if(isFirstFirst(arm1, arm0)) {
			tab[0] = arm1;
			tab[1] = arm0;
		} else {
			tab[0] = arm0;
			tab[1] = arm1;
		}
		
		if(isFirstFirst(leg1, leg0)) {
			tab[2] = leg1;
			tab[3] = leg0;
		} else {
			tab[2] = leg0;
			tab[3] = leg1;
		}
		
		for(Point p : tab) {
			result = result*31 + hashDouble(p.getX());
			result = result*31 + hashDouble(p.getY());
		}
		
		
		return result;
	}
	
	private static int hashDouble(double value) {
	    long bits = Double.doubleToLongBits(value);
	    return (int)(bits ^ (bits >>> 32));
	}
	
	private boolean isFirstFirst (Point first, Point second) {
		if(first.getX() < second.getX())
			return true;
		if(first.getX() > second.getX())
			return false;
		if(first.getY() < second.getY())
			return true;
		return false;
	}
	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (o instanceof Position) {
			Position p = (Position)o;
			if((p.arm0.equals(arm0) && p.arm1.equals(arm1)) || (p.arm0.equals(arm1) && p.arm1.equals(arm0)))
				if((p.leg0.equals(leg0) && p.leg1.equals(leg1)) || (p.leg0.equals(leg1) && p.leg1.equals(leg0)))
						return true;
		}
		return false;
	}
	
	public double getRangeSum(Point goal, Point start, double[] rangeMin) {
		double sum = 0;
		int points = (int)Math.ceil( 
				Math.sqrt((goal.getY()-start.getY())*(goal.getY()-start.getY()) + 
						(goal.getX()-start.getX())*(goal.getX()-start.getX())) /2 ); // ilosc punktów do końca
		double yRange = (goal.getY() - start.getY())/points; // średnia odległość między punktami
		double startY = start.getY();
		for(int i = 0; i < points; ++i) {
			startY += yRange; // następny punkt
			double minimum = 2;
			int index = (int)startY;
			for(int j = index-6; j<index+6; ++j) {
				if(j<0 || j>goal.getY())
					continue;
				minimum = Math.min(minimum, rangeMin[j]); // minimum z zakresu
			}
			sum+=(minimum*4);
		}
		return sum;
	}
	
	public double heuristic(AStarNode goal, double[] rangeMin) {
		double sum = 0;
		Position g = (Position) goal;
		// heurystyka dla rąk - suma odległości w linii prostej rąk do celi - bierzemy minima z przedziałów
		double arm0ToGoalOne = getRangeSum(g.arm0, arm1, rangeMin);
		double arm0ToGoalTwo = getRangeSum(g.arm1, arm1, rangeMin);
		double arm1ToGoalOne = getRangeSum(g.arm0, arm0, rangeMin);
		double arm1ToGoalTwo = getRangeSum(g.arm1, arm0, rangeMin);

		// krótsza z sum
		sum += Math.min(arm0ToGoalOne + arm1ToGoalTwo, arm0ToGoalTwo + arm1ToGoalOne);
		
		//heurystyka dla nóg - suma odległości nóg od wysokości ściany-maxStep  (nie jest istotne gdzie nogi skończą swój bieg, byle ręce były na miejscu)
		//wysokość sciany - maxStep to minimalna wysokość, na którą muszą wejsć nogi
		
		double leg0ToTop = g.arm0.getY()-maxStep-leg0.getY();
		if(new Double(leg0ToTop).compareTo(new Double(0)) > 0) {
			sum += getRangeSum(new Point(leg0.getX(), g.arm0.getY() - 2 ), leg0, rangeMin);
		}
	
		double leg1ToTop = g.arm0.getY()-maxStep-leg1.getY();
		if(new Double(leg1ToTop).compareTo(new Double(0)) > 0) {
			sum += getRangeSum(new Point(leg1.getX(), g.arm0.getY() - 2 ), leg1, rangeMin);
		}
		
		return sum;
	}
	
	public double heuristic(AStarNode goal) {
		double sum = 0;
		Position g = (Position) goal;
		// heurystyka dla r¹k - suma odleg³oœci w linii prostej r¹k do celi
		double arm0ToGoalOne = Math.sqrt((g.arm0.getY()-arm1.getY())*(g.arm0.getY()-arm1.getY()) + 
						(g.arm0.getX()-arm1.getX())*(g.arm0.getX()-arm1.getX()));
		double arm0ToGoalTwo = Math.sqrt((g.arm1.getY()-arm1.getY())*(g.arm1.getY()-arm1.getY()) + 
						(g.arm1.getX()-arm1.getX())*(g.arm1.getX()-arm1.getX()));
		double arm1ToGoalOne = Math.sqrt((g.arm0.getY()-arm0.getY())*(g.arm0.getY()-arm0.getY()) + 
						(g.arm0.getX()-arm0.getX())*(g.arm0.getX()-arm0.getX()));
		double arm1ToGoalTwo = Math.sqrt((g.arm1.getY()-arm0.getY())*(g.arm1.getY()-arm0.getY()) + 
						(g.arm1.getX()-arm0.getX())*(g.arm1.getX()-arm0.getX()));
		
		double maxSum = Math.min(Math.ceil(arm0ToGoalOne/maxStep) + Math.ceil(arm1ToGoalTwo/maxStep), Math.ceil(arm1ToGoalOne/maxStep) + Math.ceil(arm0ToGoalTwo/maxStep));
		/* jeœli potrzeba minimum steps1 kroków dla np. pierwszej rêki, 
		 * i minimalna trudnoœæ ka¿dego z zaczepów na drodze to 1, to mamy juz steps1 kosztu przy dotarciu. 
		 * Trzeba tez dla ka¿dego z ruchów policzyæ sumê trudnoœci dla pozosta³ych nóg w tym ruchu, 
		 * minimalnie 3 (ka¿da noga w ruchu musi na czymœ staæ). £¹cznie mamy 4 * steps1. 
		 * Analogicznie dla drugiej rêki.
		 */
		sum += 4 * (maxSum); 
		
		//heurystyka dla nóg - suma odleg³oœci nóg od wysokoœci œciany-maxStep  (nie jest istotne gdzie nogi skoñcz¹ swój bieg, byle rêce by³y na miejscu)
		//wysokoœæ sciany - maxStep to minimalna wysokoœæ, na któr¹ musz¹ wejsæ nogi
		
		double leg0ToTop = g.arm0.getY()-maxStep-leg0.getY();
		if(new Double(leg0ToTop).compareTo(new Double(0)) < 0) {
			leg0ToTop = 0;
		}
	
		double leg1ToTop = g.arm0.getY()-maxStep-leg1.getY();
		if(new Double(leg1ToTop).compareTo(new Double(0)) < 0) {
			leg1ToTop = 0;
		}
		
		sum += 4 * (Math.ceil(leg0ToTop/maxStep) + Math.ceil(leg1ToTop/maxStep));
		
		return sum;
	}
	
	public double value() {
		return arm0.getDifficulty() + arm1.getDifficulty() + leg0.getDifficulty() + leg1.getDifficulty();
	}
	
	public void printString() {
		System.out.println("a0 : " + arm0.getX() + " , " + arm0.getY());
		System.out.println("a1 : " + arm1.getX() + " , " + arm1.getY());
		System.out.println("l0 : " + leg0.getX() + " , " + leg0.getY());
		System.out.println("l1 : " + leg1.getX() + " , " + leg1.getY());
		System.out.println();
	}

	public static void printDiff(Position last, Position current) {
		if(!last.arm0.equals(current.arm0))
			System.out.println("lewa reka na " + last.arm0.toString());
		if(!last.arm1.equals(current.arm1))
			System.out.println("prawa reka na " + last.arm1.toString());
		if(!last.leg0.equals(current.leg0))
			System.out.println("lewa noga na " + last.leg0.toString());
		if(!last.leg1.equals(current.leg1))
			System.out.println("prawa noga na " + last.leg1.toString());
		
		System.out.println();
	}

	public static Move moveLimb(Position last, Position current) {
		if(!last.arm0.equals(current.arm0)) {
			//System.out.println("lewa reka na " + last.arm0.toString());
			return new Move(last.arm0, Leg.TOP_LEFT);
		}
		if(!last.arm1.equals(current.arm1)) {
			//System.out.println("prawa reka na " + last.arm1.toString());
			return new Move(last.arm1, Leg.TOP_RIGHT);
		}
		if(!last.leg0.equals(current.leg0)) {
			//System.out.println("lewa noga na " + last.leg0.toString());
			return new Move(last.leg0, Leg.BOTTOM_LEFT);
		}
		if(!last.leg1.equals(current.leg1)) {
			//System.out.println("prawa noga na " + last.leg1.toString());
			return new Move(last.leg1, Leg.BOTTOM_RIGHT);
		}
		return null;
	}
	
	public boolean isEnd(AStarNode end) {
		if (end == null)
			return false;
		if (end instanceof Position) {
			Position p = (Position)end;
			return (arm1.equals(p.arm1) && arm0.equals(p.arm0)) || 
					(arm1.equals(p.arm0) && arm0.equals(p.arm1)) ;
		}
		return false;
	}
}
