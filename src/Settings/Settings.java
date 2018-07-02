package Settings;

import engine.Listener;
import engine.ThreadManager;
import objects.GameObject;
import render.Render;

import javax.sound.midi.SysexMessage;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashMap;

public class Settings extends GameObject {

   private static HashMap<String,AbstractSettingModule> settMap = new HashMap<String, AbstractSettingModule>();

   public Settings(){

       settMap.put(SettMovementMod.name,new SettMovementMod());
       ThreadManager.addToThreadList(this);
       Render.addToDrawables(this);
   }


    @Override
    public void toRender(Graphics2D g) {

        int yOffset = 100;

        for(AbstractSettingModule module : settMap.values()) {

            String key = module.getName();
            String val = module.getVal();

            int keySize = g.getFontMetrics(g.getFont()).stringWidth(key);
            int valSize = g.getFontMetrics(g.getFont()).stringWidth(val);



            g.setPaint(Color.BLACK);
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
    public void toThread() {
        synchronized (Listener.getKeyCodes()){


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
    }

    public static boolean boolByName(String key){
       return settMap.get(key).getBool();
    }
}
