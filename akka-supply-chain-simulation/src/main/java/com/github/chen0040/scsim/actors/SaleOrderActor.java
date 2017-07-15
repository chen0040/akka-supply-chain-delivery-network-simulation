package com.github.chen0040.scsim.actors;

import akka.actor.AbstractActor;
import akka.actor.ActorSelection;
import akka.japi.pf.ReceiveBuilder;
import com.github.chen0040.scsim.messages.Marking;
import com.github.chen0040.scsim.messages.SaleOrderChangeNote;
import com.github.chen0040.scsim.messages.sales.SaleOrder;
import com.github.chen0040.scsim.messages.sales.SaleQuotation;
import com.github.chen0040.scsim.services.SaleOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.PartialFunction;
import scala.runtime.BoxedUnit;

/**
 * Created by xschen on 29/11/15.
 */
public class SaleOrderActor extends AbstractActor {

    private static final Logger logger = LoggerFactory.getLogger(SaleOrderActor.class);
    private String id;
    private SaleOrder saleOrder;
    private SaleQuotation quotation;

    public SaleOrderActor(String id){
        this.id = id;

        this.context().become(workInProgress());
    }

    private PartialFunction<Object, BoxedUnit> workInProgress(){
        return ReceiveBuilder
                .match(SaleQuotation.class, message -> createSaleOrder(message))
                .match(Marking.class, message -> updateMarking(message))
                .match(SaleOrderChangeNote.class, message -> updateSaleOrderChangeNote(message))
                .build();
    }

    private void createSaleOrder(SaleQuotation po){
        logger.info("createSaleOrder invoked: "+po.getId());

        quotation = po;
        saleOrder = SaleOrderService.createSaleOrder(po);

        ask4Marking(saleOrder);
        ask4SOChangeNote(saleOrder);
    }

    private void ask4Marking(SaleOrder saleOrder){
        ActorSelection vesselSchedule = this.context().actorSelection("/user/VesselSchedule");
        vesselSchedule.tell(saleOrder, this.self());
    }

    private void ask4SOChangeNote(SaleOrder saleOrder){
        ActorSelection saleOrderChangeNoteActor = this.context().actorSelection("/user/Sale/SaleOrderChangeNote");
        saleOrderChangeNoteActor.tell(saleOrder, this.self());
    }


    private void updateMarking(Marking marking){
        saleOrder.setMarking(marking);
        updateStatus();
    }

    private void updateSaleOrderChangeNote(SaleOrderChangeNote message){
        saleOrder.setChangeNote(message);
        updateStatus();
    }

    private void updateStatus(){
        if(saleOrder.isValid()){
            this.context().parent().tell(saleOrder, this.self());
        }
    }

    @Override
    public void preStart(){
        logger.info("preStart invoked.");
    }

    @Override
    public void postStop(){
        logger.info("postStop invoked.");
    }
}
