package org.prgrms.springorder.domain.voucher.api.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VoucherResponses {

    private final List<VoucherResponse> voucherResponses;

}
