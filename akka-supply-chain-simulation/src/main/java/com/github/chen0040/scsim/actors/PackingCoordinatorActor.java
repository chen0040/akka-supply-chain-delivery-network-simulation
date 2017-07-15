package com.github.chen0040.scsim.actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import com.github.chen0040.scsim.messages.inventory.ItemPickingCompleted;
import com.github.chen0040.scsim.messages.inventory.ItemPickingProgress;
import com.github.chen0040.scsim.messages.inventory.PackingInitialized;
import com.github.chen0040.scsim.messages.sales.SaleOrder;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by xschen on 29/11/15.
 */
public class PackingCoordinatorActor extends AbstractActor {
    private ConcurrentHashMap<String, ActorRef> children = new ConcurrentHashMap<>();

    public PackingCoordinatorActor(){
        receive(ReceiveBuilder
                .match(SaleOrder.class, message -> initializePacking(message))
                .match(PackingInitialized.class, message -> handlePackingInitialized(message))
                .match(ItemPickingProgress.class, message -> handleItemPickingProgress(message))
                .match(ItemPickingCompleted.class, message -> handleItemPickingCompleted(message))
                .build());
    }

    private void handlePackingInitialized(PackingInitialized message){
        context().parent().tell(message, this.self());
    }

    private void initializePacking(SaleOrder saleOrder){
        String saleOrderId = saleOrder.getId();
        ActorRef child;
        if(children.containsKey(saleOrderId)){
            child = children.get(saleOrderId);
        } else {
            child = context().actorOf(Props.create(PackingActor.class, ()->new PackingActor(saleOrderId)), "Packing-"+saleOrderId);
            children.put(saleOrderId, child);
        }

        child.tell(saleOrder, this.self());
    }

    private void handleItemPickingProgress(ItemPickingProgress pickingInfo){
        String saleOrderId = pickingInfo.getSaleOrderId();
        ActorRef child;
        if(children.containsKey(saleOrderId)){
            child = children.get(saleOrderId);
        } else {
            child = context().actorOf(Props.create(PackingActor.class, ()->new PackingActor(saleOrderId)), "Packing-"+saleOrderId);
            children.put(saleOrderId, child);
        }

        child.tell(pickingInfo, this.self());
    }

    private void handleItemPickingCompleted(ItemPickingCompleted completed){
        context().parent().tell(completed, this.self());
    }
}
