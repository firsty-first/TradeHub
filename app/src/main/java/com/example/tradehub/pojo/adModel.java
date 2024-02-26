package com.example.tradehub.pojo;

public class adModel {

    String adImage;
    String adTitle;

    public adModel(String adImage, String adTitle) {
        this.adImage = adImage;
        this.adTitle = adTitle;
    }

    public adModel(String adTitle) {
        this.adTitle = adTitle;
    }

    public String getAdImage() {
        return adImage;
    }

    public void setAdImage(String adImage) {
        this.adImage = adImage;
    }

    public String getAdTitle() {
        return adTitle;
    }

    public void setAdTitle(String adTitle) {
        this.adTitle = adTitle;
    }
}
