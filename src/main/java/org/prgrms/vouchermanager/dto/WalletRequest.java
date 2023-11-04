package org.prgrms.vouchermanager.dto;

import lombok.Builder;
import lombok.Data;
import org.prgrms.vouchermanager.domain.voucher.Voucher;

@Data
@Builder
public record WalletRequest(String customerEmail, Voucher voucher) {}
