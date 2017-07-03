package com.cfg.shop.domain;

/**
 * Created by yhp on 2017/7/2.
 * this class is just used for front-end
 * sometimes  we need to use diffrent object class for front-end with back-end ,some data we do not want to show it for client
 */
public class ProductInfo {
    private String productNo;
    private long productNumber;
    private String productName;
    private PRODUCT_STATUS status;

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public long getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(long productNumber) {
        this.productNumber = productNumber;
    }

    public PRODUCT_STATUS getStatus() {
        return status;
    }

    public void setStatus(PRODUCT_STATUS status) {
        this.status = status;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
