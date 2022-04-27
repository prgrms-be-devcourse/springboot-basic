package org.prgms.domain;

import java.util.UUID;

import static com.google.common.base.Preconditions.checkArgument;

public record Wallet(

        /* 지갑 아이디*/
        UUID walletId,

        /* 고객 아이디 */
        UUID customerId,

        /* 바우처 아이디 */
        UUID voucherId
) {
    public Wallet {
        checkArgument(walletId != null, "지갑 아이디는 null 일 수 없습니다.");
        checkArgument(customerId != null, "고객 아이디는 null 일 수 없습니다.");
        checkArgument(voucherId != null, "바우처 아이디는 null 일 수 없습니다.");
    }

}
