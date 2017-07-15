package com.github.chen0040.scsim;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.github.chen0040.scsim.actors.InventoryActor;
import com.github.chen0040.scsim.actors.PurchaseActor;
import com.github.chen0040.scsim.actors.SaleActor;
import com.github.chen0040.scsim.actors.VesselScheduleActor;
import com.github.chen0040.scsim.messages.ItemLine;
import com.github.chen0040.scsim.messages.inventory.ItemInfo;
import com.github.chen0040.scsim.messages.sales.SaleQuotationQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * Created by xschen on 29/11/15.
 */
public class SimulationApp {
    private static final Logger logger = LoggerFactory.getLogger(SimulationApp.class);

    public static void main(String[] args) {
        ActorSystem scSystem = ActorSystem.create("SC-DN");
        logger.info("SC Actor System created!");

        ActorRef saleActorRef = scSystem.actorOf(Props.create(SaleActor.class), "Sale");
        scSystem.actorOf(Props.create(VesselScheduleActor.class), "VesselSchedule");
        scSystem.actorOf(Props.create(InventoryActor.class), "Inventory");
        scSystem.actorOf(Props.create(PurchaseActor.class), "Purchase");

        List<ItemInfo> productCatelogue = new ArrayList<>();
        for(int i=0; i < 10; ++i){
            ItemInfo itemInfo = new ItemInfo();
            itemInfo.setId(UUID.randomUUID().toString());
            itemInfo.setName("Product-"+i);
            productCatelogue.add(itemInfo);

        }

        Random random = new Random();
        for(int i=0; i < 4; ++i) {
            SaleQuotationQuery po = new SaleQuotationQuery();
            po.setId(UUID.randomUUID().toString());
            po.setCustomerId("MyCustomerId");

            int k = random.nextInt(10);
            for(int j=0; j < 6; ++j){
                ItemLine line = new ItemLine();
                line.setUnitCount(random.nextInt(50));

                line.setItemInfo(productCatelogue.get((k + j) % productCatelogue.size()));

                po.getLines().add(line);
            }

            saleActorRef.tell(po, saleActorRef);
        }

        logger.info("Press ENTER to exit");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }


        logger.info("SC Actor System is shutting down...");
        scSystem.shutdown();
        scSystem.awaitTermination();
        logger.info("SC Actor System shutdown!");
    }
}
