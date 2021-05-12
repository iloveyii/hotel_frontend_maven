package org.hotel.models;


public final class DataHolder {

    private Data data;
    private final static DataHolder INSTANCE = new DataHolder();

    private DataHolder() {}

    public static DataHolder getInstance() {
        return INSTANCE;
    }

    public void setData(Data d) {
        this.data = d;
    }

    public Data getData() {
        return this.data;
    }
}
