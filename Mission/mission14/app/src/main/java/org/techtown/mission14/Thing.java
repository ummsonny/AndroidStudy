package org.techtown.mission14;

public class Thing {
    int resId;
    String name;
    String cost;
    String explain;

    public Thing(int resId, String name, String cost, String explain) {
        this.resId = resId;
        this.name = name;
        this.cost = cost;
        this.explain = explain;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }
}
