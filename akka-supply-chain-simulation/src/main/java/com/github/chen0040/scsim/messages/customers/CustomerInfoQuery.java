package com.github.chen0040.scsim.messages.customers;

/**
 * Created by xschen on 29/11/15.
 */
public class CustomerInfoQuery {
    private String customerId;

    public void setCustomerId(String customerId){
        this.customerId = customerId;
    }

    public String getCustomerId() {
        return customerId;
    }
}
