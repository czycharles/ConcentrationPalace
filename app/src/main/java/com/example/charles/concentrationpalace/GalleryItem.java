package com.example.charles.concentrationpalace;

/**
 * Gallery Item.
 */

public class GalleryItem {

    private String desc;

    private  int imageId;

    public GalleryItem(String desc, int imageId){
        this.desc = desc;
        this.imageId = imageId;
    }

    public String getDesc(){
        return desc;
    }

    public int getImageId(){
        return imageId;
    }
}
