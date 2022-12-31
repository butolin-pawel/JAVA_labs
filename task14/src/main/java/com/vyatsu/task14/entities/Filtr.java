package com.vyatsu.task14.entities;

public class Filtr {
    private String substr ="";
    private String mincost = "";
    private String maxcost = "";
public void reset(){
    substr = "";
    mincost = "";
    maxcost = "";
}
    public String getSubstr() {
        return substr;
    }

    public void setSubstr(String substr) {
        this.substr = substr;
    }
    public String getMincost(){
    return mincost;
    }
    public String getMaxcost(){
    return maxcost;
    }
//    public int getMincost() {
//        return Integer.parseInt(mincost);
//    }

    public void setMincost(String mincost) {
        this.mincost = mincost;
    }

//    public int getMaxcost() {
//        return Integer.parseInt(maxcost);
//    }

    public void setMaxcost(String  maxcost) {
        this.maxcost = maxcost;
    }
}
