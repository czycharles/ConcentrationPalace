package com.example.charles.concentrationpalace;

/**
 * Gallery Item.
 */

class GalleryItem {

    private String desc;

    private  int imageId;

    GalleryItem(String desc, int imageId){
        this.desc = desc;
        this.imageId = imageId;
    }

    String getDesc(){
        return desc;
    }

    int getImageId(){
        return imageId;
    }
}
