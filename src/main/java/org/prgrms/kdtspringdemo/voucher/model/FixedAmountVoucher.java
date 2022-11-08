package org.prgrms.kdtspringdemo.voucher.model;

import org.prgrms.kdtspringdemo.io.file.CsvDto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
    private final  UUID voucherId;
    private  final long amount;
    public FixedAmountVoucher(UUID voucherId, long amount) throws IllegalArgumentException{
        if(!voucherAllow(amount)) throw new IllegalArgumentException("허용되지 않는 숫자입니다.");
        this.voucherId = voucherId;
        this.amount = amount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    public long discount(long beforeDiscount){
        return beforeDiscount-amount;
    }

    @Override
    public VoucherType getVoucherType() {
        return VoucherType.FIXED;
    }

    @Override
    public String discountValue() {

        return String.valueOf(amount);
    }

    public CsvDto makeCsvDtoFromVoucher() {
        List<String[]> lines = new ArrayList<>();
        String[] line = {this.voucherId.toString(),String.valueOf(this.amount),this.getVoucherType().name()};
        lines.add(line);
        return CsvDto.from(lines);
    }
    private boolean voucherAllow(Long amount){
        if(amount>=0){
            return true;
        }
        return false;
    }
}
