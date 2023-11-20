package org.prgrms.vouchermanager.domain.wallet;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;


public record ApiWalletRequest(String customerEmail, UUID voucherId) {
}
