package engine;

import org.newdawn.slick.geom.Point;

import java.awt.event.*;
import java.util.ArrayList;

public class Listener implements KeyListener, MouseMotionListener, MouseListener {

    private static Boolean b_isMousePressed = false;
    private static Point p_MouseCursor = new Point(0,0);
    private static final ArrayList<Integer> l_KeyCodes = new ArrayList<>();

    @Override
    public void mouseDragged(MouseEvent arg0) {


        p_MouseCursor = new Point(arg0.getPoint().x,arg0.getPoint().y);


    }

    @Override
    public void mouseMoved(MouseEvent arg0) {



        p_MouseCursor = new Point(arg0.getPoint().x,arg0.getPoint().y);

    }


    @Override
    public void mouseClicked(MouseEvent arg0) {

    }

    @Override
    public void mouseEntered(MouseEvent arg0) {


    }

    @Override
    public void mouseExited(MouseEvent arg0) {


    }

    @Override
    public void mousePressed(MouseEvent arg0) {

        b_isMousePressed = true;

    }

    @Override
    public void mouseReleased(MouseEvent arg0) {

        b_isMousePressed = false;

    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent arg0) {

        if(arg0.getKeyCode() == KeyEvent.VK_ESCAPE)
            System.exit(0);

        synchronized (Listener.getKeyCodes()) {
            if (!l_KeyCodes.contains(arg0.getKeyCode()) && isRelevantKeyCode(arg0.getKeyCode()))
                l_KeyCodes.add(arg0.getKeyCode());
        }
    }


    @Override
    public void keyReleased(KeyEvent arg0) {

        synchronized (Listener.getKeyCodes()) {
            l_KeyCodes.remove((Object) arg0.getKeyCode());
        }


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
        return k==KeyEvent.VK_1 ||k==KeyEvent.VK_2 ||k==KeyEvent.VK_V || k==KeyEvent.VK_R || k==KeyEvent.VK_M || isMovementKeyCode(k);
    }

    public static boolean isMovementKeyCode(int k){
        return k == KeyEvent.VK_W || k == KeyEvent.VK_S || k == KeyEvent.VK_D || k == KeyEvent.VK_A;
    }


}
