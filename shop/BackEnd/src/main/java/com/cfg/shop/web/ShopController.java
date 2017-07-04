package com.cfg.shop.web;

import com.cfg.shop.domain.OrderItem;
import com.cfg.shop.domain.Product;
import com.cfg.shop.domain.ProductInfo;
import com.cfg.shop.service.ShopService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yhp on 2017/7/2.
 */
@RestController
@RequestMapping("/")
public class ShopController {

    @Autowired
    ShopService shopService;

    @Autowired
    UserDetailsService userDetailsService;

    private static final Logger logger = LoggerFactory.getLogger(ShopController.class);  //logger here can help us to fix and trace problems

    /**
     * to show the product information
     * in real world  we can not just show all data on one page
     * @return
     */
    @RequestMapping(value = "/show", method = RequestMethod.GET)
    public Map<String, Object> showProducts() {
        Map<String, Object> responseJson = new HashMap<String, Object>();
        List<ProductInfo> ProductList = shopService.getProductStoreList();   //show all stock products here no paging need as only 2 products and small amount
        responseJson.put("ProductInfos", ProductList);
        return responseJson;
    }

    /**
     * client order process
     *
     * @param OrderItems          orderitem list fromc client
     * @param httpServletResponse response need to transfer data to client
     * @return
     */
    @RequestMapping("/order")
    public Map<String, Object> Order(@RequestBody List<OrderItem> OrderItems, HttpServletResponse httpServletResponse) {
        Map<String, Object> responseJson = new HashMap<String, Object>();
        String error = "";
        if(OrderItems==null)
        {
            error = "input error,pleas contact the administrator";
            logger.error(error);
            responseJson.put("error", error);
            return responseJson;
        }
        String OrderNo = shopService.createOrderNo();
        List<Product> productList = new ArrayList<>();
        for (OrderItem orderItem : OrderItems) {
            Long amount = orderItem.getPurchasedNumber();
            if (amount <= 0) continue;  //amount ==0 ,no need to do next work for it
            Product product = shopService.getProductByNo(orderItem.getProductNo());
            if (product == null) {
                error = "Cannot find the product";
                logger.error(error);
                responseJson.put("error", error);
                break;

            }
            String result = shopService.checkStockProductAmount(product, orderItem.getPurchasedNumber());  //check the stock and to see if enough products exists
            if (result != null) {
                logger.error(result);
                responseJson.put("error", result);
                break;
            } else {
                result = shopService.buyProduct(OrderNo, product, orderItem.getPurchasedNumber());// process the buy product
                productList.add(product);
                if (result != null) {
                    logger.error(result);
                    responseJson.put("error", result);
                    result = shopService.recoverProduct(OrderNo, productList);  //when one products process failed here ,we need to recover all products belong to the order
                    break;
                }
            }
        }
        List<ProductInfo> ProductList = shopService.getProductStoreList();
        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        responseJson.put("ProductInfos", ProductList);
        responseJson.put("SummaryInfo", OrderItems);
        responseJson.put("OrderNo", OrderNo);
        return responseJson;
    }

    /**
     * for client to access and view the order information
     * here we need to check if the order no is invalid
     * @param orderNo
     * @param httpServletResponse
     * @return
     */
    @RequestMapping(value = "/queryOrder/{orderNo}", method = RequestMethod.GET)
    public Map<String, Object> QueryOrder(@PathVariable String orderNo, HttpServletResponse httpServletResponse) {
        Map<String, Object> responseJson = new HashMap<String, Object>();
        String error = "";
        if(orderNo==null)
        {
            error = "input error,pleas contact the administrator";
            logger.error(error);
            responseJson.put("error", error);
            return responseJson;
        }

        List<OrderItem> OrderItems = shopService.queryOrder(orderNo);
        if (OrderItems != null && OrderItems.isEmpty()) {
            responseJson.put("error", "invalid orderNo.");
        } else {
            responseJson.put("orderItems", OrderItems);
        }
        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        return responseJson;
    }

}
