package com.cfg.shop.domain;

/**
 * Created by Administrator on 2017/7/2.
 * product information
 * one product maybe exist in the shop but not existed in the stock
 *
 */
public class Product {
    private String no;
    private String name;
    private  PRODUCT_STATUS status;    //product status ONSALE ==on market  else the product cannot be sale on the market
    public Product(String no, String name,PRODUCT_STATUS status) {
        this.no = no;
        this.name = name;
        this.status=status;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public PRODUCT_STATUS getStatus() {
        return status;
    }

    public void setStatus(PRODUCT_STATUS status) {
        this.status = status;
    }
}
