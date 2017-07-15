package com.github.chen0040.scsim.services;

import com.github.chen0040.scsim.messages.sales.SaleOrder;
import com.github.chen0040.scsim.messages.sales.SaleQuotation;

import java.util.UUID;

/**
 * Created by xschen on 29/11/15.
 */
public class SaleOrderService {
    public static SaleOrder createSaleOrder(SaleQuotation quotation){
        SaleOrder po = new SaleOrder();

        po.setId(UUID.randomUUID().toString());
        po.setCustomerId(quotation.getCustomerId());
        po.setCustomerInfo(quotation.getCustomerInfo());
        po.setLines(quotation.getLines());

        return po;
    }
}
