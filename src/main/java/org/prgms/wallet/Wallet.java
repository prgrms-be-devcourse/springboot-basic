package org.prgms.wallet;

import com.google.common.base.Preconditions;

import java.util.UUID;

public record Wallet(

        /* 지갑 아이디*/
        UUID walletId,

        /* 고객 아이디 */
        UUID customerId,

        /* 바우처 아이디 */
        UUID voucherId
) {

    public Wallet {
        Preconditions.checkArgument(walletId != null, "지갑 아이디는 null 일 수 없습니다.");
        Preconditions.checkArgument(customerId != null, "고객 아이디는 null 일 수 없습니다.");
        Preconditions.checkArgument(voucherId != null, "바우처 아이디는 null 일 수 없습니다.");
    }
}
