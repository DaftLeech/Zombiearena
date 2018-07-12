package weapons;

import com.badlogic.gdx.graphics.Texture;
import entitys.Player;
import objects.Entity;

import java.awt.*;
import java.util.ArrayList;

public abstract class AbstractWeapon extends Entity {

    Player parent = null;
    ArrayList<Texture> animation_idle;
    ArrayList<Texture> animation_meleeattack;
    ArrayList<Texture> animation_move;
    ArrayList<Texture> animation_reload;
    ArrayList<Texture> animation_shoot;
    ArrayList<Texture> curAnim = new ArrayList<>();
    public abstract int animation_idle_length();
    public abstract int animation_meleeattack_length();
    public abstract int animation_move_length();
    public abstract int animation_reload_length();
    public abstract int animation_shoot_length();
    public abstract double spread();
    public abstract double speed();
    public abstract double reloadTime();

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
