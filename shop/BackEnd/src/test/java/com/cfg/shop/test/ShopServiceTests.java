package com.cfg.shop.test;

/**
 * Created by Administrator on 2017/7/3.
 */

import com.cfg.shop.ShopServerApplication;
import com.cfg.shop.domain.PRODUCT_STATUS;
import com.cfg.shop.domain.Product;
import com.cfg.shop.service.ShopService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;

/**
 * Created by yhp to test the shopservice interface
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=ShopServerApplication.class)
public class ShopServiceTests {
    @Autowired
    private ShopService shopService;

    @Test
    public void showShopProducts()  {
        List<Product> productList=shopService.getProductLists();
        Assert.assertNotNull("返回产品为空",productList);
        Assert.assertEquals(productList.size(),2);
        //Assert.assertFalse("数据不一致", !expectedResult.equals(result));
    }


    @Test
    public void buyShopProducts()  {
        String orderNo=shopService.createOrderNo();
        Assert.assertNotNull("订单编号为空",orderNo);

        Product  productA=new Product("ProductNoA","A", PRODUCT_STATUS.ONSALE);

        Product  productC=new Product("ProductNoC","C", PRODUCT_STATUS.ONSALE);

        String result=shopService.buyProduct(orderNo,productA,1L);
        Assert.assertNull("购买产品失败",result);

        result=shopService.buyProduct(orderNo,productA,100L);
        Assert.assertNotNull("购买产品数量超库存",result);

        result=shopService.buyProduct(orderNo,productC,1L);
        Assert.assertNotNull("购买库存中不存在的产品",result);

        result=shopService.checkStockProductAmount(productA,10L);
        Assert.assertNull("库存数量异常",result);


    }
}
