package com.github.chen0040.scsim.actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.japi.pf.ReceiveBuilder;
import com.github.chen0040.scsim.messages.customers.CustomerInfo;
import com.github.chen0040.scsim.messages.customers.CustomerInfoQuery;
import com.github.chen0040.scsim.services.CustomerInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by xschen on 29/11/15.
 */
public class CustomerMasterActor extends AbstractActor {
    private static final Logger logger = LoggerFactory.getLogger(CustomerMasterActor.class);

    public CustomerMasterActor(){
        logger.info("CustomerMaster created");

        receive(ReceiveBuilder
                .match(CustomerInfoQuery.class, message -> handleCustomerInfoRequest(message))
                .build());
    }

    private void handleCustomerInfoRequest(CustomerInfoQuery request){
        logger.info("accept customerInfoRequest: "+request.getCustomerId());

        CustomerInfo customerInfo = CustomerInfoService.findById(request.getCustomerId());
        ActorRef requester = sender();
        requester.tell(customerInfo, this.self());
    }

}
