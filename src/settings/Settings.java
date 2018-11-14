package settings;

import engine.Listener;
import engine.ThreadManager;
import objects.GameObject;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import render.Render;

import java.util.HashMap;

public class Settings extends GameObject {

   private static final HashMap<String,AbstractSettingModule> settMap = new HashMap<>();

   public Settings(){

       settMap.put(SettMovementMod.name,new SettMovementMod());
       settMap.put(SettRifleMod.name,new SettRifleMod());
       settMap.put(SettPistoleMod.name,new SettPistoleMod());
       ThreadManager.addToThreadList(this);
       Render.addToDrawables(this);
       cdl.countDown();
       layer = Layer.HUD;
   }


    @Override
    public void toRender(org.newdawn.slick.Graphics g) {

        int yOffset = 100;

        for(AbstractSettingModule module : settMap.values()) {

            String key = module.getName();
            String val = module.getVal();

            int keySize = g.getFont().getWidth(key);
            int valSize = g.getFont().getWidth(val);



            g.setColor(Color.black);
            g.drawString(key, render.Window.WIDTH - valSize - 10 - keySize - 10, yOffset);
            g.drawString(val, render.Window.WIDTH - valSize - 10, yOffset);

            yOffset += 50;
        }

    }

    @Override
    public int getTickRate() {
        return 0;
    }

    @Override
    public void toThread(GameContainer container, int delta) {



            for(int KeyCode : Listener.getKeyCodes()){

                for(AbstractSettingModule module : settMap.values()) {


                    if (KeyCode == module.getKey() && !(module.waitForRelease() && module.getLock())) {
                        module.setBool(!module.getBool());
                        module.lock();

                    }

                    if(!Listener.getKeyCodes().contains(module.getKey()) && (module.waitForRelease() && module.getLock())){
                        module.lock();
                    }

                }






        }
    }

    public static boolean boolByName(String key){
       return settMap.get(key).getBool();
    }
    public static int keyByName(String key){
        return settMap.get(key).getKey();
    }
}
