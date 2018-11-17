package objects;

import engine.ThreadManager;
import org.newdawn.slick.geom.*;
import render.Render;
import render.graphicmath;
import world.Map;

public abstract class Entity extends GameObject {

    public Point location;
    public float yaw;
    public GeomUtil.HitResult lastHitResult;

    protected Entity() {
        Render.addToDrawables(this);
        ThreadManager.addToThreadList(this);
        layer = Layer.OBJECT;

    }

    public boolean collition(float dirX, float dirY) {

        for (Circle hitbox : hitboxes) {
            hitbox.setX(hitbox.getX() + dirX);
            hitbox.setY(hitbox.getY() + dirY);

        }
        this.dirX = dirX;
        this.dirY = dirY;

        return collition();

    }

    public boolean collition() {
        //System.out.println(ThreadManager.objects.stream().filter(obj-> obj != this).noneMatch(obj -> collition(obj)));
        return ThreadManager.objects.stream().filter(obj -> obj != this).anyMatch(obj -> collition(obj));

    }

    public boolean collition(GameObject obj) {
        //System.out.println(obj.hitboxes.stream().noneMatch(hitbox -> collition(hitbox)));
        if (obj instanceof Map) {
            GeomUtil geomUtil = new GeomUtil();
            boolean ret = false;
            for (Rectangle room : Map.allRooms) {

                Line[] lines = new Line[4];
                lines[0] = new Line(room.getX(), room.getY(), room.getWidth() + room.getX(), room.getY());
                lines[1] = new Line(room.getX(), room.getY() + room.getHeight(), room.getX(), room.getY());
                lines[2] = new Line(room.getX() + room.getWidth(), room.getY(), room.getWidth() + room.getX(), room.getHeight() + room.getY());
                lines[3] = new Line(room.getX(), room.getY() + room.getHeight(), room.getX() + room.getWidth(), room.getY() + room.getHeight());

                for (Line line : lines) {
                    if (hitboxes.stream().anyMatch(hitbox -> {
                        GeomUtil.HitResult hitResult = geomUtil.intersect(hitbox, line);
                        if (hitResult != null) {
                            lastHitResult = hitResult;
                            return !Map.contains(geomUtil.intersect(hitbox, line).pt.getX(), geomUtil.intersect(hitbox, line).pt.getY(),room);

                        }
                        return false;

                    })) ret = true; ;
                }
            }
            return ret;
        }
        return obj.hitboxes.stream().anyMatch(hitbox -> collition(hitbox));

    }

    private boolean collition(Shape shape) {
        //System.out.println(hitboxes.stream().noneMatch(hitbox -> hitbox.intersects(shape)));
        return hitboxes.stream().anyMatch(hitbox -> hitbox.intersects(shape));
    }
}
