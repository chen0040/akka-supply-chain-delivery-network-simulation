package com.github.chen0040.scsim.actors;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;
import com.github.chen0040.scsim.messages.purchases.PurchaseOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.PartialFunction;
import scala.runtime.BoxedUnit;

/**
 * Created by xschen on 30/11/15.
 */
public class PurchaseOrderActor extends AbstractActor {
    private PurchaseOrder purchaseOrder;
    private String purchaseOrderId;
    private Logger logger = LoggerFactory.getLogger(PurchaseOrderActor.class);

    public PurchaseOrderActor(String id){
        this.purchaseOrderId = id;

        context().become(workInProgress());
    }

    private PartialFunction<Object, BoxedUnit> workInProgress(){
        return ReceiveBuilder
                .match(PurchaseOrder.class, message -> initializePurchaseOrder(message))
                .build();
    }

    private void initializePurchaseOrder(PurchaseOrder purchaseOrder){
        logger.info("initialize PurchaseOrder: "+purchaseOrder.getId());
    }
}
