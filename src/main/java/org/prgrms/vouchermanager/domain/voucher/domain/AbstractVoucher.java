package org.prgrms.vouchermanager.domain.voucher.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

@EqualsAndHashCode
@Getter
public abstract class AbstractVoucher implements Voucher {
    
    /* 아이디 */
    private final UUID id;
    
    /* 바우처 타입 */
    private final VoucherType type;

    public AbstractVoucher(UUID id, VoucherType type) {
        this.id = id;
        this.type = type;
    }
}
