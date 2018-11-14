package settings;


import org.newdawn.slick.Input;

public class SettPistoleMod extends AbstractSettingModule {

    private final int keyCode = Input.KEY_2;
    public static final String name = "PistoleKey";
    private final String valString = Input.getKeyName(keyCode);
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
        String cleanName = "Pistole HotKey:";
        return cleanName;
    }
}
