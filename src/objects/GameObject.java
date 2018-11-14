package objects;

import render.IRenderable;

import java.util.concurrent.CountDownLatch;

public abstract class GameObject implements IRenderable{
    public final CountDownLatch cdl = new CountDownLatch(1);
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
}
