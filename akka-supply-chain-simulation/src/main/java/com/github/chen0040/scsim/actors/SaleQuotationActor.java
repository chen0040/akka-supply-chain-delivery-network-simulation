package com.github.chen0040.scsim.actors;

import akka.actor.AbstractActor;
import akka.actor.ActorSelection;
import akka.japi.pf.ReceiveBuilder;
import com.github.chen0040.scsim.messages.ItemLine;
import com.github.chen0040.scsim.messages.customers.CustomerInfo;
import com.github.chen0040.scsim.messages.customers.CustomerInfoQuery;
import com.github.chen0040.scsim.messages.inventory.ItemInfo;
import com.github.chen0040.scsim.messages.sales.SaleItemPrice;
import com.github.chen0040.scsim.messages.sales.SaleItemPriceQuery;
import com.github.chen0040.scsim.messages.sales.SaleQuotation;
import com.github.chen0040.scsim.messages.sales.SaleQuotationQuery;
import com.github.chen0040.scsim.services.SaleQuotationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.PartialFunction;
import scala.runtime.BoxedUnit;

import java.util.List;

/**
 * Created by xschen on 29/11/15.
 */
public class SaleQuotationActor extends AbstractActor {

    private static final Logger logger = LoggerFactory.getLogger(SaleQuotationActor.class);
    private String id;
    private SaleQuotation quotation;
    private SaleQuotationQuery request;

    public SaleQuotationActor(String id){
        this.id = id;

        this.context().become(workInProgress());
    }

    private PartialFunction<Object, BoxedUnit> workInProgress(){
        return ReceiveBuilder
                .match(SaleQuotationQuery.class, (message)->createQuotation(message))
                .match(SaleItemPrice.class, (message)->updateSaleItemPrice(message))
                .match(CustomerInfo.class, (message)->updateCustomerInfo(message))
                .build();
    }

    private void createQuotation(SaleQuotationQuery po){
        logger.info("createQuotation invoked: "+po.getId());

        request = po;
        quotation = SaleQuotationService.createByRequest(po);

        ask4CustomerInfo(quotation);
        ask4SaleItemPrices(quotation);
    }

    private void ask4SaleItemPrices(SaleQuotation request){
        List<ItemLine> lines = request.getLines();
        for(int i=0; i < lines.size(); ++i){
            ask4SaleItemPrice(lines.get(i).getItemInfo());
        }
    }

    private void ask4SaleItemPrice(ItemInfo itemInfo){
        ActorSelection customerMaster = this.context().actorSelection("/user/Sale/SaleItemPriceBook");
        customerMaster.tell(new SaleItemPriceQuery(itemInfo), this.self());
    }

    private void ask4CustomerInfo(SaleQuotation request){
        ActorSelection customerMaster = this.context().actorSelection("/user/Sale/CustomerMaster");

        CustomerInfoQuery customerInfoRequest = new CustomerInfoQuery();
        customerInfoRequest.setCustomerId(request.getCustomerId());
        customerMaster.tell(customerInfoRequest, this.self());
    }

    private void updateCustomerInfo(CustomerInfo customerInfo){
        logger.info("update customer info in quotation "+quotation.getId());
        quotation.setCustomerInfo(customerInfo);
        updateStatus();
    }

    private void updateSaleItemPrice(SaleItemPrice message){
        logger.info("update sale item price: "+message.getItemInfo().getName());

        ItemInfo itemInfo = message.getItemInfo();
        String itemId = itemInfo.getId();
        List<ItemLine> lines = quotation.getLines();
        for(int i=0; i < lines.size(); ++i){
            ItemLine line = lines.get(i);
            if(line.getItemInfo().getId().equals(itemId)){
                line.setUnitPrice(message.getPrice());
                break;
            }
        }
        updateStatus();
    }

    private void updateStatus(){
        if(quotation.isValid()){
            this.context().parent().tell(quotation, this.self());
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
