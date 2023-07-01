package prgms.spring_week1.domain.voucher.model.type;

import prgms.spring_week1.exception.NoSuchVoucherTypeException;

import java.util.Optional;
import java.util.stream.Stream;

public enum VoucherType {
    FIXED("FixedAmountVoucher"),
    PERCENT("PercentDiscountVoucher");

    private final String voucherType;

    VoucherType(String voucherType) {
        this.voucherType = voucherType;
    }

    public String getVoucherType() {
        return voucherType;
    }

    public static VoucherType matchVoucherType(String inputSelectText) throws NoSuchVoucherTypeException {
        VoucherType selectedVoucherType = VoucherType.makeVoucherTypeStream(inputSelectText);
        return selectedVoucherType;
    }

    public static VoucherType makeVoucherTypeStream(String inputSelectText) {
        Optional<VoucherType> matchedVoucherType = getMatchVoucherTypeFilter(Stream.of(VoucherType.values()), inputSelectText);
        if (matchedVoucherType.isEmpty()) {
            throw new NoSuchVoucherTypeException();
        }

        return matchedVoucherType.get();
    }

    private static Optional<VoucherType> getMatchVoucherTypeFilter(Stream<VoucherType> voucherType, String inputSelectText) {
        return voucherType.filter(v -> v.getVoucherType().equalsIgnoreCase(inputSelectText))
                .findFirst();
    }

}
