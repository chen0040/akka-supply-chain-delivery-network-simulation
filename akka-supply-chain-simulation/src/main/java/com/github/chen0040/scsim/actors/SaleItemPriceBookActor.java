package com.github.chen0040.scsim.actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.japi.pf.ReceiveBuilder;
import com.github.chen0040.scsim.messages.inventory.ItemInfo;
import com.github.chen0040.scsim.messages.sales.SaleItemPrice;
import com.github.chen0040.scsim.messages.sales.SaleItemPriceQuery;
import com.github.chen0040.scsim.services.SaleItemPriceBookService;

/**
 * Created by xschen on 29/11/15.
 */
public class SaleItemPriceBookActor extends AbstractActor {
    public SaleItemPriceBookActor(){
        receive(ReceiveBuilder.
                match(SaleItemPriceQuery.class, message->handleSaleItemPriceQuery(message))
                .build());
    }

    private void handleSaleItemPriceQuery(SaleItemPriceQuery query){
        ItemInfo itemInfo = query.getItemInfo();
        double itemPrice = SaleItemPriceBookService.getItemPrice(itemInfo);

        ActorRef requester = sender();
        requester.tell(new SaleItemPrice(itemInfo, itemPrice), this.self());

    }
}
