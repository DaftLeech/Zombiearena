package Settings;

abstract class AbstractSettingModule {


    public abstract String getName();
    public abstract boolean getBool();
    public abstract int getKey();
    public abstract void setBool(boolean val);
    public abstract boolean waitForRelease();
    public abstract boolean getLock();
    public abstract void lock();
    public abstract String getVal();
    public abstract String getCleanName();


}
