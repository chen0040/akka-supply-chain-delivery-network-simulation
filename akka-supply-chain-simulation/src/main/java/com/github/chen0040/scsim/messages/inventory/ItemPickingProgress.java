package com.github.chen0040.scsim.messages.inventory;

/**
 * Created by xschen on 29/11/15.
 */
public class ItemPickingProgress {
    private int requiredUnitCount;
    private int availableUnitCount;
    private ItemInfo itemInfo;
    private String saleOrderId;

    public String getSaleOrderId() {
        return saleOrderId;
    }

    public void setSaleOrderId(String saleOrderId) {
        this.saleOrderId = saleOrderId;
    }

    public int getPickedItemUnitCount() {
        return availableUnitCount;
    }

    public void setPickedItemUnitCount(int availableUnitCount) {
        this.availableUnitCount = availableUnitCount;
    }

    public int getRequiredUnitCount() {
        return requiredUnitCount;
    }

    public void setRequiredUnitCount(int requiredUnitCount) {
        this.requiredUnitCount = requiredUnitCount;
    }

    public ItemInfo getItemInfo() {
        return itemInfo;
    }

    public void setItemInfo(ItemInfo itemInfo) {
        this.itemInfo = itemInfo;
    }
}
