package world;

import java.awt.*;
import java.util.ArrayList;

public class Dungeon {

    private final ArrayList<Rectangle> rooms = new ArrayList<>();
    private final ArrayList<Rectangle> finalrooms = new ArrayList<>();
    private final ArrayList<Rectangle> paths = new ArrayList<>();
    private final double roomPerc;
    private final int pathWidth;

    public Dungeon(Rectangle size, double roomPerc, int deepth){

        this.roomPerc = roomPerc;
        this.pathWidth = (int)(size.width / (Math.pow(2,deepth) / 2) * 0.2);
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

        if(parent.width > parent.height){

            int split = (int) (parent.width / 2 - parent.width * 0.025 + Math.random() * (parent.width * 0.05));
            room1 = new Rectangle(parent.x, parent.y, split, parent.height);
            room2 = new Rectangle(parent.x + split, parent.y, parent.width - split, parent.height);



        }else {

            int split = (int) (parent.height / 2 - parent.height * 0.025 + Math.random() * (parent.height * 0.05));
            room1 = new Rectangle(parent.x, parent.y, parent.width, split);
            room2 = new Rectangle(parent.x, parent.y + split, parent.width, parent.height - split);


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

            int xAdd = (int)( room.width * addPercMax * Math.random());
            int yAdd = (int)( room.height * addPercMax * Math.random());
            room.x += xAdd;
            room.y += yAdd;
            room.width  -= (xAdd +(int)( room.width * addPercMax * Math.random()));
            room.height -= (yAdd + (int)( room.height * addPercMax * Math.random()));

            //System.out.println("X: "+String.valueOf(room.x)+"Y: "+String.valueOf(room.y)+"W: "+String.valueOf(room.width)+"H: "+String.valueOf(room.height));


        }


    }

    private void genPaths(){

        for(int i = rooms.size()-1; i > 0; i = i -2){

            Rectangle room1 = rooms.get(i);
            Rectangle room2 = rooms.get(i-1);

            Point center1 = new Point(room1.x + room1.width/2, room1.y + room1.height/2);
            Point center2 = new Point(room2.x + room2.width/2, room2.y + room2.height/2);

            int x=Math.min(center1.x, center2.x);
            int y=Math.min(center1.y, center2.y);
            int width=Math.max(Math.abs(center1.x - center2.x),pathWidth);
            int height=Math.max(Math.abs(center1.y - center2.y),pathWidth);

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
