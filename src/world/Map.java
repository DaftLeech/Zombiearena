package world;


import general.Zombiearena;
import objects.GameObject;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.GeomUtil;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import render.Render;
import resources.ResourceManager;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Map extends GameObject {

    private static Dungeon dngn;
    private Image tile;
    public static Point location = new Point(0f,0f);
    private static Shape shape;



    public Map(){


        int height = 10000;
        int width = 10000;
        Rectangle size = new Rectangle(0,0, width, height);

        try {
            tile = new Image("src/resources/"+"test.png");

        } catch (Exception e) {
            e.printStackTrace();
        }
        int deepth = 3;
        dngn = new Dungeon(size,0.7, deepth);

        GeomUtil util = new GeomUtil();
        shape = dngn.getFinalRooms().get(0);
        System.out.println(shape.getPointCount());

        ArrayList<Rectangle> allRooms = new ArrayList<>(dngn.getFinalRooms());
        allRooms.addAll(dngn.getPaths());
        System.out.println(allRooms.size()*4);
        ArrayList<Integer> roomIDs = new ArrayList<>();
        for(int i = 0; i < allRooms.size(); i++){
            roomIDs.add(i);
        }
        int rsize = 0;
        while(roomIDs.size()>0 && rsize != roomIDs.size()){
            rsize = roomIDs.size();
            for(int i = 0; i <allRooms.size(); i++){
                if(roomIDs.contains((Object)i)) {
                    Shape[] unionRes = util.union(shape, allRooms.get(i));
                    if (unionRes.length == 1) {
                        shape = unionRes[0];
                        roomIDs.remove((Object) i);
                        rsize--;
                    }

                }
            }
        }




        System.out.println(shape.getPointCount());

        Render.addToDrawables(this);

        layer = Layer.BASE;
        cdl.countDown();
    }

    @Override
    public void toRender(Graphics g) {

        synchronized (location) {
            g.translate(-location.getX(), -location.getY());


            g.setColor(Color.white);

            for(Rectangle room : dngn.getFinalRooms()){
                g.texture(room,tile);
            }
            for(Rectangle path : dngn.getPaths()){
                g.texture(path,tile);
            }



            g.flush();


            /*
            if(Zombiearena.pLocal != null) {
                ArrayList<Point> filteredEdges = new ArrayList<>();
                Point pHere = Zombiearena.pLocal.getLocation();
                pHere.translate(location.getX(),location.getY());
                ArrayList<Rectangle> shape = new ArrayList<>(dngn.getFinalRooms());
                shape.addAll(dngn.getPaths());
                for (Rectangle room : shape) {

                    int x = room.x;
                    int y = room.y;
                    int width = room.width;
                    int height = room.height;


                    double px = (Zombiearena.pLocal.getLocation().x + location.x);
                    double py = (Zombiearena.pLocal.getLocation().y + location.y);
                    double yaw = Zombiearena.pLocal.getYaw();

                    double px2 = (Math.cos(Math.toRadians(-yaw+90))*(10000)+ Zombiearena.pLocal.getLocation().x+location.x);
                    double py2 = (Math.sin(Math.toRadians(-yaw+90))*(10000)+ Zombiearena.pLocal.getLocation().y+location.y);

                    Point[] edges = new Point[4];
                    edges[0] = graphicmath.lineIntersect(x,y,x+width,y,(int)px,(int)py,(int)px2,(int)py2);
                    edges[1] = graphicmath.lineIntersect(x,y+height,x+width,y+height,(int)px,(int)py,(int)px2,(int)py2);
                    edges[2] = graphicmath.lineIntersect(x,y,x,y+height,(int)px,(int)py,(int)px2,(int)py2);
                    edges[3] = graphicmath.lineIntersect(x+width,y,x+width,y+height,(int)px,(int)py,(int)px2,(int)py2);




                    filteredEdges.addAll(Arrays.stream(edges).filter(e->e != null&&(!contains(e.x+10,e.y)||!contains(e.x-10,e.y)||!contains(e.x,e.y+10)||!contains(e.x,e.y-10))).collect(Collectors.toCollection(ArrayList::new)));



                }

                g.setStroke(new BasicStroke(10));
                g.setPaint(Color.RED);

                if(filteredEdges.size()>0) {
                    Point intersection = filteredEdges.stream().sorted((e1,e2)->Integer.compare(graphicmath.PointDistance(e2,Zombiearena.pLocal.getLocation().toPoint()),graphicmath.PointDistance(e1,Zombiearena.pLocal.getLocation().toPoint()))).collect(Collectors.toCollection(ArrayList::new)).get(0);
                    g.drawLine(intersection.x, intersection.y, intersection.x, intersection.y);
                }

                g.setStroke(new BasicStroke(1));

            }*/

            g.translate(location.getX(), location.getY());
            g.setColor(Color.green);
            if(Zombiearena.pLocal != null)
                g.drawString(String.valueOf(Zombiearena.pLocal.getYaw()),20,100);
            g.flush();
        }
    }

    @Override
    public int getTickRate() {
        return 0;
    }

    @Override
    public void toThread(GameContainer container, int delta) {

    }

    public Dungeon getDngn(){
        return  dngn;
    }

    public static boolean contains(Point point){
        Point p = point;
        return dngn.getPaths().stream().anyMatch(r -> r.contains(p)) || dngn.getFinalRooms().stream().anyMatch(r -> r.contains(p));
    }

    public static boolean contains(int x, int y){
        Point p = new Point(x,y);
        return dngn.getPaths().stream().anyMatch(r -> r.contains(p)) || dngn.getFinalRooms().stream().anyMatch(r -> r.contains(p));
    }
}
