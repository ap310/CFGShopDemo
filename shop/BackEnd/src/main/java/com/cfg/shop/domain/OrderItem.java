package com.cfg.shop.domain;

import java.util.Date;

/**
 * Created by Administrator on 2017/7/2.
 * one order contain many orderitem ,one orderitem contain the client to buy special product information
 */
public class OrderItem {
    private String productNo;
    private String productName;
    private long purchasedNumber;

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public long getPurchasedNumber() {
        return purchasedNumber;
    }

    public void setPurchasedNumber(long purchasedNumber) {
        this.purchasedNumber = purchasedNumber;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
