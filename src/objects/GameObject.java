package objects;

import engine.ThreadManager;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;
import render.IRenderable;

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


    public boolean collition(float dirX, float dirY){

        for(Circle hitbox : hitboxes){
            hitbox.setX(hitbox.getX()+dirX);
            hitbox.setY(hitbox.getY()+dirY);

        }
        return collition();

    }

    public boolean collition(){
        //System.out.println(ThreadManager.objects.stream().filter(obj-> obj != this).noneMatch(obj -> collition(obj)));
        return ThreadManager.objects.stream().filter(obj-> obj != this).anyMatch(obj -> collition(obj));

    }

    public boolean collition(GameObject obj){
        //System.out.println(obj.hitboxes.stream().noneMatch(hitbox -> collition(hitbox)));
        return obj.hitboxes.stream().anyMatch(hitbox -> collition(hitbox));

    }

    private boolean collition(Shape shape){
        //System.out.println(hitboxes.stream().noneMatch(hitbox -> hitbox.intersects(shape)));
        return hitboxes.stream().anyMatch(hitbox -> hitbox.intersects(shape));
    }
}
