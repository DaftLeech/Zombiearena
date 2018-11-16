package world;


import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;

import java.util.ArrayList;

public class Dungeon {

    private final ArrayList<Rectangle> rooms = new ArrayList<>();
    private final ArrayList<Rectangle> finalrooms = new ArrayList<>();
    private final ArrayList<Rectangle> paths = new ArrayList<>();
    private final double roomPerc;
    private final int pathWidth;

    public Dungeon(Rectangle size, double roomPerc, int deepth){

        this.roomPerc = roomPerc;
        this.pathWidth = (int)(size.getWidth() / (Math.pow(2,deepth) / 2) * 0.2);
        System.out.println(pathWidth);
        genRooms(size,deepth);

    }

    private void genRooms(Rectangle parent, int deepth){
        genRooms(parent,deepth,0);
    }

    private void genRooms(Rectangle parent, int deepth, int level){

        level++;
        Rectangle room1;
        Rectangle room2;

        if(parent.getWidth() > parent.getHeight()){

            int split = (int) (parent.getWidth() / 2 - parent.getWidth() * 0.025 + Math.random() * (parent.getWidth() * 0.05));
            room1 = new Rectangle(parent.getX(), parent.getY(), split, parent.getHeight());
            room2 = new Rectangle(parent.getX() + split, parent.getY(), parent.getWidth() - split, parent.getHeight());



        }else {

            int split = (int) (parent.getHeight() / 2 - parent.getHeight() * 0.025 + Math.random() * (parent.getHeight() * 0.05));
            room1 = new Rectangle(parent.getX(), parent.getY(), parent.getWidth(), split);
            room2 = new Rectangle(parent.getX(), parent.getY() + split, parent.getWidth(), parent.getHeight() - split);


        }


        rooms.add(room1);
        rooms.add(room2);


        if(deepth > level){

            genRooms(room1,deepth,level);
            genRooms(room2,deepth,level);
        }else {

            finalrooms.add(room1);
            finalrooms.add(room2);

            shrinkRooms();
            genPaths();

        }




    }

    private void shrinkRooms(){

        double addPercMax = (1 - roomPerc) / 2;

        for(Rectangle room : rooms){

            int xAdd = (int)( room.getWidth() * addPercMax * Math.random());
            int yAdd = (int)( room.getHeight()* addPercMax * Math.random());
            room.setX(room.getX()+ xAdd);
            room.setY(room.getY()+ yAdd);
            room.setWidth(room.getWidth()  - (xAdd +(int)( room.getWidth() * addPercMax * Math.random())));
            room.setHeight(room.getHeight() - (yAdd + (int)( room.getHeight() * addPercMax * Math.random())));

            //System.out.println("X: "+String.valueOf(room.x)+"Y: "+String.valueOf(room.y)+"W: "+String.valueOf(room.width)+"H: "+String.valueOf(room.height));


        }


    }

    private void genPaths(){

        for(int i = rooms.size()-1; i > 0; i = i -2){

            Rectangle room1 = rooms.get(i);
            Rectangle room2 = rooms.get(i-1);

            float r1XOff = 0;
            float r1YOff = 0;
            float r2XOff = 0;
            float r2YOff = 0;
            if(room1.getWidth()>room1.getHeight()){
                if(room1.getY()<room2.getY()){

                    r1XOff = room1.getWidth()/2;
                    r1YOff = room1.getHeight();
                    r2XOff = room2.getWidth()/2;
                    r2YOff = 0;

                } else {

                    r1XOff = room1.getWidth()/2;
                    r1YOff = 0;
                    r2XOff = room2.getWidth()/2;
                    r2YOff = room2.getHeight();

                }
            } else {
                if(room1.getX()<room2.getX()){

                    r1XOff = room1.getWidth();
                    r1YOff = room1.getHeight()/2;
                    r2XOff = 0;
                    r2YOff = room2.getHeight()/2;

                }else {

                    r1XOff = 0;
                    r1YOff = room1.getHeight()/2;
                    r2XOff = room2.getWidth();
                    r2YOff = room2.getHeight()/2;
                }
            }


            Point center1 = new Point(room1.getX() + r1XOff, room1.getY() + r1YOff);
            Point center2 = new Point(room2.getX() + r2XOff, room2.getY() + r2YOff);

            int x=Math.min((int)center1.getX(), (int)center2.getX());
            int y=Math.min((int)center1.getY(), (int)center2.getY());
            int width=Math.max(Math.abs((int)center1.getX() - (int)center2.getX()),pathWidth);
            int height=Math.max(Math.abs((int)center1.getY() - (int)center2.getY()),pathWidth);

            Rectangle path = new Rectangle(x, y, width, height);

            paths.add(path);
            //System.out.println("X: "+String.valueOf(path.x)+"Y: "+String.valueOf(path.y)+"W: "+String.valueOf(path.width)+"H: "+String.valueOf(path.height));

        }


    }

    public ArrayList<Rectangle> getFinalRooms() {
        return finalrooms;
    }

    public ArrayList<Rectangle> getPaths() {
        return paths;
    }
}
