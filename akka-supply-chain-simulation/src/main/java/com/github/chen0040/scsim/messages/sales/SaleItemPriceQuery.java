package com.github.chen0040.scsim.messages.sales;

import com.github.chen0040.scsim.messages.inventory.ItemInfo;

/**
 * Created by xschen on 29/11/15.
 */
public class SaleItemPriceQuery {
    private ItemInfo itemInfo;
    public SaleItemPriceQuery(ItemInfo itemInfo){
        this.itemInfo = itemInfo;
    }

    public ItemInfo getItemInfo(){
        return itemInfo;
    }
}
