package geometry.algorithm;

import geometry.Point2D;
import geometry.Polygon;
import geometry.Segment2D;

import java.util.ArrayList;

import collections.PointRing;
import geometry3D.Polygon3D;


public class Triangulator {

	private Polygon p;
	
	boolean computed;
	private ArrayList<Integer> indices = new ArrayList<Integer>();

	public Triangulator(Polygon p) {
		this.p = p;
		computed = false;
	}

        public Triangulator(Polygon3D p) {
		this.p = p.proj;
		computed = false;
	}
	
	public ArrayList<Integer> getIndices(){
		compute();
		return indices;
	}

	public void compute() {
		if(computed)
			return;
		
		Polygon work = new Polygon(p);
		while (work.size() > 3) {
//			PointRing remainingPoints = work.points;
			PointRing remainingPoints = new PointRing(work.points);
			Point2D ear = null;
			for (Point2D point : remainingPoints) {
				if (isValidEar(point, work)) {
					ear = point;
					indices.add(p.points.indexOf(ear));
					indices.add(p.points.indexOf(work.points.getPrevious(ear)));
					indices.add(p.points.indexOf(work.points.getNext(ear)));
					
					break;
				}
			}
			if(ear == null) {
				throw new RuntimeException("Polygon has no more ear which is impossible.");
			}
			remainingPoints.remove(ear);
			work = new Polygon(remainingPoints);
		}

		if (work.size() == 3) {
			indices.add(p.points.indexOf(work.points.get(0)));
			indices.add(p.points.indexOf(work.points.get(2)));
			indices.add(p.points.indexOf(work.points.get(1)));
		}
		computed = true;
	}

	private boolean isValidEar(Point2D ear, Polygon polygon) {
		Segment2D diagonal = new Segment2D(polygon.points.getPrevious(ear), polygon.points.getNext(ear));
		return polygon.hasFullyInternalDiagonal(diagonal);
	}
}
