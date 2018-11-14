package entitys;

import general.Zombiearena;
import objects.Entity;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Point;
import render.graphicmath;
import resources.ResourceManager;
import world.Map;

import java.util.ArrayList;

public class Zombie extends Entity {


    private static final int idle_AnimCount = 16;
    private static final int move_AnimCount = 16;
    private static final int attack_AnimCount = 8;
    private static Animation idle_Anim = new Animation();
    private static Animation move_Anim = new Animation();
    private static Animation attack_Anim = new Animation();
    private Point location;
    private float yaw;
    private int health;
    private STATES state = STATES.IDLE;
    private STATES lastState = state;
    private Animation curAnim = new Animation();
    private int curAnimCount = 0;
    private int frameAnim = 0;
    private int AnimOffX = 0;
    private int AnimOffY = 0;

    public Zombie(Point location) {

        this.location = location;
        this.yaw = 0.0f;
        this.health = 3;


        if (idle_Anim.getFrameCount() == 0) {
            try {
                loadAnimations();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        curAnim = idle_Anim;
        curAnimCount = idle_AnimCount;

        layer = Layer.OBJECT;
        cdl.countDown();

    }

    private static void loadAnimations() throws Exception {
        ArrayList<Image> temp;
        temp = new ArrayList<>(new ArrayList<>(ResourceManager
                .loadImageCollection("zombie/skeleton-idle_"
                        , ".png"
                        , idle_AnimCount)));
        idle_Anim = new Animation(temp.toArray(new Image[temp.size()]), 50, true);

        temp = new ArrayList<>(new ArrayList<>(ResourceManager
                .loadImageCollection("zombie/skeleton-move_"
                        , ".png"
                        , move_AnimCount)));
        move_Anim = new Animation(temp.toArray(new Image[temp.size()]), 50, false);

        temp = new ArrayList<>(new ArrayList<>(ResourceManager
                .loadImageCollection("zombie/skeleton-attack_"
                        , ".png"
                        , attack_AnimCount)));
        attack_Anim = new Animation(temp.toArray(new Image[temp.size()]), 50, false);
    }

    @Override
    public void toRender(Graphics g) {

        if(Map.location != null) return;

        g.translate(-Map.location.getX(), -Map.location.getY());
        g.rotate(this.location.getX(), this.location.getY(), this.getYaw());



        g.drawImage(curAnim.getCurrentFrame()
                , (int) getLocation().getX() - AnimOffX
                , (int) getLocation().getY() - AnimOffY
        );


        g.rotate(this.location.getX(), this.location.getY(), -this.getYaw());
        g.translate(Map.location.getX(), Map.location.getY());
        g.flush();

    }

    @Override
    public int getTickRate() {
        return 0;
    }

    @Override
    public void toThread(GameContainer container, int delta) {


        curAnim.update(delta);
        handleCurAnim();
        //yaw = graphicmath.getTargetAngle(this.location.getX(),this.location.getY(), Zombiearena.pLocal.getLocation().getX(), Zombiearena.pLocal.getLocation().getY());


    }

    private void handleCurAnim() {

        if (state == lastState)
            return;

        switch (state) {

            case IDLE:
                curAnim = idle_Anim;
                curAnimCount = idle_AnimCount;
                break;
            case MOVING:
                curAnim = move_Anim;
                curAnimCount = move_AnimCount;
                break;
            case ATTACK:
                curAnim = attack_Anim;
                curAnimCount = attack_AnimCount;
                break;

        }


    }

    public float getYaw() {
        return this.yaw;
    }

    public Point getLocation() {
        return this.location;
    }

    private enum STATES {IDLE, MOVING, ATTACK}
}
