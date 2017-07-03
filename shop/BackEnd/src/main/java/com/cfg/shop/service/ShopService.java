package com.cfg.shop.service;

import com.cfg.shop.domain.OrderItem;
import com.cfg.shop.domain.Product;
import com.cfg.shop.domain.ProductInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/7/2.
 */
public interface ShopService {
  /**
   * get products list of the shop
   * @return
   */
  List<Product>   getProductLists() ;

  /**
   * get all stock products informations
   * @return
   */
  List<ProductInfo> getProductStoreList() ;

  /**
   * get one product info with productno
   * @param productNo
   * @return
   */
  Product getProductByNo(String productNo);

  /**
   * check if there is enough products existed in the stock
   * @param product
   * @param Amount
   * @return
   */
  String checkStockProductAmount(Product product,Long Amount);

  /**
   * buy product process
   * @param orderNo order no
   * @param product
   * @param Amount   buy amount
   * @return
   */
  String buyProduct(String orderNo,Product product,Long Amount);

  /**
   * recover one order when the buy process failed,it is not happened always ,but we need to consider the solution
   * @param orderNo
   * @param productList
   * @return
   */
  String recoverProduct(String orderNo,List<Product> productList);

  /**
   * create an order no by uuid functions
   * @return
   */
  String createOrderNo();


  /**
   *
   * @param orderNo
   * @return
   */
  List<OrderItem> queryOrder(String orderNo);
}
