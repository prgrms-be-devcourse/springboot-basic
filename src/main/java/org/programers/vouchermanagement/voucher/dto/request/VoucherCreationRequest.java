package org.programers.vouchermanagement.voucher.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.programers.vouchermanagement.voucher.domain.VoucherType;

@AllArgsConstructor
@Getter
public class VoucherCreationRequest {

    @NotNull
    private VoucherType type;

    @NotNull
    private int value;
}
