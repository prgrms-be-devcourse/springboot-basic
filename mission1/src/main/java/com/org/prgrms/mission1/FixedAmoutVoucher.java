package com.org.prgrms.mission1;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import lombok.*;

import java.util.UUID;

@Data
@ToString
public class FixedAmoutVoucher implements Voucher{


    @CsvBindByPosition(position = 0)
    private VoucherType voucherType;
    @CsvBindByPosition(position = 1)
    private UUID voucherId;
    @CsvBindByPosition(position = 2)
    private long amount;

    public FixedAmoutVoucher(VoucherType voucherType, UUID voucherId, long amount) {
        this.voucherType = voucherType;
        this.voucherId = voucherId;
        this.amount = amount;
    }


    public FixedAmoutVoucher() {
    }



    public long discount(long beforeDiscounnt) {
        return beforeDiscounnt - amount;
    }

    @Override
    public UUID getVoucherID() {
        return null;
    }


}
