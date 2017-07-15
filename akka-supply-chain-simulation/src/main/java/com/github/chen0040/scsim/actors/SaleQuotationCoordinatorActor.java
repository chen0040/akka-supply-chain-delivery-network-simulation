package com.github.chen0040.scsim.actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import com.github.chen0040.scsim.messages.sales.SaleQuotationQuery;
import com.github.chen0040.scsim.messages.sales.SaleQuotation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by xschen on 29/11/15.
 */
public class SaleQuotationCoordinatorActor extends AbstractActor {
    private static final Logger logger = LoggerFactory.getLogger(SaleQuotationCoordinatorActor.class);

    private ConcurrentHashMap<String, ActorRef> saleQuotationRefs = new ConcurrentHashMap<>();

    public SaleQuotationCoordinatorActor(){
        receive(ReceiveBuilder
                .match(SaleQuotationQuery.class, co-> createSaleQuotation(co))
                .match(SaleQuotation.class, quotation->handleQuotationCreated(quotation))
                .build());
    }

    private void handleQuotationCreated(SaleQuotation quotation){
        logger.info("quotation created: "+quotation.getId());

        this.context().stop(sender());

        this.context().parent().tell(quotation, this.self());
    }

    private void createSaleQuotation(SaleQuotationQuery co){
        String coId = co.getId();
        ActorRef qoActor;
        if(saleQuotationRefs.containsKey(coId)){
            qoActor = saleQuotationRefs.get(coId);
        } else {
            String qoId = coId;
            qoActor = context().actorOf(Props.create(SaleQuotationActor.class, ()->new SaleQuotationActor(qoId)), "SaleQuotation-"+qoId);
            saleQuotationRefs.put(qoId, qoActor);
        }

        logger.info("delegate quotation order");

        ActorRef customer = sender();
        qoActor.tell(co, customer);
    }


}
