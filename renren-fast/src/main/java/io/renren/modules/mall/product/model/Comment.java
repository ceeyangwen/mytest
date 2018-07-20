package io.renren.modules.mall.product.model;

import java.util.Date;

/**
 * 商品评价类
 * Created by GavinCee on 2018/7/8.
 */
public class Comment {

    private long id;

    //评价用户
    private long userId;

    //评价星级
    private int star;

    //评价内容
    private String content;

    private long goodId;


    private Date time;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getGoodId() {
        return goodId;
    }

    public void setGoodId(long goodId) {
        this.goodId = goodId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
