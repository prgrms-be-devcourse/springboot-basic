//package com.programmers.VoucherManagementApplication.vo;
//
//import com.programmers.VoucherManagementApplication.voucher.VoucherType;
//
//public class CreateVoucherRequest {
//
//    private final VoucherType voucherType;
//    private final OriginPrice originPrice;
//    private final Amount amount;
//
//    public CreateVoucherRequest(String voucherType, long originPrice, String amount) {
//        this.voucherType = VoucherType.of(voucherType);
//        this.originPrice = new OriginPrice(originPrice);
//        this.amount = new Amount(originPrice, amount);
//    }
//
//    public VoucherType getVoucherType() {
//        return voucherType;
//    }
//
//    public OriginPrice getOriginPrice() {
//        return originPrice;
//    }
//
//    public Amount getAmount() {
//        return amount;
//    }
//}
