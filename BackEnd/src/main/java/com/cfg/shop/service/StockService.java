package com.cfg.shop.service;

import com.cfg.shop.domain.Product;
import com.cfg.shop.domain.ProductItem;

import java.util.LinkedHashMap;
import java.util.Vector;

/**
 * Created by Administrator on 2017/7/2.
 */
public interface StockService {
     /**
      * 根绝Product查询SKU数量
      * @param product
      * @return 如果Product不存在，则抛Exception
      */
     Long countByProduct(Product product);


     /**
      * 根据Product和Amount，将SKU出库，减少库存数量
      * @param product
      * @param amount
      * 当库存不足时，抛出exception
      */
     String outStockSKUByProductAndAmount(Product product, Long amount);

}
