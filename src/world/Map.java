package world;

import general.DPoint;
import objects.GameObject;
import render.Render;
import render.Window;

import java.awt.*;

public class Map extends GameObject {

    Dungeon dngn;
    private final int deepth = 3;
    private final int width = 10000;
    private final int height = 10000;
    public static DPoint location = new DPoint(0,0);
    public Map(){

        Rectangle size = new Rectangle(0,0,width, height);


        dngn = new Dungeon(size,0.7,deepth);

        Render.addToDrawables(this);
        System.out.println(Math.pow(2,deepth-1));
        cdl.countDown();
    }

    @Override
    public void toRender(Graphics2D g) {

        g.setPaint(Color.green);
        for(Rectangle path : dngn.getPaths()){
            g.fill(path);
        }
        g.setPaint(Color.red);

        for(Rectangle room : dngn.getFinalRooms()){
            g.fill(room);
        }

    }

    @Override
    public int getTickRate() {
        return 0;
    }

    @Override
    public void toThread(int tick) {

    }
}
