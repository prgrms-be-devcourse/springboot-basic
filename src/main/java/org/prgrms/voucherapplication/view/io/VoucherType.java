package org.prgrms.voucherapplication.view.io;

import org.prgrms.voucherapplication.entity.FixedAmountVoucher;
import org.prgrms.voucherapplication.entity.PercentDiscountVoucher;
import org.prgrms.voucherapplication.entity.Voucher;
import org.prgrms.voucherapplication.exception.InvalidVoucherTypeException;

import java.util.Arrays;
import java.util.UUID;
import java.util.function.BiFunction;

/**
 * 바우처 옵션을 정의한 enum class
 */
public enum VoucherType {
    FIXED_AMOUNT("1", (voucherId, discountValue) -> new FixedAmountVoucher(voucherId, discountValue)),
    PERCENT_DISCOUNT("2", (voucherId, discountValue) -> new PercentDiscountVoucher(voucherId, discountValue));

    private final String input;
    private final BiFunction<UUID, Long, Voucher> biFunction;

    VoucherType(String input, BiFunction<UUID, Long, Voucher> biFunction) {
        this.input = input;
        this.biFunction = biFunction;
    }

    /**
     * input이 "1"이면 FIXED_AMOUNT 바우처 타입
     * input이 "2"면 PERCENT_DISCOUNT 바우처 타입
     * input이 그 이외의 문자이면 null
     *
     * @param input: 사용자의 입력
     * @return 선택된 바우처 타입
     */
    public static VoucherType getVoucherType(String input) throws InvalidVoucherTypeException {
        return Arrays.stream(VoucherType.values())
                .filter(type -> type.input.equals(input))
                .findAny()
                .orElseThrow(InvalidVoucherTypeException::new);
    }

    /**
     * voucher type에 따라서 Voucher Instance 생성
     * @param voucherId
     * @param discountValue
     * @return Voucher Instance
     */
    public Voucher getVoucherInstance(UUID voucherId, long discountValue) {
        return this.biFunction.apply(voucherId, discountValue);
    }
}
