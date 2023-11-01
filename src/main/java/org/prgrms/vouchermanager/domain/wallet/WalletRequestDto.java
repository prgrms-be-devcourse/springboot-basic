package org.prgrms.vouchermanager.domain.wallet;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.prgrms.vouchermanager.domain.voucher.Voucher;

@Data
@Builder
public class WalletRequestDto {
    private final String customerEmail;
    private final Voucher voucher;

}
