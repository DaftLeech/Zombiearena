package entitys;

import engine.Listener;
import objects.Entity;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Point;
import render.Camera;
import render.graphicmath;
import resources.ResourceManager;
import settings.Settings;
import weapons.AbstractWeapon;
import weapons.pistole;
import weapons.rifle;
import world.Map;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Player extends Entity {


    private Point location;
    private float yaw;
    private int health;
    private static BufferedImage sprite;
    private Animation feetAnim_run;
    private ArrayList<Image> feet;
    private Animation curAnim = new Animation();
    private final int feetAnim_run_imageCount = 20;
    private boolean isRunning = false;
    private int frameAnim = 0;
    private int frameCount = 0;

    public static final int IDLE = 0;
    public static final int MELEEATTACK = 1;
    public static final int MOVE = 2;
    public static final int RELOAD = 3;
    public static final int SHOOT = 4;
    private int curState = 0;
    private final int animOffY = 70;
    private final int animOffX = 68;


    private rifle rifleWeapon;
    private pistole pistoleWeapon;
    private AbstractWeapon curWeapon;


    public Player(Point location) {
        super();

        this.location = location;
        this.yaw = 0.0f;
        this.health = 3;

        if (sprite == null) {
            try {
                sprite = ResourceManager.loadImage("Top_Down_Survivor/rifle/idle/survivor-idle_rifle_0.png");
                feet = new ArrayList<Image>(ResourceManager.loadImageCollection("Top_Down_Survivor/feet/run/survivor-run_", ".png", feetAnim_run_imageCount));
                feetAnim_run = new Animation(feet.toArray(new Image[feet.size()]), 50, false);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        curWeapon = null;
        curAnim = feetAnim_run;

        cdl.countDown();

    }

    @Override
    public void toRender(Graphics g) {


        g.translate(-(Map.location.getX()), -(Map.location.getY()));
        g.rotate(this.location.getX(), this.location.getY(), this.yaw);


        if (curAnim.getFrameCount() > 0) {
            g.drawImage(curAnim.getCurrentFrame(), getLocation().getX() - animOffX, getLocation().getY() - animOffY);

        }


        g.setColor(Color.red);
        g.drawLine((int) location.getX(), (int) location.getY(), (int) (Math.cos(Math.toRadians(-yaw + 90)) * (10000) + location.getX()), (int) (Math.sin(Math.toRadians(-yaw + 90)) * (10000) + location.getY()));

        g.setLineWidth(2f);
        g.setColor(Color.red);

        g.drawLine((int) getLocation().getX() - 5, (int) getLocation().getY(), (int) getLocation().getX() - 5, (int) getLocation().getY());
        g.setColor(Color.red);



        g.rotate(this.location.getX(), this.location.getY(), -this.yaw);

        Point dir = new Point((float) Math.cos(Math.toRadians(yaw)), (float) Math.sin(Math.toRadians(yaw)));

        g.drawLine(getLocation().getX(), getLocation().getY(), getLocation().getX() + dir.getX() * 30F, getLocation().getY() + dir.getY() * 30F);

        g.translate(Map.location.getX(), Map.location.getY());
        g.drawString(String.valueOf(curWeapon.collition()),50,500);

        g.flush();

    }

    @Override
    public void toThread(GameContainer container, int delta) {

        Input input = container.getInput();

        handleKeyInput(input, delta);

        setYaw(new Point(input.getMouseX(), input.getMouseY()));
        handleCurAnim();
        curAnim.update(delta);




    }

    @Override
    public int getTickRate() {
        return 0;
    }

    public Point getLocation() {
        return this.location;
    }

    private void setLocation(Point location) {
        this.location = location;
    }

    private void setLocation(float x, float y) {
        this.location = new Point(x, y);
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void removeHealth(int value) {
        this.health -= value;
    }

    public void addHealth(int value) {
        this.health += value;
    }

    public int getHealth() {
        return this.health;
    }

    public float getYaw() {
        return this.yaw;
    }

    public void setYaw(int value) {
        this.yaw = value;
    }

    private void setYaw(Point pLookAt) {
        this.yaw = graphicmath.getTargetAngle(this.location.getX(), this.location.getY(), Map.location.getX() + pLookAt.getX(), Map.location.getY() + pLookAt.getY());
        //(float)Math.toDegrees(Math.atan2((pLookAt.getX()+Map.location.getX() - this.location.getX()), (pLookAt.getY()+Map.location.getY()- this.location.getY()))*-1)+90;
    }

    private void setIsShooting(Boolean value) {
        Boolean isShooting = value;
    }

    private void handleKeyInput(Input input, int delta) {

        int moveMod = (int) ((Listener.getKeyCodes().size() == 1 ? 2 : Math.sqrt(4)));
        if (input.isKeyDown(Input.KEY_ESCAPE)) System.exit(0);
        synchronized (this) {

            if (curWeapon != null) {
                synchronized (curWeapon) {
                    if (0 < curWeapon.waitFactor || curWeapon.breakAnimation) {
                        curState = IDLE;
                        this.isRunning = false;
                        if (input.isKeyDown(Input.KEY_W) || input.isKeyDown(Input.KEY_S) || input.isKeyDown(Input.KEY_A) || input.isKeyDown(Input.KEY_D)) {
                            this.isRunning = true;
                            curState = MOVE;
                        }
                        if (input.isKeyDown(Input.KEY_R)) {

                            curState = RELOAD;
                        }
                        if (input.isKeyDown(Input.KEY_V)) {

                            curState = MELEEATTACK;
                        }
                        if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
                            setIsShooting(true);
                            curState = SHOOT;
                        }
                    }
                }
            }


            if (Settings.boolByName("MovementType")) {

                if (input.isKeyDown(Input.KEY_W)) {
                    setLocation(getLocation().getX(), getLocation().getY() - moveMod);
                }

                if (input.isKeyDown(Input.KEY_S)) {
                    setLocation(getLocation().getX(), getLocation().getY() + moveMod);
                }

                if (input.isKeyDown(Input.KEY_A)) {
                    setLocation(getLocation().getX() - moveMod, getLocation().getY());
                }

                if (input.isKeyDown(Input.KEY_D)) {
                    setLocation(getLocation().getX() + moveMod, getLocation().getY());
                }
            } else {

                synchronized (Map.location) {
                    if (input.isKeyDown(Input.KEY_W)) {
                        Point dir = new Point((float) Math.cos(Math.toRadians(yaw)), (float) Math.sin(Math.toRadians(yaw)));
                        handleMoveDir(dir, delta);
                    }
                    if (input.isKeyDown(Input.KEY_S)) {
                        Point dir = new Point(-(float) Math.cos(Math.toRadians(yaw)), -(float) Math.sin(Math.toRadians(yaw)));
                        handleMoveDir(dir, delta);
                    }
                    if (input.isKeyDown(Input.KEY_A)) {
                        Point dir = new Point((float) Math.cos(Math.toRadians(yaw - 90)), (float) Math.sin(Math.toRadians(yaw - 90)));
                        handleMoveDir(dir, delta);
                    }
                    if (input.isKeyDown(Input.KEY_D)) {
                        Point dir = new Point((float) Math.cos(Math.toRadians(yaw + 90)), (float) Math.sin(Math.toRadians(yaw + 90)));
                        handleMoveDir(dir, delta);
                    }
                }
            }

            if (input.isKeyDown(Settings.keyByName("RifleKey"))) {
                curWeapon = rifleWeapon;
            }

            if (input.isKeyDown(Settings.keyByName("PistoleKey"))) {
                curWeapon = pistoleWeapon;
            }


        }

    }

    private void handleCurAnim() {

        curAnim = new Animation();

        if (isRunning) {
            curAnim = feetAnim_run;
            frameCount = feetAnim_run_imageCount;
        }

    }


    public void handleMouseInput() {


        setYaw(Listener.getMouseCursor());


    }

    public int getCurState() {
        return curState;
    }

    public AbstractWeapon getCurWeapon() {
        return curWeapon;
    }

    public void setCurWeapon(AbstractWeapon weap) {
        this.curWeapon = weap;
    }

    public void setRifle(rifle rifle) {
        this.rifleWeapon = rifle;
    }

    public void setPistole(pistole pistole) {
        this.pistoleWeapon = pistole;
    }

    private void handleMoveDir(Point dir, int delta) {
        float speed = 0.3F * delta;
        Point newLoc = new Point(location.getX() + dir.getX() * speed, location.getY() + dir.getY() * speed);
        if (Map.contains(newLoc) && !curWeapon.collition(dir.getX() * speed,dir.getY() * speed)) {

                Point newMapLoc = new Point(Map.location.getX() + dir.getX() * speed, Map.location.getY() + dir.getY() * speed);
                Map.location = newMapLoc;
                setLocation(newLoc);

        }

        /*
        while (curWeapon.collition()) {
            speed = 2F * delta;
            newLoc = new Point(location.getX() - dir.getX() * speed, location.getY() - dir.getY() * speed);
            Point newMapLoc = new Point(Map.location.getX() - dir.getX() * speed, Map.location.getY() - dir.getY() * speed);
            Map.location = newMapLoc;
            setLocation(newLoc);

            curWeapon.updateHitbox();

        }*/
    }

}
