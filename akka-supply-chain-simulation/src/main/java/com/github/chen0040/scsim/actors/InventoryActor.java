package com.github.chen0040.scsim.actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import com.github.chen0040.scsim.messages.ItemLine;
import com.github.chen0040.scsim.messages.inventory.*;
import com.github.chen0040.scsim.messages.sales.SaleOrder;

import java.util.List;

/**
 * Created by xschen on 29/11/15.
 */
public class InventoryActor extends AbstractActor {
    private ActorRef itemStockCoordinator;
    private ActorRef packingCoordinator;

    public InventoryActor(){
        itemStockCoordinator = context().actorOf(Props.create(ItemStockCoordinatorActor.class, ()->new ItemStockCoordinatorActor()), "ItemStockCoordinator");
        packingCoordinator = context().actorOf(Props.create(PackingCoordinatorActor.class, ()->new PackingCoordinatorActor()), "PackingCoordinator");

        receive(ReceiveBuilder
                .match(SaleOrder.class, message -> handleSaleOrder(message))
                .match(PackingInitialized.class, message -> handlePackingInitialized(message))
                .match(ItemPickingProgress.class, message -> handleItemPickingProgress(message))
                .match(ItemPickingCompleted.class, message -> handleItemPickingCompleted(message))
                .build());


    }

    private void handlePackingInitialized(PackingInitialized message){
        Packing packing = message.getPacking();
        SaleOrder saleOrder = packing.getSaleOrder();
        List<ItemLine> lines = saleOrder.getLines();
        for(int i=0; i < lines.size(); ++i){
            ItemLine line = lines.get(i);
            ItemInfo itemInfo = line.getItemInfo();
            ItemPickingQuery query = new ItemPickingQuery();
            query.setRequiredUnitCount(line.getUnitCount());
            query.setItemInfo(itemInfo);
            query.setSaleOrderId(saleOrder.getId());

            itemStockCoordinator.tell(query, this.self());
        }
    }

    private void handleSaleOrder(SaleOrder message){
        packingCoordinator.tell(message, this.self());

    }

    private void handleItemPickingProgress(ItemPickingProgress pickingInfo){
        packingCoordinator.tell(pickingInfo, this.self());
    }

    private void handleItemPickingCompleted(ItemPickingCompleted completed){
        if(!completed.getUnavailableItems().isEmpty()) {
            context().actorSelection("/user/Purchase").tell(completed, this.self());
        }
    }



}
