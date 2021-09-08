package org.prgms.order.voucher.entity;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class VoucherFactory {
    public Voucher getVoucher(VoucherIndexType voucherType, UUID voucherId, long amount, LocalDateTime createdAt, LocalDateTime expiredAt){
        if(voucherType == null){
            return null;
        }
        if(voucherType.equals(VoucherIndexType.FIXED)){
            return FixedAmountVoucher.builder().voucherId(voucherId).amount(amount).createdAt(createdAt).expiredAt(expiredAt).build();
        }else if(voucherType.equals(VoucherIndexType.PERCENT)){
            return PercentDiscountVoucher.builder().voucherId(voucherId).amount(amount).createdAt(createdAt).expiredAt(expiredAt).build();
        }

        return null;
    }
}
