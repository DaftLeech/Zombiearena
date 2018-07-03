package objects;

import engine.ThreadManager;
import render.Render;

import java.util.concurrent.CountDownLatch;

public abstract class Entity extends GameObject{



    public Entity(){
        Render.addToDrawables(this);
        ThreadManager.addToThreadList(this);

    }
}
