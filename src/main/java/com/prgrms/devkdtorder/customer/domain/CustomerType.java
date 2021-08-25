package com.prgrms.devkdtorder.customer.domain;

public enum CustomerType {
    WHITE,
    BLACK;


    public boolean isBlack(){
        return this.equals(BLACK);
    }
}
