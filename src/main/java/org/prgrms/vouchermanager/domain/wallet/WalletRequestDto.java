package org.prgrms.vouchermanager.domain.wallet;

import lombok.Builder;
import lombok.Data;
import org.prgrms.vouchermanager.domain.voucher.Voucher;

@Data
@Builder
public class WalletRequestDto {
    private final String customerEmail;
    private final Voucher voucher;
}
