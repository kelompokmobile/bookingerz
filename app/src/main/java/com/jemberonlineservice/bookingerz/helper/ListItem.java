package com.jemberonlineservice.bookingerz.helper;

import android.graphics.Bitmap;

/**
 * Created by vmmod on 12/28/2016.
 */

public class ListItem {
    private String id;
    private String judul;
    private String penyelenggara;
    private Bitmap poster;
    private String urls;

    public String getId(){
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJudul(){
        return judul;
    }

    public void setJudul(String judul){
        this.judul = judul;
    }

    public String getPenyelenggara(){
        return penyelenggara;
    }

    public void setPenyelenggara(String penyelenggara){
        this.penyelenggara = penyelenggara;
    }

    public Bitmap getPoster(){
        return poster;
    }

    public void setPoster(Bitmap poster){
        this.poster = poster;
    }

    public String getUrls(){
        return urls;
    }

    public void setUrls(String urls){
        this.urls = urls;
    }
}
