package org.prgrms.vouchermanager.dto;

import lombok.RequiredArgsConstructor;
import org.prgrms.vouchermanager.domain.voucher.Voucher;

@RequiredArgsConstructor
public class VoucherRequest {
    private final Voucher voucher;
}
