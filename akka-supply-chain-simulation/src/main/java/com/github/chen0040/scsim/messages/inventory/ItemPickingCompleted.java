package com.github.chen0040.scsim.messages.inventory;

import com.github.chen0040.scsim.messages.ItemLine;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xschen on 29/11/15.
 */
public class ItemPickingCompleted {
    private String saleOrderId;
    private List<ItemLine> unavailableItems = new ArrayList<>();

    public ItemPickingCompleted(Packing packing){
        this.saleOrderId = packing.getSaleOrder().getId();
        unavailableItems = packing.getUnavailableItems();
    }

    public List<ItemLine> getUnavailableItems(){
        return unavailableItems;
    }

    public String getSaleOrderId(){
        return saleOrderId;
    }
}
