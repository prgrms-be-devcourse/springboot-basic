package prgms.spring_week1.domain.voucher.model.type;

import prgms.spring_week1.exception.NoSuchVoucherTypeException;

import java.util.stream.Stream;

public enum VoucherType {
    FIXED,
    PERCENT;

    public static VoucherType findVoucherType(String inputSelectText) {
        VoucherType matchedVoucherType = Stream.of(VoucherType.values())
                .filter(v -> v.name().equalsIgnoreCase(inputSelectText))
                .findFirst()
                .orElseThrow(() -> new NoSuchVoucherTypeException("해당 바우처 타입이 존재하지 않습니다."));

        return matchedVoucherType;
    }

    public static Long validateAmountInputValue(long inputValue) {
        if (inputValue > 0) {
            return inputValue;
        }

        return null;
    }

    public static Long validatePercentInputValue(long inputValue) {
        if (inputValue > 0 && inputValue <= 100) {
            return inputValue;
        }

        return null;
    }
}
