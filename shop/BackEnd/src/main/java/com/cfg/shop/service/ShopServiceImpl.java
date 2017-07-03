package com.cfg.shop.service;

import com.cfg.shop.domain.*;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Administrator on 2017/7/2.
 */
@Service
public class ShopServiceImpl implements  ShopService {

  private static final String MODEL_PREFIX = "CFG";

  @Autowired
  StockService stockService;
  @Autowired
  UtilityService utilityService;
  @Override
  public List<Product>   getProductLists() {
    List<Product> productLists=new ArrayList<Product>();

    Product  productA=new Product("ProductNoA","A",PRODUCT_STATUS.ONSALE);
    Product  productB=new Product("ProductNoB","B",PRODUCT_STATUS.ONSALE);
    productLists.add(productA);
    productLists.add(productB);
    return productLists;
  }

  @Override
  public List<ProductInfo> getProductStoreList() {
    List<Product>  theProductList =getProductLists();
    List<ProductInfo> piList=new ArrayList<ProductInfo>();
    for(int i=0;i<theProductList.size();i++)
    {
      Product product=theProductList.get(i);
      ProductInfo  productInfo=new ProductInfo();
      productInfo.setProductNo(product.getNo());
      productInfo.setProductNumber(stockService.countByProduct(product));
      productInfo.setProductName(product.getName());
      piList.add(productInfo);
    }
    return piList;

  }
  @Override
  public Product getProductByNo(String productNo)
  {
    List<Product> productList=  getProductLists();
    if(productList!=null)
    {
      for(Product product:productList)
      {
        if(product.getNo().equals(productNo))
        {
          return product;

        }
      }

    }
   return null;
  }
  @Override
  public String createOrderNo()
  {
    String no=utilityService.getHumanReadableIdByModelPrefix(MODEL_PREFIX);
    return no;
  }

  @Override
  public String buyProduct(String orderNo,Product product,Long Amount)
  {
    return stockService.outStockSKUByProductAndAmount(orderNo,product,Amount);
  }


  @Override
  public  String checkStockProductAmount(Product product,Long Amount)
  {
    Long existAmount=stockService.countByProduct(product);
    if(existAmount<Amount)
    {
      return "no enough products in stock,please contact us for help.";
    }
    return null;
  }

  @Override
  public String recoverProduct(String orderNo,List<Product> productList)
  {
    String msg=null;
    if(productList==null) return null;
    for(Product product:productList)
    {
      msg=stockService.recoverStockSKUByProductAndAmount(orderNo,product);
      if(msg!=null) break;
    }
    return msg;

  }
  @Override
  public List<OrderItem> queryOrder(String orderNo)
  {
    return stockService.queryOrder(orderNo);

  }
}
