package objects;

import engine.ThreadManager;
import render.Render;

public abstract class Entity extends GameObject{

    public Entity(){
        Render.addToDrawables(this);
        ThreadManager.addToThreadList(this);
    }
}
