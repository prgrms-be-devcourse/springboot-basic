//package com.prgrms.voucher_manager;
//
//import com.prgrms.voucher_manager.voucher.service.VoucherService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
//@Component
//public class VoucherManager {
//    private static final Logger logger = LoggerFactory.getLogger(VoucherManager.class);
//    private final VoucherService voucherService;
//
//    public VoucherManager(VoucherService voucherService) {
//        this.voucherService = voucherService;
//    }
//
//    void createVoucher(String voucherType,long value) {
//        if (voucherType.equals("1")) {
//            voucherService.createFixedAmountVoucher(value);
//        } else if (voucherType.equals("2")) {
//            voucherService.createPercentDiscountVoucher(value);
//        }
//    }
//
//    void checkType(String voucherType){
//        if(!voucherType.equals("1") && !voucherType.equals("2")) {
//            logger.error("VoucherType check fail! - wrong input {}",voucherType);
//            throw new IllegalArgumentException();
//        }
//    }
//
//    public void getFindAllVoucher() {
//        voucherService.getFindAllVoucher();
//    }
//}

