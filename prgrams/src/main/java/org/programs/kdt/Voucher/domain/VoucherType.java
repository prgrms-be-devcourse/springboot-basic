package org.programs.kdt.Voucher.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.programs.kdt.Utils.TriFunction;
import org.programs.kdt.Utils.EnumInterface;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Getter
public enum VoucherType implements EnumInterface {
    PERCENT("percent", (uuid, value, localDateTime ) -> new PercentDiscountVoucher(uuid, value, localDateTime)),
    FIXEDAMOUNT("fixedAmount", (uuid, value, localDateTime) -> new FixedAmountVoucher(uuid, value, localDateTime));
    private String type;
    private final TriFunction<UUID, Long, LocalDateTime,Voucher> constuructor;
    public static VoucherType findVoucherType(String type) {
        return EnumInterface.find(type, values());
    }

    public Voucher createVoucher(UUID uuid, Long inputVoucherValue, LocalDateTime localDateTime) {
        return getConstuructor().apply(uuid, inputVoucherValue, localDateTime);
    }
}
