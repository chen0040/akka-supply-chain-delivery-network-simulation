package com.github.chen0040.scsim.actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import com.github.chen0040.scsim.messages.sales.SaleOrder;
import com.github.chen0040.scsim.messages.sales.SaleQuotation;
import com.github.chen0040.scsim.messages.sales.SaleQuotationQuery;

/**
 * Created by xschen on 29/11/15.
 */
public class SaleActor extends AbstractActor {
    private ActorRef saleItemPriceBook;
    private ActorRef customerMaster;
    private ActorRef saleQuotationCoordinator;
    private ActorRef saleOrderCoordinator;
    private ActorRef saleOrderChangeNote;

    public SaleActor(){
        saleItemPriceBook = context().actorOf(Props.create(SaleItemPriceBookActor.class, ()->new SaleItemPriceBookActor()), "SaleItemPriceBook");
        customerMaster = context().actorOf(Props.create(CustomerMasterActor.class, ()->new CustomerMasterActor()), "CustomerMaster");
        saleQuotationCoordinator = context().actorOf(Props.create(SaleQuotationCoordinatorActor.class, ()->new SaleQuotationCoordinatorActor()), "SaleQuotationCoordinator");
        saleOrderCoordinator = context().actorOf(Props.create(SaleOrderCoordinatorActor.class, ()->new SaleOrderCoordinatorActor()), "SaleOrderCoordinator");
        saleOrderChangeNote = context().actorOf(Props.create(SaleOrderChangeNoteActor.class, ()->new SaleOrderChangeNoteActor()), "SaleOrderChangeNote");

        receive(ReceiveBuilder
                .match(SaleQuotationQuery.class, message -> createSaleQuotation(message))
                .match(SaleQuotation.class, message -> handleSaleQuotationCreated(message))
                .match(SaleOrder.class, message -> handleSaleOrderCreated(message))
                .build());

    }

    private void createSaleQuotation(SaleQuotationQuery message){
        saleQuotationCoordinator.tell(message, sender());
    }

    private void handleSaleQuotationCreated(SaleQuotation message){
        saleOrderCoordinator.tell(message, sender());
    }

    private void handleSaleOrderCreated(SaleOrder message) {
        context().actorSelection("/user/Inventory").tell(message, this.self());
    }


}
