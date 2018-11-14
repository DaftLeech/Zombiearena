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


    public static Point lineIntersect(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        double denom = (y4 - y3) * (x2 - x1) - (x4 - x3) * (y2 - y1);
        if (denom == 0.0) { // Lines are parallel.
            return null;
        }
        double ua = ((x4 - x3) * (y1 - y3) - (y4 - y3) * (x1 - x3))/denom;
        double ub = ((x2 - x1) * (y1 - y3) - (y2 - y1) * (x1 - x3))/denom;
        if (ua >= 0.0f && ua <= 1.0f && ub >= 0.0f && ub <= 1.0f) {
            // Get the intersection point.
            return new Point((int) (x1 + ua*(x2 - x1)), (int) (y1 + ua*(y2 - y1)));
        }

        return null;
    }

    public static int PointDistance(Point p1, Point p2){
        return Math.abs(p1.x-p2.x+p1.y-p2.y);
    }

    public static int PointDistance(int x1, int y1, int x2, int y2){
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
