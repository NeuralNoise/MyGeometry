package geometry;

import java.util.ArrayList;

import geometry.algorithm.Intersector;

public class Ray2D extends Line2D{

	public Ray2D(Point2D startPoint, double angle) {
		this(startPoint, startPoint.getTranslation(angle, 1));
	}

	public Ray2D(Point2D startPoint, Point2D otherPoint) {
		super(startPoint, otherPoint);
	}
	
	/**
	 * Depending on the type of the "other" argument, this methods return the unique intersection.
	 * 
	 * It doesn't consider collinearity as an intersection.
	 * 
	 * @return the intersection point if there is one and only one, or "null" if there is no intersection, or more than
	 *         one.
	 */
	public Point2D getUniqueIntersection(Line2D other) {
		Intersector intersector = new Intersector(other, this);

		if(!intersector.hasUniqueLineToLineIntersection())
			return null;
		if(other instanceof Segment2D && !intersector.hasUniqueSegmentToLineIntersection())
			return null;
		
		Point2D intersection = intersector.getUniqueIntersection();
		double p1Dist = intersection.getDistance(p1);
		if(p1Dist > p0.getDistance(p1)){
			double p0Dist = intersection.getDistance(p0);
			if(p0Dist < p1Dist)
				return null;
		}
		return intersection;
	}
	
	/**
	 * Depending on the type of the "other" argument, this methods says if a unique intersection exists.
	 * 
	 * It doesn't consider collinearity as an intersection.
	 * 
	 * @return true if a unique intersection exists or false in the other cases.
	 */
	public boolean intersectAtSinglePoint(Line2D other) {
		return getUniqueIntersection(other) != null;
	}
	
	public Point2D getPointAt(double distance){
		return p0.getTranslation(getAngle(), distance);
	}
	
    /**
     * look for the intersection between segment and a circle
     * 
     * two points are computed : the first is always the nearest of p0 (start of the segment
     * 
     * if tangent, the intersection is the first.
     * @param center
     * @param radius
     * @return 
     */
    public ArrayList<Point2D> getIntersectionsWithCircle(Circle2D circle) {
    	Point2D farEnd = p0.getTranslation(getAngle(), 10000);
        ArrayList<Point2D> res = new ArrayList<>();
        // d = direction vector of the segment
        Point2D d = farEnd.getSubtraction(p0);
        // f = vector from center to ray start
        Point2D f = p0.getSubtraction(circle.center);
        
        double a = d.getDotProduct(d);
        double b = 2*f.getDotProduct(d);
        double c = f.getDotProduct(f)-circle.radius*circle.radius;

        double discriminant = b*b-4*a*c;
        if( discriminant < 0 ){
          // no intersection
            res.add(null);
            res.add(null);
            return res;
        }else{
          // ray didn't totally miss sphere,
          // so there is a solution to
          // the equation.

          discriminant = Math.sqrt(discriminant);

          // either solution may be on or off the ray so need to test both
          // t1 is always the smaller value, because BOTH discriminant and
          // a are nonnegative.
          double t1=(-b-discriminant)/(2*a);
          double t2=(-b+discriminant)/(2*a);

          // 3x HIT cases:
          //          -o->             --|-->  |            |  --|->
          // Impale(t1 hit,t2 hit), Poke(t1 hit,t2>1), ExitWound(t1<0, t2 hit), 

          // 3x MISS cases:
          //       ->  o                     o ->              | -> |
          // FallShort (t1>1,t2>1), Past (t1<0,t2<0), CompletelyInside(t1<0, t2>1)

          if(t1 >= 0 && t1 <= 1){
            // t1 is the intersection, and it's closer than t2
            // (since t1 uses -b - discriminant)
            // Impale, Poke
              res.add(p0.getTranslation(p0.getAngle(), t1));
          }
//          else
//              res.add(null);

          // here t1 didn't intersect so we are either started
          // inside the sphere or completely past it
          if(t2 >= 0 && t2 <= 1) {
            // ExitWound
            res.add(p0.getTranslation(p0.getAngle(), t2));
          }
//          else
//              res.add(null);
          // no intn: FallShort, Past, CompletelyInside
        }
        return res;
    }

	

	
}
