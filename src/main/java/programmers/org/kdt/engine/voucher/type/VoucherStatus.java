package programmers.org.kdt.engine.voucher.type;

import java.util.Locale;

public enum VoucherStatus {
    NULL{
        @Override
        public String toValueType(){
            return VoucherDiscountType.NULL.toString();
        }
    },
    FIXEDAMOUNTVOUCHER{
        @Override
        public String toValueType(){
            return VoucherDiscountType.DOLLAR.toString();
        }
    },
    PERCENTDISCOUNTVOUCHER {
        @Override
        public String toValueType(){
            return VoucherDiscountType.PERCENT.toString();
        }
    };

    public static VoucherStatus fromString(String string) {
        VoucherStatus voucherStatus = NULL;
        try {
            voucherStatus = VoucherStatus.valueOf(string.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        return voucherStatus;
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
            case FIXEDAMOUNTVOUCHER -> ret = VoucherDiscountType.DOLLAR.toString();
            case PERCENTDISCOUNTVOUCHER -> ret = VoucherDiscountType.PERCENT.toString();
            default -> ret = VoucherDiscountType.NULL.toString();
        }
        return ret;
    }

    public abstract String toValueType();

}

