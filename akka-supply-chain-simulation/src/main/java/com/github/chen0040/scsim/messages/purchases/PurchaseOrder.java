package com.github.chen0040.scsim.messages.purchases;

import com.github.chen0040.scsim.messages.ItemLine;
import com.github.chen0040.scsim.messages.suppliers.SupplierInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xschen on 29/11/15.
 */
public class PurchaseOrder {
    private String id;
    private List<ItemLine> lines = new ArrayList<>();
    private SupplierInfo supplierInfo;
    private String saleOrderId;

    public String getId() {
        return id;
    }

    public SupplierInfo getSupplierInfo() {
        return supplierInfo;
    }

    public void setSupplierInfo(SupplierInfo supplierInfo) {
        this.supplierInfo = supplierInfo;
    }

    public String getSaleOrderId() {
        return saleOrderId;
    }

    public void setSaleOrderId(String saleOrderId) {
        this.saleOrderId = saleOrderId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<ItemLine> getLines() {
        return lines;
    }

    public void setLines(List<ItemLine> lines) {
        this.lines = lines;
    }


    public boolean isValid() {

        if(supplierInfo == null) return false;
        for(int i=0; i < lines.size(); ++i){
            if(!lines.get(i).isValid()){
                return false;
            }
        }
        return true;
    }
}
