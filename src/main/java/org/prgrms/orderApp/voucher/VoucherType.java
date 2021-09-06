package org.prgrms.orderApp.voucher;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

public enum VoucherType {
    FIXED(1,  100000000, voucher -> new FixedAmountVoucher(voucher.getVouchcerId(), voucher.getAmount()), FixedAmountVoucher.class.getName()),
    PERCENT(2,  100, voucher -> new PercentDiscountVoucher(voucher.getVouchcerId(), voucher.getAmount()), PercentDiscountVoucher.class.getName());


    private final int menuNumber;
    private final long limit;
    private final Function<VoucherVo, Voucher> voucher;
    private final String voucherClassName ;

    public Voucher getVoucher(UUID voucherId, long amonut){
        return voucher.apply(new VoucherVo(voucherId, amonut));
    }

    VoucherType(int menuNumber, long limit, Function<VoucherVo, Voucher> voucher, String voucherClassName) {
        this.menuNumber = menuNumber;
        this.limit = limit;
        this.voucher = voucher;
        this.voucherClassName = voucherClassName;

    }


    public boolean invalidAmount(long amountChecked){
        return 0L < amountChecked && amountChecked < limit;
    }

    public static String getMenuNameByMenuNumber(int menuNumber) {
        var getVoucherType = Arrays.stream(VoucherType.values())
                .filter(voucherType -> voucherType.menuNumber == menuNumber)
                .findAny();
        return getVoucherType.map(Enum::name).orElse("");

    }
    public static Optional<VoucherType> getVoucherTypeByVoucherClassName(String className){
        return Arrays.stream(VoucherType.values())
                .filter(voucherType -> voucherType.voucherClassName.equals(className))
                .findAny();
    }

}
