package objects;

import engine.ThreadManager;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Shape;
import render.IRenderable;
import render.graphicmath;
import world.Map;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public abstract class GameObject implements IRenderable{
    public final CountDownLatch cdl = new CountDownLatch(1);
    public ArrayList<Circle> hitboxes = new ArrayList<>();
    public static enum Layer {BASE,HUD,OBJECT};
    public static Layer layer = Layer.BASE;
    public int LayerToInt(){
        int retVal = 0;
        switch (layer){
            case BASE:retVal = 0; break;
            case OBJECT:retVal = 1;break;
            case HUD:retVal = 2;break;

        }
        return retVal;
    }
    public float dirX;
    public float dirY;



}
