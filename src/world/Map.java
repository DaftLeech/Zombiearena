package world;

import general.DPoint;
import objects.GameObject;
import render.Render;
import render.Window;
import resources.ResourceManager;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;

public class Map extends GameObject {

    private static Dungeon dngn;
    private final int deepth = 3;
    private final int width = 10000;
    private final int height = 10000;
    private BufferedImage tile;
    public static DPoint location = new DPoint(0,0);
    public static Area shape = new Area();
    public Map(){

        Rectangle size = new Rectangle(0,0,width, height);

        try {
            ResourceManager.loadImage("test.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
        dngn = new Dungeon(size,0.7,deepth);


        for(Rectangle room : dngn.getFinalRooms()){
            shape.add(new Area(room));
        }
        for(Rectangle path : dngn.getPaths()){
            shape.add(new Area(path));
        }

        Render.addToDrawables(this);
        System.out.println(Math.pow(2,deepth-1));
        cdl.countDown();
    }

    @Override
    public void toRender(Graphics2D g) {

        synchronized (location) {
            g.translate(-location.x, -location.y);

            g.setPaint(Color.WHITE);
        /*for(Rectangle path : dngn.getPaths()){
            //g.fill(path);

        }
        //g.setPaint(Color.red);

        for(Rectangle room : dngn.getFinalRooms()){
            g.fill(room);
        }*/
            g.fill(shape);

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
