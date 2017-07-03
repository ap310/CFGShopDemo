
package com.cfg.shop.service;

/**
 * Created by Administrator on 2017/7/2.
 */

public interface UtilityService {
     //to create an order no
     String getHumanReadableIdByModelPrefix(String modelPrefix);

     String getUUID();
}
