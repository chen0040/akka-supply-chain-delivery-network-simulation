package com.github.chen0040.scsim.messages.sales;

import com.github.chen0040.scsim.messages.ItemLine;
import com.github.chen0040.scsim.messages.Marking;
import com.github.chen0040.scsim.messages.SaleOrderChangeNote;
import com.github.chen0040.scsim.messages.customers.CustomerInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xschen on 29/11/15.
 */
public class SaleOrder {
    private String id;
    private List<ItemLine> lines = new ArrayList<>();
    private CustomerInfo customerInfo;
    private String customerId;
    private Marking marking;
    private SaleOrderChangeNote changeNote;

    public String getId() {
        return id;
    }

    public CustomerInfo getCustomerInfo() {
        return customerInfo;
    }

    public void setCustomerInfo(CustomerInfo customerInfo) {
        this.customerInfo = customerInfo;
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

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }


    public boolean isValid() {
        if(marking == null) return false;
        if(changeNote == null) return false;
        if(customerInfo == null) return false;
        for(int i=0; i < lines.size(); ++i){
            if(!lines.get(i).isValid()){
                return false;
            }
        }
        return true;
    }

    public void setMarking(Marking marking) {
        this.marking = marking;
    }

    public Marking getMarking(){
        return this.marking;
    }

    public void setChangeNote(SaleOrderChangeNote changeNote){
        this.changeNote = changeNote;
    }

    public SaleOrderChangeNote getChangeNote(){
        return changeNote;
    }
}
