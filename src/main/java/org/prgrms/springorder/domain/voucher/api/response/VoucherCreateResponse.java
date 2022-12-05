package org.prgrms.springorder.domain.voucher.api.response;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class VoucherCreateResponse {

    private final UUID voucherId;

}
