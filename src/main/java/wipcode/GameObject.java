package wipcode;

import java.awt.*;
import java.awt.geom.Area;

abstract public interface GameObject {

    public void paintComponent(Graphics g);
    public Area getArea();
}
