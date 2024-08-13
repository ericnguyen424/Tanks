package wipcode;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

public class testClass {
    public static void main(String[] args) {
        Shape test1 = new Rectangle2D.Double(0, 0, 10, 10);
        Shape test2 = new Rectangle2D.Double(100, 0, 10, 10);
        Area testArea1 = new Area(test1);
        Area testArea2 = new Area(test2);
        System.out.println(intersects(testArea1, testArea2));
    }

    public static boolean intersects(Area areaOne, Area areaTwo) {
        if (areaOne == null || areaTwo == null) {
            throw new IllegalArgumentException();
        }
        //Get the area of the two objects

        areaOne.intersect(areaTwo);
        //The intersect function creates a new object that contains the space they both occupy
        return !areaOne.isEmpty();
    }
}
