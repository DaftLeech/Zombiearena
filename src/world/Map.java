package world;

import general.DPoint;
import general.Zombiearena;
import objects.GameObject;
import render.Render;
import resources.ResourceManager;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;

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
                for (Rectangle room : dngn.getFinalRooms()) {

                    int x = room.x;
                    int y = room.y;
                    int width = room.width;
                    int height = room.height;


                    int px = (int) (Zombiearena.pLocal.getLocation().x + location.x);
                    int py = (int) (Zombiearena.pLocal.getLocation().y + location.y);
                    double yaw = Zombiearena.pLocal.getYaw();

                    int cx = (int) (Math.cos(Math.toRadians(-yaw + 90)) * ((x-px)) + px);
                    int cy = (int) (Math.sin(Math.toRadians(-yaw + 90)) * ((y-py)) + py);

                    g.setPaint(Color.RED);


                    g.setStroke(new BasicStroke(10));
                    g.drawLine(cx, cy, cx, cy);
                    g.setStroke(new BasicStroke(1));

                }
            }

            g.translate(location.x, location.y);

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
}
