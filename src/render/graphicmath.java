package render;

import general.DPoint;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class graphicmath {

    public static AffineTransform createRotation(double yaw, DPoint location){

        return AffineTransform.getRotateInstance((-yaw+90)*Math.PI/180,location.x,location.y);

    }


    public static void drawShadow(Graphics2D g, DPoint location){

        float[] dist = {0.0f, 0.4f};
        Color[] colors = {Color.BLACK, new Color(0,0,0,0)};
        RadialGradientPaint rgp = new RadialGradientPaint(new Point((int)location.x + 5,(int)location.y + 5),40,dist,colors);
        g.setPaint(rgp);
        g.fillOval((int)location.x - 15, (int)location.y - 15, 40, 40);


    }
}
