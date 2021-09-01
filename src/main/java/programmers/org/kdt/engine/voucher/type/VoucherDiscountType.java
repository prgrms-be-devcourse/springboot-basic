package programmers.org.kdt.engine.voucher.type;

public enum VoucherDiscountType {
    NULL{
        @Override
        public String toString() {return "error";}
    },
    PERCENT{
        @Override
        public String toString() {return "%";}
    },
    DOLLAR{
        @Override
        public String toString() {return "$";}
    },
    WON{
        @Override
        public String toString() {return "\\";}
    };

}
