package io.renren.modules.mall.product.model;

import java.util.Date;

/**
 * 商品详情类
 * Created by GavinCee on 2018/7/8.
 */
public class GoodDetails {

    private long id;

    private String number;

    private double weight;//重量，单位是KG

    private String originPlace;//产地

    private Date shelfTime;//保质期

    private Date producTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getOriginPlace() {
        return originPlace;
    }

    public void setOriginPlace(String originPlace) {
        this.originPlace = originPlace;
    }

    public Date getShelfTime() {
        return shelfTime;
    }

    public void setShelfTime(Date shelfTime) {
        this.shelfTime = shelfTime;
    }

    public Date getProducTime() {
        return producTime;
    }

    public void setProducTime(Date producTime) {
        this.producTime = producTime;
    }
}
