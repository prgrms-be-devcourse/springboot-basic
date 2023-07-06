package org.programers.vouchermanagement.voucher.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Range;
import org.programers.vouchermanagement.voucher.domain.VoucherType;

@AllArgsConstructor
@Getter
public class VoucherCreationRequest {

    @NotNull
    private VoucherType type;

    @Range(min = 1, max = 99)
    @NotNull
    private int value;
}
