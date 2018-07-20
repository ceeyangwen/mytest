package io.renren.modules.mall.product.model;

/**
 * 商品服务类型
 * Created by GavinCee on 2018/7/8.
 */
public enum ServiceType {

    Quality_Assurance(1, "正品保证"),
    Sever_Days_Return(2, "七天退换");

    private int id;

    private String serviceName;

    private ServiceType(int id, String serviceName) {
        this.id = id;
        this.serviceName = serviceName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}
