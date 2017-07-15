package com.github.chen0040.scsim.messages.sales;

import akka.actor.ActorRef;
import com.github.chen0040.scsim.messages.ItemLine;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xschen on 29/11/15.
 */
public class SaleQuotationQuery {
    private String id;
    private List<ItemLine> lines = new ArrayList<>();
    private String customerId;
    private ActorRef sender;

    public void setSender(ActorRef sender){
        this.sender = sender;
    }

    public ActorRef getSender(){
        return sender;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getId() {

        return id;
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
}
