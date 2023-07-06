package org.programers.vouchermanagement.voucher.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.programers.vouchermanagement.voucher.domain.VoucherType;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class VoucherCreationRequest {

    private VoucherType type;
    private int value;
}
