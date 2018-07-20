package io.renren.modules.mall.product.model;

/**
 * 商品类
 * Created by GavinCee on 2018/7/8.
 */
public class Good {

    private long id;

    private double price;

    private double oriPrice;

    private String title;

    private int saleNum;

    private int stock;

    private ServiceType[] serviceTypes;

    private GoodDesc goodDesc;

    private GoodDetails goodDetails;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getOriPrice() {
        return oriPrice;
    }

    public void setOriPrice(double oriPrice) {
        this.oriPrice = oriPrice;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSaleNum() {
        return saleNum;
    }

    public void setSaleNum(int saleNum) {
        this.saleNum = saleNum;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public ServiceType[] getServiceTypes() {
        return serviceTypes;
    }

    public void setServiceTypes(ServiceType[] serviceTypes) {
        this.serviceTypes = serviceTypes;
    }

    public GoodDesc getGoodDesc() {
        return goodDesc;
    }

    public void setGoodDesc(GoodDesc goodDesc) {
        this.goodDesc = goodDesc;
    }

    public GoodDetails getGoodDetails() {
        return goodDetails;
    }

    public void setGoodDetails(GoodDetails goodDetails) {
        this.goodDetails = goodDetails;
    }
}
