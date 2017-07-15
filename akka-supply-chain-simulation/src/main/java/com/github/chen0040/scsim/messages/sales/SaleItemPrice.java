package com.github.chen0040.scsim.messages.sales;

import com.github.chen0040.scsim.messages.inventory.ItemInfo;

/**
 * Created by xschen on 29/11/15.
 */
public class SaleItemPrice {
    private ItemInfo itemInfo;
    private double price;

    public SaleItemPrice(ItemInfo itemInfo, double price){
        this.itemInfo = itemInfo;
        this.price = price;
    }

    public ItemInfo getItemInfo() {
        return itemInfo;
    }

    public double getPrice() {
        return price;
    }
}
