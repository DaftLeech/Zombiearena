package engine;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import general.Zombiearena;
import render.Render;

import java.awt.*;
import java.util.ArrayList;

public class Listener implements InputProcessor {

    private static Boolean b_isMousePressed = false;
    private static Point p_MouseCursor = new Point(0,0);
    private static final ArrayList<Integer> l_KeyCodes = new ArrayList<>();

    @Override
    public boolean keyDown(int keycode) {

        if(keycode == Keys.ESCAPE) {

            System.exit(0);
        }

        synchronized (Listener.getKeyCodes()) {
            if (!l_KeyCodes.contains(keycode) && isRelevantKeyCode(keycode))
                l_KeyCodes.add(keycode);
        }

        return true;
    }

    @Override
    public boolean keyUp(int keycode) {

        synchronized (Listener.getKeyCodes()) {
            l_KeyCodes.remove((Object) keycode);
        }
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {

        p_MouseCursor = new Point(screenX,screenY);
        return true;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }





    public static Boolean isMousePressed(){
        return b_isMousePressed;
    }

    public static Point getMouseCursor(){
        return p_MouseCursor;
    }

    public static ArrayList<Integer> getKeyCodes(){
        return l_KeyCodes;
    }

    private Boolean isRelevantKeyCode(int k){
        return k==Keys.NUM_1 ||k==Keys.NUM_2 ||k==Keys.V || k==Keys.R || k==Keys.M || isMovementKeyCode(k);
    }

    public static boolean isMovementKeyCode(int k){
        return k == Keys.W || k == Keys.S || k == Keys.D || k == Keys.A;
    }


}
