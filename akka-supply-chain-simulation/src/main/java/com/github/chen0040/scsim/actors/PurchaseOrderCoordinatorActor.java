package com.github.chen0040.scsim.actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import com.github.chen0040.scsim.messages.ItemLine;
import com.github.chen0040.scsim.messages.inventory.ItemPickingCompleted;
import com.github.chen0040.scsim.messages.purchases.PurchaseOrder;
import com.github.chen0040.scsim.services.PurchaseOrderService;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by xschen on 30/11/15.
 */
public class PurchaseOrderCoordinatorActor extends AbstractActor {
    private ConcurrentHashMap<String, ActorRef> children = new ConcurrentHashMap<>();

    public PurchaseOrderCoordinatorActor(){
        receive(ReceiveBuilder
                .match(ItemPickingCompleted.class, message -> initializePurchaseOrder(message))
                .build());
    }

    private void initializePurchaseOrder(ItemPickingCompleted message){
        List<ItemLine> items2Purchase = message.getUnavailableItems();
        String saleOrderId = message.getSaleOrderId();

        List<PurchaseOrder> orders = PurchaseOrderService.createPurchaseOrders(saleOrderId, items2Purchase);

        for(int i=0; i < orders.size(); ++i){
            PurchaseOrder po = orders.get(i);
            ActorRef child = getOrCreateChild(po);
            child.tell(po, this.self());
        }

    }

    private ActorRef getOrCreateChild(PurchaseOrder po){
        String poId = po.getId();
        ActorRef child;
        if(children.containsKey(poId)){
            child = children.get(poId);
        } else {
            child = context().actorOf(Props.create(PurchaseOrderActor.class, ()->new PurchaseOrderActor(poId)), "PurchaseOrder-"+poId);
            children.put(poId, child);
        }
        return child;
    }

}
