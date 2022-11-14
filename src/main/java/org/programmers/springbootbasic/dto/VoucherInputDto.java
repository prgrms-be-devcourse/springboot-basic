package org.programmers.springbootbasic.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.programmers.springbootbasic.data.VoucherType;
import org.programmers.springbootbasic.exception.WrongRangeInputException;
import org.programmers.springbootbasic.exception.WrongTypeInputException;

@AllArgsConstructor
@Getter
public class VoucherInputDto {
    private String type;
    private long amount;
    private static final int MAX_FIXED_AMOUNT = Integer.MAX_VALUE;
    private static final int MAX_PERCENT_AMOUNT = 100;

    public void validateVoucher() throws WrongTypeInputException, WrongRangeInputException {
        if (isWrongTypeInput()) throw new WrongTypeInputException();
        if (isWrongRangeInput()) throw new WrongRangeInputException();
    }

    private boolean isWrongTypeInput() {
        return VoucherType.WRONG_INPUT.equals(VoucherType.valueOfType(type));
    }

    private boolean isWrongRangeInput() {
        VoucherType voucherType = VoucherType.valueOfType(type);
        return switch (voucherType) {
            case FIXED -> {
                if (amount > MAX_FIXED_AMOUNT) yield true;
                else yield false;
            }
            case PERCENT -> {
                if (amount > MAX_PERCENT_AMOUNT) yield true;
                else yield false;
            }
            default -> false;
        };
    }
}
