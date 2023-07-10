package com.prgrms.springbootbasic.dto.voucher.response;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class VoucherListResponse {

    private final List<VoucherResponse> voucherResponseList;
}
