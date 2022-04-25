package org.programmers.springbootbasic.controller.vouchers;

import lombok.Data;
import org.programmers.springbootbasic.voucher.domain.VoucherType;

@Data
class VoucherCreateForm {
    private VoucherType type;
    private Integer amount;
}