package com.cfg.shop.service;

import com.cfg.shop.domain.OrderItem;
import com.cfg.shop.domain.Product;
import com.cfg.shop.domain.ProductItem;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Vector;

/**
 * Created by Administrator on 2017/7/2.
 */
public interface StockService {
     /**
      * get the count of one product by the product object
      * @param product
      * @return
      */
     Long countByProduct(Product product);


     /**
      * decrease the product amount in the stock
      * @param orderNo order number
      * @param product
      * @param amount  decrease amount
      */
     String outStockSKUByProductAndAmount(String orderNo ,Product product, Long amount);

     /**
      * recover the order products when buy process failed happen
      * @param orderNo
      * @param product
      */
     String recoverStockSKUByProductAndAmount(String orderNo ,Product product);


     /**
      *
      * @param orderNo
      * @return
      */
     List<OrderItem> queryOrder(String orderNo);

}
