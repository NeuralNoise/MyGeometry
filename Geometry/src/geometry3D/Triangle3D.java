/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package geometry3D;

import geometry.Point2D;
import geometry3D.Point3D;
import tools.LogUtil;

/**
 *
 * @author Benoît
 */
public class Triangle3D {
    public Point3D a;
    public Point3D b;
    public Point3D c;
    
    public Point3D normal;
    
    public Triangle3D(Point3D a, Point3D b, Point3D c) {
        this.a = a;
        this.b = b;
        this.c = c;
        try
        {
            normal = b.getSubtraction(a).getCross(c.getSubtraction(a));
        normal = normal.getDivision(normal.getNorm());
        } catch (Exception e) {
            LogUtil.logger.info(""+this);
            
        }
    }
    
    public boolean shareVert(Triangle3D o){
        return a.equals(o.a) || a.equals(o.b) || a.equals(o.c) ||
                b.equals(o.a) || b.equals(o.b) || b.equals(o.c) ||
                c.equals(o.a) || c.equals(o.b) || c.equals(o.c);
    }
    
    /**
     * the plan is defined bye ax+by+cz+d = 0, where (a, b, c) is the normal vector
     * @param p
     * @return 
     */
    public Point3D getElevated(Point2D p) {
        double factorD = -(normal.x*a.x+normal.y*a.y+normal.z*a.z);
        double z = -(normal.x*p.x+normal.y*p.y+factorD)/normal.z;
        return new Point3D(p.x, p.y, z);
    }
    
    public Triangle3D getRotationAroundZ(double angle){
        return new Triangle3D(a.get2D().getRotation(angle).get3D(a.z),
                b.get2D().getRotation(angle).get3D(b.z),
                c.get2D().getRotation(angle).get3D(c.z));
    }
    
    public Triangle3D getTranslation(double x, double y, double z){
        return new Triangle3D(a.getAddition(x, y, z),
                b.getAddition(x, y, z),
                c.getAddition(x, y, z));
    }
    public Triangle3D getTranslation(Point3D o){
        return new Triangle3D(a.getAddition(o),
                b.getAddition(o),
                c.getAddition(o));
    }

    @Override
    public String toString() {
        return a+"-"+b+"-"+c;
    }
    
    
}
