package com.github.chen0040.scsim.services;

import com.github.chen0040.scsim.messages.sales.SaleQuotationQuery;
import com.github.chen0040.scsim.messages.sales.SaleQuotation;

import java.util.UUID;

/**
 * Created by xschen on 29/11/15.
 */
public class SaleQuotationService {
    public static SaleQuotation createByRequest(SaleQuotationQuery po) {
        SaleQuotation quotation = new SaleQuotation();
        quotation.setId(UUID.randomUUID().toString());
        quotation.setLines(po.getLines());
        quotation.setCustomerId(po.getCustomerId());

        return quotation;
    }
}
