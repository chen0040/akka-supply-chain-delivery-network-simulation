package com.github.chen0040.scsim.messages.inventory;

import com.github.chen0040.scsim.messages.ItemLine;
import com.github.chen0040.scsim.messages.sales.SaleOrder;

import java.util.*;

/**
 * Created by xschen on 29/11/15.
 */
public class Packing {
    private Map<String, Integer> completed = new HashMap<>();
    private Map<String, Integer> picked = new HashMap<>();

    private SaleOrder saleOrder;

    public SaleOrder getSaleOrder() {
        return saleOrder;
    }

    public void setSaleOrder(SaleOrder saleOrder) {
        this.saleOrder = saleOrder;
        completed.clear();
        List<ItemLine> lines = saleOrder.getLines();
        for(int i=0; i < lines.size(); ++i){
            ItemLine line = lines.get(i);
            ItemInfo itemInfo = line.getItemInfo();
            String itemId = itemInfo.getId();
            completed.put(itemId, 0);
        }
    }

    public List<ItemLine> getUnavailableItems(){
        List<ItemLine> result = new ArrayList<>();

        List<ItemLine> lines = saleOrder.getLines();
        for(int i=0; i < lines.size(); ++i) {
            ItemLine line = lines.get(i);
            ItemInfo itemInfo = line.getItemInfo();
            String itemId = itemInfo.getId();
            int remaining = line.getUnitCount() - completed.get(itemId);
            if(remaining > 0){
                ItemLine line2 = new ItemLine();
                line2.setItemInfo(itemInfo);
                line2.setId(UUID.randomUUID().toString());
                line2.setUnitCount(remaining);
                line2.setUnitPrice(line.getUnitPrice());
                result.add(line2);
            }
        }
        return result;
    }

    public void updatePicking(ItemPickingProgress pickingInfo){
        ItemInfo itemInfo = pickingInfo.getItemInfo();
        String itemId = itemInfo.getId();
        picked.put(itemId, pickingInfo.getPickedItemUnitCount());
        completed.put(itemId, pickingInfo.getPickedItemUnitCount() + completed.get(itemId));
    }

    public boolean isPickingCompleted(){
        for(String itemId : completed.keySet()){
            if(!picked.containsKey(itemId)){
                return false;
            }
        }
        return true;
    }

    public boolean isPackingCompleted(){
        List<ItemLine> lines = saleOrder.getLines();
        for(int i=0; i < lines.size(); ++i) {
            ItemLine line = lines.get(i);
            ItemInfo itemInfo = line.getItemInfo();
            String itemId = itemInfo.getId();
            if(completed.get(itemId) < line.getUnitCount()){
                return false;
            }
        }
        return true;
    }
}
