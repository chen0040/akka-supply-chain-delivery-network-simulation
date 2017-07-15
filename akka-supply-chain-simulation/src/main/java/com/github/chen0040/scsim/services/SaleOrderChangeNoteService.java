package com.github.chen0040.scsim.services;

import com.github.chen0040.scsim.messages.SaleOrderChangeNote;
import com.github.chen0040.scsim.messages.sales.SaleOrder;

/**
 * Created by xschen on 29/11/15.
 */
public class SaleOrderChangeNoteService {
    public static SaleOrderChangeNote getSaleOrderChangeNote(SaleOrder saleOrder){
        SaleOrderChangeNote note = new SaleOrderChangeNote();
        note.setDescription("SO Change Note for SaleOrder "+saleOrder.getId());
        return note;
    }
}
