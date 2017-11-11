package com.zcy.hnkjxy.customview.bean;

/**
 * Created by zhang chen yang on 2017/10/10 0010.
 * 一个图片文件实体类
 */

public class ImageFolderBean {
    //相册文件夹的名字
    private String imgFolderName;
    //第一个图片的地址
    private String imgFirstName;
    //相册图片的数量
    private int imgFolderCount;


    private ImageBean []imageBeens;

    public ImageBean[] getImageBeens() {
        return imageBeens;
    }

    public void setImageBeens(ImageBean[] imageBeens) {
        this.imageBeens = imageBeens;
    }


    public String getImgFolderName() {
        return imgFolderName;
    }

    public void setImgFolderName(String imgFolderName) {
        this.imgFolderName = imgFolderName;
    }

    public String getImgFirstName() {
        return imgFirstName;
    }

    public void setImgFirstName(String imgFirstName) {
        this.imgFirstName = imgFirstName;
    }

    public int getImgFolderCount() {
        return imgFolderCount;
    }

    public void setImgFolderCount(int imgFolderCount) {
        this.imgFolderCount = imgFolderCount;
    }
}
