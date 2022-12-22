package org.prgrms.java.domain.voucher;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public abstract class Voucher {
    protected final UUID voucherId;
    protected UUID ownerId;
    protected final long amount;
    protected final VoucherType type;
    protected boolean isUsed;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    protected final LocalDateTime createdAt;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    protected LocalDateTime expiredAt;

    public abstract long discount(long beforeDiscount);

    @Override
    public String toString() {
        return String.format("%s, %s, %d, %s, %s, %s, %s", voucherId, ownerId, amount, type, createdAt, expiredAt, isUsed);
    }

}
