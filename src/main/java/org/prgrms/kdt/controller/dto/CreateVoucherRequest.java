package org.prgrms.kdt.controller.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.prgrms.kdt.voucher.model.VoucherType;

public class CreateVoucherRequest {

    @Min(value = 0, message = "최솟값은 0이상이어야 합니다.")
    private Long value;
    @NotNull(message = "타입은 필수입니다.")
    private VoucherType type;

    public CreateVoucherRequest(Long value, VoucherType type) {
        this.value = value;
        this.type = type;
    }

    public Long getValue() {
        return value;
    }

    public VoucherType getType() {
        return type;
    }

}
