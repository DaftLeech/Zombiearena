package world;


import engine.ThreadManager;
import entitys.Zombie;
import general.Zombiearena;
import objects.GameObject;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.*;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import render.Render;
import java.util.ArrayList;

public class Map extends GameObject {

    private static Dungeon dngn;
    private Image tile;
    private Image base;
    public static Point location = new Point(0f,0f);
    private static Shape shape;
    private Rectangle size;
    public static ArrayList<Rectangle> allRooms;



    public Map(){


        int height = 10000;
        int width = 10000;
        size = new Rectangle(0,0, width, height);

        try {
            tile = new Image("src/resources/"+"test.png");
            //base = new Image("src/resources/"+"roof1.png");

        } catch (Exception e) {
            e.printStackTrace();
        }
        int deepth = 3;
        dngn = new Dungeon(size,0.7, deepth);

        GeomUtil util = new GeomUtil();
        shape = size;
        System.out.println(shape.getPointCount());

        allRooms = new ArrayList<>(dngn.getFinalRooms());
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
                    Shape[] unionRes = util.subtract(shape, allRooms.get(i));
                    if (unionRes.length == 1) {
                        shape = unionRes[0];
                        roomIDs.remove((Object) i);
                        rsize--;
                        System.out.println(rsize);

                    }

                }
            }
        }




        System.out.println(shape.getPointCount());

        Render.addToDrawables(this);
        ThreadManager.addToThreadList(this);

        layer = Layer.BASE;
        cdl.countDown();
    }

    @Override
    public void toRender(Graphics g) {

        synchronized (location) {
            g.translate(-location.getX(), -location.getY());


            g.setColor(Color.white);
            //g.texture(size,tile);


            for(Rectangle room : dngn.getFinalRooms()){
                g.texture(room,tile);
            }
            for(Rectangle path : dngn.getPaths()){
                g.texture(path,tile);
            }

            g.setColor(Color.red);
            for(Rectangle room : Map.allRooms) {

                Line[] lines = new Line[4];
                lines[0] = new Line(room.getX(),room.getY(),room.getWidth()+room.getX(),room.getY());
                lines[1] = new Line(room.getX(),room.getY()+room.getHeight(),room.getX(),room.getY());
                lines[2] = new Line(room.getX()+room.getWidth(),room.getY(),room.getWidth()+room.getX(),room.getHeight()+room.getY());
                lines[3] = new Line(room.getX(),room.getY()+room.getHeight(),room.getX()+room.getWidth(),room.getY()+room.getHeight());

                for(Line line : lines) {
                    g.draw(line);
                    if(Zombiearena.pLocal.getCurWeapon() != null ) {
                        if (Zombiearena.pLocal.getCurWeapon().hitboxes.size() > 0) {
                            float dirX = line.getDY();
                            float dirY = line.getDX();
                            //dirX += Math.cos(Math.toRadians(90));
                            //dirY += Math.sin(Math.toRadians(90));

                            Circle hitbox = Zombiearena.pLocal.getCurWeapon().hitboxes.get(0);
                            Line op = new Line(hitbox.getX()+hitbox.radius,hitbox.getY()+hitbox.radius,hitbox.getX()+hitbox.radius+dirX*10000f,hitbox.getY()+hitbox.radius+dirY*10000f);
                            g.draw(op);
                        }
                    }

                }
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

    public static boolean contains(float x, float y){
        Point p = new Point(x,y);
        return dngn.getPaths().stream().anyMatch(r -> r.contains(p)) || dngn.getFinalRooms().stream().anyMatch(r -> r.contains(p));
    }
}
