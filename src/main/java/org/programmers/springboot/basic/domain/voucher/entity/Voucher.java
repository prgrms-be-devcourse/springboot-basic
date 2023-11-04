package org.programmers.springboot.basic.domain.voucher.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.programmers.springboot.basic.domain.voucher.service.validate.VoucherValidationStrategy;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class Voucher {

    private final UUID voucherId;
    private final VoucherType voucherType;
    private final Long discount;
    private final LocalDateTime createdAt;
    private VoucherValidationStrategy validationStrategy;

    public void setValidationStrategy(VoucherValidationStrategy validationStrategy) {
        this.validationStrategy = validationStrategy;
    }

    public int getVoucherTypeValue() {
        return this.voucherType.getValue();
    }

    public void validate() {
        validationStrategy.validate(this.discount);
    }
}
