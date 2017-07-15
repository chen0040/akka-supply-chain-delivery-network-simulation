package com.github.chen0040.scsim.actors;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;
import com.github.chen0040.scsim.messages.inventory.ItemPickingProgress;
import com.github.chen0040.scsim.messages.inventory.ItemPickingQuery;
import com.github.chen0040.scsim.services.InventoryService;

/**
 * Created by xschen on 29/11/15.
 */
public class ItemStockActor extends AbstractActor {
    private String id;
    private int currentStock = 0;

    public ItemStockActor(String itemId){
        this.id = itemId;
        currentStock = InventoryService.getInitialStock(itemId);
        receive(ReceiveBuilder
            .match(ItemPickingQuery.class, message -> pickItem(message))
            .build());
    }

    private void pickItem(ItemPickingQuery query){
        int requiredUnitCount = query.getRequiredUnitCount();
        int pickUnitCount = 0;
        if(currentStock > 0){
            if(requiredUnitCount > currentStock){
                pickUnitCount = currentStock;
                currentStock = 0;
            } else {
                pickUnitCount = requiredUnitCount;
                currentStock -= requiredUnitCount;
            }
        }
        ItemPickingProgress stockInfo = new ItemPickingProgress();
        stockInfo.setItemInfo(query.getItemInfo());
        stockInfo.setPickedItemUnitCount(pickUnitCount);
        stockInfo.setRequiredUnitCount(requiredUnitCount);
        stockInfo.setSaleOrderId(query.getSaleOrderId());
        context().parent().tell(stockInfo, self());
    }

}
