package com.example.foodfuzz;

public class itemAdapter {
    String pics;
    String details;
    String Vidurl;

    itemAdapter(){

    }

    itemAdapter(String pics, String details, String Vidurl){
        this.pics = pics;
        this.details = details;
        this.Vidurl = Vidurl;

    }

    public String getPics() {
        return pics;
    }

    public void setPics(String pics) {
        this.pics = pics;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getVidurl() {
        return Vidurl;
    }

    public void setVidurl(String vidurl) {
        Vidurl = vidurl;
    }
}
