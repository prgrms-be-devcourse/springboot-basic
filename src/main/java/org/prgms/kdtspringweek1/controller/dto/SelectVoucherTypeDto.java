package org.prgms.kdtspringweek1.controller.dto;

import org.prgms.kdtspringweek1.exception.InputExceptionCode;
import org.prgms.kdtspringweek1.voucher.entity.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

// 기존에 VoucherType에 num 속성을 두어, num에 해당되는 VoucherType을 반환하도록 하였었으나,
// num은 뷰에 종속적인 값으로, 요구사항 변경 시 도메인에도 영향을 미쳐 의존되고 있어
// 따로 dto를 만들어 선택할 수 있도록 변경
public enum SelectVoucherTypeDto {
    FIXED_AMOUNT(1, VoucherType.FIXED_AMOUNT),
    PERCENT_DISCOUNT(2, VoucherType.PERCENT_DISCOUNT);

    private long num;
    private VoucherType voucherType;
    private final static Logger logger = LoggerFactory.getLogger(SelectVoucherTypeDto.class);

    SelectVoucherTypeDto(long num, VoucherType voucherType) {
        this.num = num;
        this.voucherType = voucherType;
    }

    public static VoucherType getVoucherTypeByNum(long num) {
        return Arrays.stream(SelectVoucherTypeDto.values())
                .filter(voucherType -> voucherType.num == num)
                .findFirst()
                .orElseThrow(() -> {
                    logger.debug("Invalid Voucher Type -> {}", num);
                    return new IllegalArgumentException(InputExceptionCode.INVALID_VOUCHER_TYPE.getMessage());
                }).voucherType;
    }
}