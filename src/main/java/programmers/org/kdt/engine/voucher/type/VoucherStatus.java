package programmers.org.kdt.engine.voucher.type;

public enum VoucherStatus {
    NULL{
        @Override
        public String toValueType(){
            return VoucherDiscountType.NULL.toString();
        }
    },
    FixedAmountVoucher{
        @Override
        public String toValueType(){
            return VoucherDiscountType.DOLLAR.toString();
        }
    },
    PercentDiscountVoucher{
        @Override
        public String toValueType(){
            return VoucherDiscountType.PERCENT.toString();
        }
    };

    public static VoucherStatus fromString(String string) {
        if (string.equals("FixedAmountVoucher")) {
            return FixedAmountVoucher;
        } else if (string.equals("PercentDiscountVoucher")){
            return PercentDiscountVoucher;
        } else {
            return NULL;
        }
    }

    public static String getValueType(VoucherStatus inputStatus) {
        VoucherStatus voucherStatus = NULL;
        String ret;
        try {
            voucherStatus = VoucherStatus.valueOf(inputStatus.name());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        switch (voucherStatus) {
            case FixedAmountVoucher -> ret = VoucherDiscountType.DOLLAR.toString();
            case PercentDiscountVoucher -> ret = VoucherDiscountType.PERCENT.toString();
            default -> ret = VoucherDiscountType.NULL.toString();
        }
        return ret;
    }

    public abstract String toValueType();

}

