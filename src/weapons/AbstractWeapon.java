package weapons;

import entitys.Player;
import objects.Entity;

import java.awt.*;
import java.util.ArrayList;

public abstract class AbstractWeapon extends Entity {

    Player parent = null;
    ArrayList<Image> animation_idle;
    ArrayList<Image> animation_meleeattack;
    ArrayList<Image> animation_move;
    ArrayList<Image> animation_reload;
    ArrayList<Image> animation_shoot;
    ArrayList<Image> curAnim = new ArrayList<>();
    public abstract int animation_idle_length();
    public abstract int animation_meleeattack_length();
    public abstract int animation_move_length();
    public abstract int animation_reload_length();
    public abstract int animation_shoot_length();
    public abstract void loadAnimations() throws Exception;
    public abstract void handleCurAnim();
    public void setParent(Player parent){
        this.parent = parent;
    }

    final int tickrateAnim = 10;
    public int frameAnim = 0;
    public int frameCount = 0;
    public int waitFactor = 0;
    int lastParentState = 0;

    //
    int AnimOffX = 0;
    int AnimOffY = 0;


}
