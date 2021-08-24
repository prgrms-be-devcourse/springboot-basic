package com.org.prgrms.mission.model;
import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;
import lombok.ToString;

import java.util.UUID;

@Data
@ToString
public class PercentDiscountVoucher implements Voucher {


    @CsvBindByPosition(position = 0)
    private VoucherType voucherType;
    @CsvBindByPosition(position = 1)
    private UUID voucherId;
    @CsvBindByPosition(position = 2)
    private long percent;

    public PercentDiscountVoucher(VoucherType voucherType, UUID voucherId, long percent) {
        this.voucherType = voucherType;
        this.voucherId = voucherId;
        this.percent = percent;
    }

    public PercentDiscountVoucher() {
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (percent /100);
    }

    @Override
    public UUID getVoucherID() {
        return voucherId;
    }

}
