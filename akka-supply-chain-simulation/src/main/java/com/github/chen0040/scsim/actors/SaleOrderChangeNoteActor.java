package com.github.chen0040.scsim.actors;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;
import com.github.chen0040.scsim.messages.SaleOrderChangeNote;
import com.github.chen0040.scsim.messages.sales.SaleOrder;
import com.github.chen0040.scsim.services.SaleOrderChangeNoteService;

/**
 * Created by xschen on 29/11/15.
 */
public class SaleOrderChangeNoteActor extends AbstractActor {
    public SaleOrderChangeNoteActor(){
        receive(ReceiveBuilder.match(SaleOrder.class, message -> getSaleOrderChangeNote(message)).build());
    }

    private void getSaleOrderChangeNote(SaleOrder saleOrder){
        SaleOrderChangeNote note = SaleOrderChangeNoteService.getSaleOrderChangeNote(saleOrder);
        this.sender().tell(note, this.self());
    }
}
