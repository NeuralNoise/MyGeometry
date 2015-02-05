package collections;

import geometry.Point2D;
import geometry.Segment2D;
import geometry.Transform2D;

import java.util.ArrayList;
import java.util.List;

public class Polyline2D extends ArrayList<Segment2D>{
	
	Point2D initialPoint = null;
	
	public Polyline2D() {
	}
	
	public Polyline2D(Point2D initialPoint){
		this.initialPoint = initialPoint;
	}
	
	public void addPoint(Point2D p){
		if(isEmpty())
			if(initialPoint == null)
				initialPoint = p;
			else
				add(new Segment2D(initialPoint, p));
		else
			add(new Segment2D(get(size()-1).getEnd(), p));
	}
	
	@Override
	public void clear() {
		super.clear();
		initialPoint = null;
	}

	public boolean contains(Point2D p){
		for(Segment2D s : this)
			if(s.contains(p))
				return true;
		return false;
	}
	
	public Polyline2D getTransformed(Transform2D transform){
		Polyline2D res = new Polyline2D();
		for(Segment2D s : this)
			res.add(s.getTransformed(transform));
		return res;
	}
}
