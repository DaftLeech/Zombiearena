package general;

import java.awt.*;

public class DPoint {

    public final double x;
    public final double y;

    public DPoint(double x, double y){
        this.x = x;
        this.y = y;

    }

    public Point toPoint(){
        return new Point((int)x,(int)y);
    }

}
