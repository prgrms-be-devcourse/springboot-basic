package org.prgrms.vouchermanager.domain.voucher;



import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import java.util.UUID;

@RequiredArgsConstructor
@ToString
public class FixedAmountVoucher implements Voucher{
    private final UUID voucherId;
    private final int amount;
    private final MenuType menuType;

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - amount;
    }

    @Override
    public MenuType getType() {
        return menuType;
    }

    @Override
    public int getAmount() {
        return amount;
    }


}
