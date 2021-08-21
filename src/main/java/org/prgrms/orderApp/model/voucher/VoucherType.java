package org.prgrms.orderApp.model.voucher;


public enum VoucherType {
    FIXEDAMOUNT(-1L),
    PERCENTDISCOUNT(100L) ;

    private Long limitAmount;


    VoucherType(Long limitAmount) {
        this.limitAmount = limitAmount;
    }

    public Long getLimitAmount(){
        return this.limitAmount;
    }

}
