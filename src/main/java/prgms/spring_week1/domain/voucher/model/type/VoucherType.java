package prgms.spring_week1.domain.voucher.model.type;

import prgms.spring_week1.exception.NoSuchVoucherTypeException;

import java.util.Optional;
import java.util.stream.Stream;

public enum VoucherType {
    FIXED,
    PERCENT;

    public static VoucherType makeVoucherType(String inputSelectText) {
        VoucherType matchedVoucherType = getMatchVoucherTypeFilter(Stream.of(VoucherType.values()), inputSelectText)
                .orElseThrow(() -> new NoSuchVoucherTypeException("해당 바우처 타입이 존재하지 않습니다."));

        return matchedVoucherType;
    }

    private static Optional<VoucherType> getMatchVoucherTypeFilter(Stream<VoucherType> voucherType, String inputSelectText) {
        return voucherType.filter(v -> v.name().equalsIgnoreCase(inputSelectText))
                .findFirst();
    }

}
