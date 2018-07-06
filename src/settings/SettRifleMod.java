package settings;

import java.awt.event.KeyEvent;

public class SettRifleMod extends AbstractSettingModule {

    private final int keyCode = KeyEvent.VK_1;
    public static final String name = "RifleKey";
    private final String valString = KeyEvent.getKeyText(keyCode);
    private boolean isLocked = false;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean getBool() {
        return false;
    }

    @Override
    public int getKey() {
        return keyCode;
    }

    @Override
    public void setBool(boolean val) {
        boolean isActive = val;
    }

    @Override
    public boolean waitForRelease() {
        return false;
    }

    @Override
    public boolean getLock() {
        return isLocked;
    }

    @Override
    public void lock() {
        isLocked = !isLocked;
    }

    @Override
    public String getVal() {
        return valString;
    }

    @Override
    public String getCleanName() {
        String cleanName = "Rifle HotKey:";
        return cleanName;
    }
}
