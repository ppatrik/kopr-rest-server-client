package sk.upjs.kopr2014.ppatrik.rest.logic;

public enum DataFactory {
    INSTANCE;
    private DataManager dm = null;
    public DataManager getManager() {
        if(dm==null) {
            dm = new DataManager();
        }
        return dm;
    }
}
