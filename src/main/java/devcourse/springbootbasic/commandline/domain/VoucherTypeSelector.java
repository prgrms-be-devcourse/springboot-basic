package devcourse.springbootbasic.commandline.domain;

import devcourse.springbootbasic.domain.voucher.VoucherType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Optional;

@RequiredArgsConstructor
public enum VoucherTypeSelector {
    FIXED("1", "Fixed Amount", VoucherType.FIXED),
    PERCENT("2", "Percent Discount", VoucherType.PERCENT);

    private final String code;
    private final String description;
    @Getter
    private final VoucherType voucherType;

    public static Optional<VoucherTypeSelector> fromString(String code) {
        return Arrays.stream(values())
                .filter(voucherTypeSelector -> voucherTypeSelector.code.equalsIgnoreCase(code.trim()))
                .findFirst();
    }

    public String getCodeWithDescription() {
        return this.code + ". " + this.description;
    }

    @Override
    public String toString() {
        return this.getCodeWithDescription();
    }
}
