package com.github.chen0040.scsim.actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import com.github.chen0040.scsim.messages.inventory.ItemPickingProgress;
import com.github.chen0040.scsim.messages.inventory.ItemPickingQuery;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by xschen on 29/11/15.
 */
public class ItemStockCoordinatorActor extends AbstractActor {
    private ConcurrentHashMap<String, ActorRef> itemStockActors = new ConcurrentHashMap<>();

    public ItemStockCoordinatorActor(){
        receive(ReceiveBuilder
                .match(ItemPickingQuery.class, message -> pickItem(message))
                .match(ItemPickingProgress.class, message -> handleItemPicked(message))
                .build());
    }

    private void pickItem(ItemPickingQuery message){
        String itemId = message.getItemInfo().getId();
        ActorRef child;
        if(itemStockActors.containsKey(itemId)){
            child = itemStockActors.get(itemId);
        } else {
            child = context().actorOf(Props.create(ItemStockActor.class, ()->new ItemStockActor(itemId)), "ItemStock-"+itemId);
            itemStockActors.put(itemId, child);
        }

        child.tell(message, sender());
    }

    private void handleItemPicked(ItemPickingProgress message){
        context().parent().tell(message, this.self());
    }
}
