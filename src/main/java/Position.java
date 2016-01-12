
public class Position {
	Point arm0;
	Point arm1;
	Point leg0;
	Point leg1;
	private double maxStep = 2.0;
	double priority = Double.MAX_VALUE;
	private static Point goal[];
	
	public Position(Point arm0, Point arm1, Point leg0, Point leg1){
		this.arm0 = arm0;
		this.arm1 = arm1;
		this.leg0 = leg0;
		this.leg1 = leg1;
	}
	
	public void setPriority(double priority) {
		this.priority = priority;
	}
	
	public static void setGoal(Point[] goal) {
		Position.goal = goal;
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
		StringBuffer r = new StringBuffer();
		if(arm1.getX() < arm0.getX())
			r.append(arm1.getX() + arm0.getX());
		else 
			r.append(arm0.getX() + arm1.getX());
		if(leg1.getX() < leg0.getX())
			r.append(leg1.getX() + leg0.getX());
		else 
			r.append(leg0.getX() + leg1.getX());
		return r.toString().hashCode();
	}
	
	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (o instanceof Position) {
			Position p = (Position)o;
			return p.arm0.equals(arm0) && p.arm1.equals(arm1) && p.leg0.equals(leg0) && p.leg1.equals(leg1);
		}
		return false;
	}
	
	public double heuristic() {
		double sum = 0;
		
		// heurystyka dla r¹k - suma odleg³oœci w linii prostej r¹k do celi
		double arm0ToGoalOne = Math.sqrt((goal[0].getY()-arm1.getY())*(goal[0].getY()-arm1.getY()) + 
						(goal[0].getX()-arm1.getX())*(goal[1].getX()-arm1.getX()));
		double arm0ToGoalTwo = Math.sqrt((goal[1].getY()-arm1.getY())*(goal[0].getY()-arm1.getY()) + 
						(goal[1].getX()-arm1.getX())*(goal[1].getX()-arm1.getX()));
		double arm1ToGoalOne = Math.sqrt((goal[0].getY()-arm0.getY())*(goal[0].getY()-arm0.getY()) + 
						(goal[0].getX()-arm0.getX())*(goal[1].getX()-arm0.getX()));
		double arm1ToGoalTwo = Math.sqrt((goal[1].getY()-arm0.getY())*(goal[0].getY()-arm0.getY()) + 
						(goal[1].getX()-arm0.getX())*(goal[1].getX()-arm0.getX()));
		double steps1, steps2;
		if(new Double(arm0ToGoalOne + arm1ToGoalTwo).compareTo(new Double(arm1ToGoalOne + arm0ToGoalTwo)) < 0) { // minimum odleg³oœci r¹k do takiego momentu, w którym obie s¹ na ró¿nych punktach koñcowych, sprawdzamy która kombinacja daje krótsz¹ sumê odleg³oœci
			steps1 = Math.ceil(arm0ToGoalOne/maxStep); // minimalna iloœæ przesuniêæ pierwszej rêki do pierwszego celu
			steps2 = Math.ceil(arm1ToGoalTwo/maxStep); // analogicznie
			
		} else {
			steps1 = Math.ceil(arm1ToGoalOne/maxStep); // minimalna iloœæ przesuniêæ drugiej rêki do pierwszego celu
			steps2 = Math.ceil(arm0ToGoalTwo/maxStep); // analogicznie
		}
		/* jeœli potrzeba minimum steps1 kroków dla np. pierwszej rêki, 
		 * i minimalna trudnoœæ ka¿dego z zaczepów na drodze to 1, to mamy juz steps1 kosztu przy dotarciu. 
		 * Trzeba tez dla ka¿dego z ruchów policzyæ sumê trudnoœci dla pozosta³ych nóg w tym ruchu, 
		 * minimalnie 3 (ka¿da noga w ruchu musi na czymœ staæ). £¹cznie mamy 4 * steps1. 
		 * Analogicznie dla drugiej rêki.
		 */
		sum += 4 * (steps1 + steps2); 
		
		//heurystyka dla nóg - suma odleg³oœci nóg od wysokoœci œciany-maxStep  (nie jest istotne gdzie nogi skoñcz¹ swój bieg, byle rêce by³y na miejscu)
		//wysokoœæ sciany - maxStep to minimalna wysokoœæ, na któr¹ musz¹ wejsæ nogi
		
		double leg0ToTop = goal[0].getY()-maxStep-leg0.getY();
		if(new Double(leg0ToTop).compareTo(new Double(0)) < 0) {
			leg0ToTop = 0;
		}
	
		double leg1ToTop = goal[0].getY()-maxStep-leg1.getY();
		if(new Double(leg1ToTop).compareTo(new Double(0)) < 0) {
			leg1ToTop = 0;
		}
		
		sum += 4 * (Math.ceil(leg0ToTop/maxStep) + Math.ceil(leg1ToTop/maxStep));
		
		return sum;
	}
	
	public double value() {
		return arm0.getDifficulty() + arm1.getDifficulty() + leg0.getDifficulty() + leg1.getDifficulty();
	}
	
	public boolean isEnd() {
		return (arm1.equals(goal[0]) && arm0.equals(goal[1])) || 
				(arm1.equals(goal[1]) && arm0.equals(goal[0])) ;
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
}
