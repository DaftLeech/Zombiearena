package objects;

import render.IRenderable;

import java.util.concurrent.CountDownLatch;

public abstract class GameObject implements IRenderable{
    public CountDownLatch cdl = new CountDownLatch(1);
}
