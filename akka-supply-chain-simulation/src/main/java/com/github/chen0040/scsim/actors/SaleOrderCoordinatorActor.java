package com.github.chen0040.scsim.actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import com.github.chen0040.scsim.messages.sales.SaleOrder;
import com.github.chen0040.scsim.messages.sales.SaleQuotation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by xschen on 29/11/15.
 */
public class SaleOrderCoordinatorActor extends AbstractActor {
    private static final Logger logger = LoggerFactory.getLogger(SaleOrderCoordinatorActor.class);

    private ConcurrentHashMap<String, ActorRef> saleOrderActors = new ConcurrentHashMap<>();

    public SaleOrderCoordinatorActor(){
        receive(ReceiveBuilder
                .match(SaleOrder.class, co-> handleSaleOrderCreated(co))
                .match(SaleQuotation.class, quotation->createSaleOrder(quotation))
                .build());
    }

    private void handleSaleOrderCreated(SaleOrder saleOrder){
        logger.info("saleOrder created: "+saleOrder.getId());
        this.context().parent().tell(saleOrder, this.self());
    }

    private void createSaleOrder(SaleQuotation co){
        String coId = co.getId();
        ActorRef qoActor;
        if(saleOrderActors.containsKey(coId)){
            qoActor = saleOrderActors.get(coId);
        } else {
            String qoId = coId;
            qoActor = context().actorOf(Props.create(SaleOrderActor.class, ()->new SaleOrderActor(qoId)), "SaleOrder-"+qoId);
            saleOrderActors.put(qoId, qoActor);
        }

        logger.info("delegate sale order");

        qoActor.tell(co, sender());
    }


}
