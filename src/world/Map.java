package world;

import general.DPoint;
import general.Zombiearena;
import objects.GameObject;
import render.Render;
import render.graphicmath;
import resources.ResourceManager;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Map extends GameObject {

    private static Dungeon dngn;
    private BufferedImage tile;
    public static DPoint location = new DPoint(0,0);
    private static final Area shape = new Area();
    public Map(){


        int height = 10000;
        int width = 10000;
        Rectangle size = new Rectangle(0,0, width, height);

        try {
            ResourceManager.loadImage("test.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
        int deepth = 3;
        dngn = new Dungeon(size,0.7, deepth);


        for(Rectangle room : dngn.getFinalRooms()){
            shape.add(new Area(room));
        }
        for(Rectangle path : dngn.getPaths()){
            shape.add(new Area(path));
        }

        Render.addToDrawables(this);

        cdl.countDown();
    }

    @Override
    public void toRender(Graphics2D g) {

        synchronized (location) {
            g.translate(-location.x, -location.y);

            g.setPaint(Color.WHITE);

            g.fill(shape);



            if(Zombiearena.pLocal != null) {
                Point pHere = Zombiearena.pLocal.getLocation().toPoint();
                pHere.translate((int)location.x,(int)location.y);
                ArrayList<Rectangle> shape = new ArrayList<>(dngn.getFinalRooms());
                shape.addAll(dngn.getPaths());
                for (Rectangle room : shape) {

                    int x = room.x;
                    int y = room.y;
                    int width = room.width;
                    int height = room.height;


                    double px = (Zombiearena.pLocal.getLocation().x + location.x);
                    double py = (Zombiearena.pLocal.getLocation().y + location.y);
                    double yaw = Zombiearena.pLocal.getYaw();

                    double px2 = (Math.cos(Math.toRadians(-yaw+90))*(10000)+ Zombiearena.pLocal.getLocation().x+location.x);
                    double py2 = (Math.sin(Math.toRadians(-yaw+90))*(10000)+ Zombiearena.pLocal.getLocation().y+location.y);

                    Point[] edges = new Point[4];
                    edges[0] = graphicmath.lineIntersect(x,y,x+width,y,(int)px,(int)py,(int)px2,(int)py2);
                    edges[1] = graphicmath.lineIntersect(x,y+height,x+width,y+height,(int)px,(int)py,(int)px2,(int)py2);
                    edges[2] = graphicmath.lineIntersect(x,y,x,y+height,(int)px,(int)py,(int)px2,(int)py2);
                    edges[3] = graphicmath.lineIntersect(x+width,y,x+width,y+height,(int)px,(int)py,(int)px2,(int)py2);


                    g.setStroke(new BasicStroke(10));
                    g.setPaint(Color.RED);

                    for(Point intersection : edges) {
                        if (intersection != null) {
                            if(!contains(intersection.x+10,intersection.y)||!contains(intersection.x-10,intersection.y)||!contains(intersection.x,intersection.y+10)||!contains(intersection.x,intersection.y-10))
                                g.drawLine(intersection.x, intersection.y, intersection.x, intersection.y);
                        }
                    }

                    g.setStroke(new BasicStroke(1));

                }
            }

            g.translate(location.x, location.y);
            g.setColor(Color.GREEN);
            if(Zombiearena.pLocal != null)
                g.drawString(String.valueOf((-Zombiearena.pLocal.getYaw()+90)*Math.PI/180),20,100);

        }
    }

    @Override
    public int getTickRate() {
        return 0;
    }

    @Override
    public void toThread(int tick) {

    }

    public Dungeon getDngn(){
        return  dngn;
    }

    public static boolean contains(DPoint point){
        Point p = point.toPoint();
        p.translate((int)location.x,(int)location.y);
        return dngn.getPaths().stream().anyMatch(r -> r.contains(p)) || dngn.getFinalRooms().stream().anyMatch(r -> r.contains(p));
    }

    public static boolean contains(int x, int y){
        Point p = new Point(x,y);
        return dngn.getPaths().stream().anyMatch(r -> r.contains(p)) || dngn.getFinalRooms().stream().anyMatch(r -> r.contains(p));
    }
}
