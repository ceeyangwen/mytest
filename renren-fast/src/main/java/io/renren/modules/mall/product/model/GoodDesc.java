package io.renren.modules.mall.product.model;

/**
 * 商品描述类
 * Created by GavinCee on 2018/7/8.
 */
public class GoodDesc {

    private long id;

    private String desc;

    private Picture[] pictures;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Picture[] getPictures() {
        return pictures;
    }

    public void setPictures(Picture[] pictures) {
        this.pictures = pictures;
    }
}
