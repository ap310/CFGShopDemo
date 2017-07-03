package com.cfg.shop.service;

import com.cfg.shop.domain.*;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * Created by Administrator on 2017/7/2.
 */
@Service
public class StockServiceImpl  implements  StockService {
    private static LinkedHashMap<String, Vector<ProductItem>> stockProducts = new LinkedHashMap<String, Vector<ProductItem>>();
    private static String CONST_PRODUCTA_ID = "ProductNoA";
    private static String CONST_PRODUCTB_ID = "ProductNoB";

    //init the stock with ProductA and ProductB
    @PostConstruct
    public void initStore() {

        Vector<ProductItem> currentProductItemA = new Vector();
        Date inStockTime = new Date();
        for (int i = 0; i < 20; i++) {
            ProductItem item = new ProductItem();
            item.setProductId(CONST_PRODUCTA_ID);
            item.setItemNo("ProductItemA" + i);
            item.setStatus(PRODUCT_STOCK_STATUS.INSTORE);
            item.setInStockTime(inStockTime);
            item.setOutStockTime(null);
            item.setProductName("A");
            currentProductItemA.addElement(item);
        }
        stockProducts.put(CONST_PRODUCTA_ID, currentProductItemA);

        Vector<ProductItem> currentProductItemB = new Vector();

        for (int i = 0; i < 10; i++) {
            ProductItem item = new ProductItem();
            item.setProductId(CONST_PRODUCTB_ID);
            item.setItemNo("ProductItemA" + i);
            item.setInStockTime(inStockTime);
            item.setStatus(PRODUCT_STOCK_STATUS.INSTORE);
            item.setOutStockTime(null);
            item.setProductName("B");
            currentProductItemB.addElement(item);
        }
        stockProducts.put(CONST_PRODUCTB_ID, currentProductItemB);

    }

    @Override
    public Long countByProduct(Product product) {
        Long amount = 0L;
        if (stockProducts != null) {
            Vector<ProductItem> currentProductItem = stockProducts.get(product.getNo());
            if (currentProductItem != null) {
                for (int i = 0; i < currentProductItem.size(); i++) {
                    ProductItem productItem = currentProductItem.get(i);
                    if (productItem.getStatus() == PRODUCT_STOCK_STATUS.INSTORE) {
                        amount++;
                    }
                }
            }
        }
        return amount;
    }

    @Override
    public String outStockSKUByProductAndAmount(String orderNo, Product product, Long amount) {

        Long currentStoreNumber = countByProduct(product);
        if (currentStoreNumber < amount) {
            return "no enough products in stock,please contact us for help.";
        } else {
            if (stockProducts != null) {
                Vector<ProductItem> currentProductItem = stockProducts.get(product.getNo());
                if (currentProductItem != null) {
                    int count = 0;
                    Date outStockTime = new Date();
                    for (int i = 0; i < currentProductItem.size(); i++) {
                        ProductItem productItem = currentProductItem.get(i);
                        if (productItem.getStatus() == PRODUCT_STOCK_STATUS.INSTORE) {
                            productItem.setStatus(PRODUCT_STOCK_STATUS.PURCHASED);
                            productItem.setOrderNo(orderNo);
                            productItem.setOutStockTime(outStockTime);
                            currentProductItem.set(i, productItem);
                            count++;
                        }
                        if (count >= amount) break;
                    }
                } else {
                    return "no this product in stock,please contact us for help.";
                }
            }

        }
        return null;
    }


    @Override
    public String recoverStockSKUByProductAndAmount(String orderNo, Product product) {
        if (stockProducts != null) {
            Vector<ProductItem> currentProductItem = stockProducts.get(product.getNo());
            if (currentProductItem != null) {
                int count = 0;
                for (int i = 0; i < currentProductItem.size(); i++) {
                    ProductItem productItem = currentProductItem.get(i);
                    if (productItem!=null&&productItem.getOrderNo().equals(orderNo)) {
                        productItem.setStatus(PRODUCT_STOCK_STATUS.INSTORE);                  //recover the data to not bought status
                        productItem.setOrderNo(null);
                        productItem.setOutStockTime(null);
                        currentProductItem.set(i, productItem);
                        count++;
                    }
                }
            } else {
                return "no this product in stock,please contact us for help.";
            }
        }
        return null;

    }


    @Override
    public List<OrderItem> queryOrder(String orderNo) {
        List<OrderItem> orderItemList = new ArrayList<OrderItem>();
        if (stockProducts != null) {
            Iterator<Map.Entry<String, Vector<ProductItem>>> iterator = stockProducts.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = iterator.next();
                String productNo = entry.getKey() + "";
                Vector<ProductItem> currentProductItem = stockProducts.get(productNo);
                if (currentProductItem != null) {
                    Long amount = 0L;
                    String productName="";
                    for (int i = 0; i < currentProductItem.size(); i++) {
                        ProductItem productItem = currentProductItem.get(i);
                        productName=productItem.getProductName();
                        String productOrderNo=productItem.getOrderNo();
                        if (productOrderNo!=null&&productOrderNo.equals(orderNo) && productItem.getStatus() == PRODUCT_STOCK_STATUS.PURCHASED) {
                            amount++;
                        }
                    }
                    if (amount > 0) {
                        OrderItem orderItem = new OrderItem();
                        orderItem.setProductNo(productNo);
                        orderItem.setPurchasedNumber(amount);
                        orderItem.setProductName(productName);
                        orderItemList.add(orderItem);

                    }
                }

                //  System.out.println(entry.getKey()+":"+entry.getValue());
            }
        }
        return orderItemList;
    }
}
