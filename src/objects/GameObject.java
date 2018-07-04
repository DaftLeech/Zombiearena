package objects;

import render.IRenderable;

import java.util.concurrent.CountDownLatch;

public abstract class GameObject implements IRenderable{
    public final CountDownLatch cdl = new CountDownLatch(1);
}
