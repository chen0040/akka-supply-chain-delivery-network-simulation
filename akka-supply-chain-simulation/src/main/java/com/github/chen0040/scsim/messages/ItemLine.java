package com.github.chen0040.scsim.messages;

import com.github.chen0040.scsim.messages.inventory.ItemInfo;

/**
 * Created by xschen on 29/11/15.
 */
public class ItemLine implements Cloneable {
    private String id;
    private ItemInfo itemInfo;
    private double unitPrice = -1;
    private int unitCount = 1;

    public void copy(ItemLine rhs){
        id = rhs.id;
        itemInfo = rhs.itemInfo;
        unitPrice = rhs.unitPrice;
        unitCount = rhs.unitCount;
    }

    @Override
    public Object clone(){
        ItemLine clone = new ItemLine();
        clone.copy(this);
        return clone;
    }

    public void setItemInfo(ItemInfo itemInfo){
        this.itemInfo = itemInfo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ItemInfo getItemInfo() {
        return itemInfo;
    }

    public void setUnitPrice(double price) {
        unitPrice = price;
    }

    public double getUnitPrice(){
        return unitPrice;
    }

    public void setUnitCount(int unitCount) {
        this.unitCount = unitCount;
    }

    public int getUnitCount(){
        return unitCount;
    }

    public boolean isValid(){
        return unitPrice >= 0;
    }
}
