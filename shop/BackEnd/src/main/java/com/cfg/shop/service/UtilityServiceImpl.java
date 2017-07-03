/*
 * Powered By [rongxinzaixian]
 * Since 2008 - 2016
 * v1 .. YeHaping
 */
package com.cfg.shop.service;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;
/**
 * Created by Administrator on 2017/7/2.
 * to create an unique serial no that can be used for order,transaction,pay etc
 */

@Service
class UtilityServiceImpl implements UtilityService{
private static  String PRODUCT_NAME="E";
    private static int SUFFIXLEN=4;

    @Override
    public String getHumanReadableIdByModelPrefix(String modelPrefix)
    {
        String uuid= UUID.randomUUID().toString().replace("-", "");
        String suffix=uuid.substring(0,SUFFIXLEN);
        java.text.DateFormat format2 = new java.text.SimpleDateFormat("yyyyMMddHHmmss");
        String no = PRODUCT_NAME+ modelPrefix +format2.format(new Date())+suffix;
        return no.toUpperCase();

    }

    @Override
    public String getUUID()
    {
        String uuid= UUID.randomUUID().toString().replace("-", "");
        return uuid;

    }

}
