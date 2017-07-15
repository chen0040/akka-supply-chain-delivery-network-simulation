package com.github.chen0040.scsim.services;

import com.github.chen0040.scsim.messages.ItemLine;
import com.github.chen0040.scsim.messages.purchases.PurchaseOrder;
import com.github.chen0040.scsim.messages.suppliers.SupplierInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by xschen on 30/11/15.
 */
public class PurchaseOrderService {
    public static List<PurchaseOrder> createPurchaseOrders(String saleOrderId, List<ItemLine> shoppingList){
        SupplierInfo fakeSupplier = new SupplierInfo();
        fakeSupplier.setId(UUID.randomUUID().toString());
        fakeSupplier.setName("SC-Supplier");

        List<PurchaseOrder> poList = new ArrayList<>();
        for(int i=0; i < shoppingList.size(); ++i){
            ItemLine line = shoppingList.get(i);
            PurchaseOrder po = new PurchaseOrder();
            po.setSaleOrderId(saleOrderId);
            po.setSupplierInfo(fakeSupplier);
            po.setId(UUID.randomUUID().toString());

            List<ItemLine> purchaseItemLines = new ArrayList<>();
            purchaseItemLines.add((ItemLine)line.clone());
            po.setLines(purchaseItemLines);

            poList.add(po);
        }
        return poList;
    }
}
