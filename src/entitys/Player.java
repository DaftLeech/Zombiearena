package entitys;

import engine.Listener;
import objects.Entity;
import render.Window;
import resources.ResourceManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Player extends Entity{

    private Point location;
    private double yaw;
    private int health;
    private static BufferedImage sprite;
    private static Boolean isShooting = false;


    public Player(Point location) throws Exception {
        super();
        this.location = location;
        this.yaw = 0.0;
        this.health = 3;

        if(sprite == null){

            sprite = ResourceManager.loadImage("survivor.png");

        }
    }

    @Override
    public void toRender(Graphics2D g) {

        AffineTransform at
                = AffineTransform.getRotateInstance((-yaw+90)*Math.PI/180,getLocation().x,getLocation().y);


        AffineTransform backup = g.getTransform();

        float[] dist = {0.0f, 0.4f};
        Color[] colors = {Color.BLACK, new Color(0,0,0,0)};
        RadialGradientPaint rgp = new RadialGradientPaint(new Point(getLocation().x + 5,getLocation().y + 5),40,dist,colors);
        g.setPaint(rgp);
        g.fillOval(getLocation().x - 15, getLocation().y - 15, 40, 40);
        g.setTransform(at);
        g.drawImage(sprite,getLocation().x-15,getLocation().y-15,30,30, null);

        g.setTransform(backup);

    }

    @Override
    public void toThread() {


        handleKeyInput();
        setYaw(Listener.getMouseCursor());


    }

    @Override
    public int getTickRate() {
        return 0;
    }

    public Point getLocation(){
        return this.location;
    }

    public void setLocation(Point location){
        this.location = location;
    }

    public void setLocation(int x, int y) { this.location = new Point(x,y);}

    public void setHealth(int health){
        this.health = health;
    }

    public void removeHealth(int value){
        this.health -= value;
    }

    public void addHealth(int value){
        this.health += value;
    }

    public int getHealth(){
        return this.health;
    }

    public double getYaw(){
        return this.yaw;
    }

    public void setYaw(int value){
        this.yaw = value;
    }

    public void setYaw(Point pLookAt){
        this.yaw = Math.toDegrees(Math.atan2(pLookAt.x - this.location.x, pLookAt.y- this.location.y));
    }

    public void setIsShooting(Boolean value){
        isShooting = value;
    }

    public void handleKeyInput(){

        synchronized (Listener.getKeyCodes()){

            int moveMod = (int) ((Listener.getKeyCodes().size() == 1 ? 2 : 1) );


            for(int KeyCode : Listener.getKeyCodes()){

                System.out.println(KeyCode);

                if(KeyCode == KeyEvent.VK_W && getLocation().y > 0){
                    setLocation(getLocation().x, getLocation().y - moveMod);
                }

                if(KeyCode == KeyEvent.VK_S && getLocation().y < Window.HEIGHT){
                    setLocation(getLocation().x, getLocation().y + moveMod);
                }

                if(KeyCode == KeyEvent.VK_A && getLocation().x > 0){
                    setLocation(getLocation().x  - moveMod, getLocation().y);
                }

                if(KeyCode == KeyEvent.VK_D && getLocation().y < Window.WIDTH){
                    setLocation(getLocation().x  + moveMod, getLocation().y);
                }

            }
            if(Listener.getKeyCodes().size()> 0)
            System.out.println("");

        }

    }


    public void handleMouseInput(){


        setYaw(Listener.getMouseCursor());


    }


}
