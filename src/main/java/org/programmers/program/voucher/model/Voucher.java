package org.programmers.voucherProgram.voucher.Model;

import java.util.UUID;

public abstract class Voucher {
    protected final UUID Id;
    protected final Long discountAmount;

    protected Voucher(UUID id, Long discountAmount) {
        this.Id = id;
        this.discountAmount = discountAmount;
    }

    
}
