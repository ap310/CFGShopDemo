package com.cfg.shop.domain;

import java.util.Date;

/**
 * Created by Administrator on 2017/7/2.
 * the class is used for stock ,one product has some information once it is putted to the stock
 */
public class ProductItem {
    private String itemNo;
    private String productId;
    private String productName;
    private PRODUCT_STOCK_STATUS status;  //product stack status
    private Date inStockTime; // into stock time
    private Date outStockTime; // out of  stock time
    private String orderNo;    //order no

    public String getItemNo() {
        return itemNo;
    }

    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public PRODUCT_STOCK_STATUS getStatus() {
        return status;
    }

    public void setStatus(PRODUCT_STOCK_STATUS status) {
        this.status = status;
    }

    public Date getInStockTime() {
        return inStockTime;
    }

    public void setInStockTime(Date inStockTime) {
        this.inStockTime = inStockTime;
    }

    public Date getOutStockTime() {
        return outStockTime;
    }

    public void setOutStockTime(Date outStockTime) {
        this.outStockTime = outStockTime;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
