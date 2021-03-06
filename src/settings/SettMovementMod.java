package settings;

import org.newdawn.slick.Input;

public class SettMovementMod extends AbstractSettingModule {

    private boolean isActive = false;
    public static final String name = "MovementType";
    private String valString = "Direkt";
    private boolean isLocked = false;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getCleanName() {
        String cleanName = "Movement Type:";
        return cleanName;
    }

    @Override
    public boolean getBool() {
        return isActive;
    }
    @Override
    public void setBool(boolean val) {
        isActive = val;
        valString = !getBool() ? "Direkt" : "Indirekt";
    }

    @Override
    public boolean waitForRelease() {
        return true;
    }

    @Override
    public boolean getLock() {
        return isLocked;
    }

    @Override
    public String getVal() {
        return valString;
    }

    @Override
    public void lock() {
        isLocked = !isLocked;
    }

    @Override
    public int getKey() {
        int keyCode = Input.KEY_M;
        return keyCode;
    }
}
