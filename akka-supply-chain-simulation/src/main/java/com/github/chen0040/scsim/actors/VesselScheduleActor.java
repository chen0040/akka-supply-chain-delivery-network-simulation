package com.github.chen0040.scsim.actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.japi.pf.ReceiveBuilder;
import com.github.chen0040.scsim.messages.Marking;
import com.github.chen0040.scsim.messages.sales.SaleOrder;
import com.github.chen0040.scsim.services.VesselScheduleService;

/**
 * Created by xschen on 29/11/15.
 */
public class VesselScheduleActor extends AbstractActor {
    public VesselScheduleActor(){
        receive(ReceiveBuilder
                .match(SaleOrder.class, message -> getMarking4SaleOrder(message))
                .build());
    }

    private void getMarking4SaleOrder(SaleOrder so){
        Marking marking = VesselScheduleService.getMarking4SaleOrder(so);
        ActorRef requester = sender();

        requester.tell(marking, this.self());
    }
}
