package org.prgrms.kdtspringorder;

import java.util.UUID;
import java.util.function.Supplier;

//public Voucher createVoucher(VoucherType voucherType) {
//        UUID voucherId = UUID.randomUUID();
//        if (voucherType.equals(VoucherType.FixedAmountVoucher)) {
//        return new FixedAmountVoucher(voucherId, 100);
//        } else  {
//        return new PercentDiscountVoucher(voucherId, 10);
//        }
//        }
public enum VoucherType {
    FIXED("fixed",()->{ UUID voucherId = UUID.randomUUID();
        return new FixedAmountVoucher(voucherId, 100);})
    ,
    PERCENT("percent", ()->{UUID voucherId = UUID.randomUUID();
        return new PercentDiscountVoucher(voucherId, 10);});


    private String value;
    public String value(){
        return value;
    }
    private Supplier<Voucher> supplier;

    VoucherType(String value, Supplier<Voucher> supplier){
        this.value = value;
        this.supplier = supplier;
    }

//    public static Command from(String value){
//        return Command.valueOf(value.toUpperCase());
//    }
    public static VoucherType from(String value) {
        return VoucherType.valueOf(value.toUpperCase());
    }

    public Voucher accept() {
        Voucher voucher = this.supplier.get();
        return voucher;
    }
}
