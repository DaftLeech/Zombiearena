package objects;

import engine.ThreadManager;
import render.Render;

public abstract class Entity extends GameObject{



    protected Entity(){
        Render.addToDrawables(this);
        ThreadManager.addToThreadList(this);
        layer = Layer.OBJECT;

    }
}
