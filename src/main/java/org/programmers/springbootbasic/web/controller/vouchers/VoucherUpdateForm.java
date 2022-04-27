package org.programmers.springbootbasic.web.controller.vouchers;

import lombok.Data;
import org.programmers.springbootbasic.voucher.domain.VoucherType;

import javax.validation.constraints.NotNull;

@Data
public class VoucherUpdateForm {

    @NotNull
    private VoucherType type;

    @NotNull
    private Integer amount;
}
