package com.github.chen0040.scsim.services;

import com.github.chen0040.scsim.messages.Marking;
import com.github.chen0040.scsim.messages.sales.SaleOrder;

/**
 * Created by xschen on 29/11/15.
 */
public class VesselScheduleService {
    public static Marking getMarking4SaleOrder(SaleOrder so){
        Marking marking = new Marking();
        marking.setDescription("Marking for SaleOrder "+so.getId());
        return marking;
    }
}
