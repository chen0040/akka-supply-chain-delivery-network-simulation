package com.github.chen0040.scsim.actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import com.github.chen0040.scsim.messages.inventory.ItemPickingCompleted;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.PartialFunction;
import scala.runtime.BoxedUnit;

/**
 * Created by xschen on 29/11/15.
 */
public class PurchaseActor extends AbstractActor {

    private static Logger logger = LoggerFactory.getLogger(PurchaseActor.class);

    private ActorRef purchaseOrderCoordinator;

    public PurchaseActor(){
        purchaseOrderCoordinator = context().actorOf(Props.create(PurchaseOrderCoordinatorActor.class, ()->new PurchaseOrderCoordinatorActor()), "PurchaseOrderCoordinator");
        context().become(workInProgress());
    }

    private PartialFunction<Object, BoxedUnit> workInProgress(){
        return ReceiveBuilder
                .match(ItemPickingCompleted.class, message -> initializePurchaseOrder(message))
                .build();
    }

    private void initializePurchaseOrder(ItemPickingCompleted message) {
        purchaseOrderCoordinator.tell(message, this.self());
    }

}
