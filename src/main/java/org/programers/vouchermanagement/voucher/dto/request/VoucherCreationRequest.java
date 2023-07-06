package org.programers.vouchermanagement.voucher.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.programers.vouchermanagement.voucher.domain.VoucherType;

@AllArgsConstructor
@Getter
public class VoucherCreationRequest {

    private VoucherType type;
    private int value;
}
