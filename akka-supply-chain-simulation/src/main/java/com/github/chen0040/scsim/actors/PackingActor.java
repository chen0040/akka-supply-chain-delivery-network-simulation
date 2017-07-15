package com.github.chen0040.scsim.actors;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;
import com.github.chen0040.scsim.messages.inventory.ItemPickingCompleted;
import com.github.chen0040.scsim.messages.inventory.ItemPickingProgress;
import com.github.chen0040.scsim.messages.inventory.Packing;
import com.github.chen0040.scsim.messages.inventory.PackingInitialized;
import com.github.chen0040.scsim.messages.sales.SaleOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by xschen on 29/11/15.
 */
public class PackingActor extends AbstractActor {
    private String saleOrderId;
    private SaleOrder saleOrder;
    private Packing packing;
    private static final Logger logger = LoggerFactory.getLogger(PackingActor.class);

    public PackingActor(String saleOrderId){
        this.saleOrderId = saleOrderId;
        receive(ReceiveBuilder
                .match(ItemPickingProgress.class, message -> handleItemPickingProgress(message))
                .match(SaleOrder.class, message -> initializePacking(message))
                .build());
    }

    private void initializePacking(SaleOrder saleOrder){
        packing = new Packing();
        packing.setSaleOrder(saleOrder);


        context().parent().tell(new PackingInitialized(packing), this.self());
    }

    private void handleItemPickingProgress(ItemPickingProgress pickingInfo){
        packing.updatePicking(pickingInfo);

        if(packing.isPickingCompleted()){
            logger.info("Picking completed for SaleOrder: "+saleOrderId);
            this.context().parent().tell(new ItemPickingCompleted(packing), this.self());
        }
    }


}
