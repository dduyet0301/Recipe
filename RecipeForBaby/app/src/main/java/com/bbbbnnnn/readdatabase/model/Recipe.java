package com.bbbbnnnn.readdatabase.model;

public class Recipe {
    private int id;
    private String tenHinh;
    private String linkHinh;
    private String youtube;
    private String love;

    public Recipe(int id, String tenHinh, String linkHinh, String youtube, String love) {
        this.id = id;
        this.tenHinh = tenHinh;
        this.linkHinh = linkHinh;
        this.youtube = youtube;
        this.love = love;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenHinh() {
        return tenHinh;
    }

    public void setTenHinh(String tenHinh) {
        this.tenHinh = tenHinh;
    }

    public String getLinkHinh() {
        return linkHinh;
    }

    public void setLinkHinh(String linkHinh) {
        this.linkHinh = linkHinh;
    }

    public String getYoutube() {
        return youtube;
    }

    public void setYoutube(String youtube) {
        this.youtube = youtube;
    }

    public String getLove() {
        return love;
    }

    public void setLove(String love) {
        this.love = love;
    }
}
