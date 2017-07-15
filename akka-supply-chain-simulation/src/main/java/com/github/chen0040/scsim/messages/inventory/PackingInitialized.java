package com.github.chen0040.scsim.messages.inventory;

/**
 * Created by xschen on 29/11/15.
 */
public class PackingInitialized {
    private Packing packing;
    public PackingInitialized(Packing packing){
        this.packing = packing;
    }

    public Packing getPacking(){
        return packing;
    }
}
