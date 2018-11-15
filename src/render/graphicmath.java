package render;


import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Point;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class graphicmath {

    public static AffineTransform createRotation(double yaw, Point location){


        return AffineTransform.getRotateInstance((-yaw+90)*Math.PI/180,location.getX(),location.getY());

    }


    public static void drawShadow(Graphics g, Point location){

        float[] dist = {0.0f, 0.4f};
        Color[] colors = {Color.black, new Color(0,0,0,0)};
        //RadialGradientPaint rgp = new RadialGradientPaint(new Point((int)location.getX() + 5,(int)location.getY() + 5),40,dist,colors);
        //g.setPaint(rgp);
        g.fillOval((int)location.getX() - 15, (int)location.getY() - 15, 40, 40);


    }


    public static Point lineIntersect(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4) {
        float denom = (y4 - y3) * (x2 - x1) - (x4 - x3) * (y2 - y1);
        if (denom == 0.0f) { // Lines are parallel.
            return null;
        }
        float ua = ((x4 - x3) * (y1 - y3) - (y4 - y3) * (x1 - x3))/denom;
        float ub = ((x2 - x1) * (y1 - y3) - (y2 - y1) * (x1 - x3))/denom;
        if (ua >= 0.0f && ua <= 1.0f && ub >= 0.0f && ub <= 1.0f) {
            // Get the intersection point.
            return new Point((x1 + ua*(x2 - x1)), (y1 + ua*(y2 - y1)));
        }

        return null;
    }

    public static float PointDistance(Point p1, Point p2){
        return p1.getX()-p2.getY()+p1.getX()-p2.getY();
    }

    public static float PointDistance(int x1, int y1, int x2, int y2){
        return PointDistance(new Point(x1,y1),new Point(x2,y2));
    }

    public static float getDistanceBetween(float startX, float startY, float endX, float endY) {
        float dx = endX - startX;
        float dy = endY - startY;
        return (float)Math.sqrt(dx*dx + dy*dy);
    }

    public static float getTargetAngle(float startX, float startY, float targetX, float targetY) {
        float dx = targetX - startX;
        float dy = targetY - startY;
        return (float)Math.toDegrees(Math.atan2(dy, dx));
    }

}
