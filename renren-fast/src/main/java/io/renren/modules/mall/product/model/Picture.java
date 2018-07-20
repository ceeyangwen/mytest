package io.renren.modules.mall.product.model;

/**
 * Created by GavinCee on 2018/7/8.
 */
public class Picture {

    private long id;

    //图片地址
    private String url;

    private long size;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
