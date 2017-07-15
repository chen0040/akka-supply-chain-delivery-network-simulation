package com.github.chen0040.scsim.services;

import com.github.chen0040.scsim.messages.customers.CustomerInfo;

/**
 * Created by xschen on 29/11/15.
 */
public class CustomerInfoService {
    public static CustomerInfo findById(String customerId){
        CustomerInfo customerInfo = new CustomerInfo();
        customerInfo.setId(customerId);

        return customerInfo;
    }
}
