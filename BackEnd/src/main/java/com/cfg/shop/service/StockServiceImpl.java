package com.cfg.shop.service;

import com.cfg.shop.domain.PRODUCT_STATUS;
import com.cfg.shop.domain.Product;
import com.cfg.shop.domain.ProductItem;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Vector;

/**
 * Created by Administrator on 2017/7/2.
 */
@Service
public class StockServiceImpl  implements  StockService{
    private static LinkedHashMap<String, Vector<ProductItem>> stockProducts = new LinkedHashMap<String, Vector<ProductItem>>();
    private static String CONST_PRODUCTA_ID="ProductNoA";
    private static String CONST_PRODUCTB_ID="ProductNoB";

    //初始化库存
    @PostConstruct
    public void initStore() {

        Vector<ProductItem>   currentProductItemA=  new Vector();
        Date instoreTime=new Date();
        for(int i=0;i<20;i++)
        {
            ProductItem item =new ProductItem();
            item.setProductId(CONST_PRODUCTA_ID);
            item.setItemNo("ProductItemA"+i);
            item.setStatus(PRODUCT_STATUS.INSTORE);
            item.setInStoreTime(instoreTime);
            item.setOutStoreTime(null);
            currentProductItemA.addElement(item);
        }
        stockProducts.put(CONST_PRODUCTA_ID,currentProductItemA);

        Vector<ProductItem>   currentProductItemB=  new Vector();

        for(int i=0;i<10;i++)
        {
            ProductItem item =new ProductItem();
            item.setProductId(CONST_PRODUCTB_ID);
            item.setItemNo("ProductItemA"+i);
            item.setInStoreTime(instoreTime);
            item.setStatus(PRODUCT_STATUS.INSTORE);
            item.setOutStoreTime(null);
            currentProductItemB.addElement(item);
        }
        stockProducts.put(CONST_PRODUCTB_ID,currentProductItemB);

    }



    @Override
    public  Long countByProduct(Product product)
    {

        Long amount=0L;
        if(stockProducts!=null)
        {
            Vector<ProductItem>   currentProductItem=stockProducts.get(product.getNo());
            if(currentProductItem!=null)
            {
                for(int i = 0;i < currentProductItem.size();i++){
                    ProductItem productItem=currentProductItem.get(i);
                    if(productItem.getStatus()==PRODUCT_STATUS.INSTORE)
                    {
                        amount++;
                    }
                }
            }
        }

        return amount;

    }

    @Override
    public String outStockSKUByProductAndAmount(Product product, Long amount)
    {

        Long currentStoreNumber=countByProduct(product);
        if(currentStoreNumber<amount)
        {

            return "很抱歉，库存不足";
        }
        else
        {

            if(stockProducts!=null)
            {
                Vector<ProductItem>   currentProductItem=stockProducts.get(product.getNo());
                if(currentProductItem!=null)
                {
                    int count=0;
                    for(int i = 0;i < currentProductItem.size();i++){
                        ProductItem productItem=currentProductItem.get(i);
                        if(productItem.getStatus()==PRODUCT_STATUS.INSTORE)
                        {
                            productItem.setStatus(PRODUCT_STATUS.PURCHASED);
                            currentProductItem.set(i,productItem);
                            count++;
                        }
                        if(count>=amount)break;
                    }
                }
                else
                {

                    return "很抱歉，库存中没有该商品信息";
                }
            }


        }
        return   null;
    }



}
