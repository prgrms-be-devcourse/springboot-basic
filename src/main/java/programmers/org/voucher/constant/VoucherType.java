package programmers.org.voucher.constant;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

import static programmers.org.voucher.exception.ErrorMessage.INVALID_VOUCHER_TYPE;

public enum VoucherType {
    FIXED((amount) -> amount > 0),
    PERCENT((amount) -> amount > 0 && amount <= 100);

    private final Predicate<Integer> validation;

    VoucherType(Predicate<Integer> validation) {
        this.validation = validation;
    }

    public static VoucherType find(String type) {
        return Arrays.stream(VoucherType.values())
                .filter(voucherType -> voucherType.name().equalsIgnoreCase(type))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException(INVALID_VOUCHER_TYPE.getMessage()));
    }

    public boolean isInvalidAmount(int amount) {
        return !validation.test(amount);
    }
}
