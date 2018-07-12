package general;

import java.awt.*;

public class DPoint {

    public final float x;
    public final float y;

    public DPoint(float x, float y){
        this.x = x;
        this.y = y;

    }

    public Point toPoint(){
        return new Point((int)x,(int)y);
    }

}
