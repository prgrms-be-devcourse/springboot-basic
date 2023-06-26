package org.prgrms.kdt.voucher.dto;

public class VoucherDto {
    private final String voucherDtoName;
    private final long benefit;

    public VoucherDto(String voucherDtoName, long benefit) {
        this.voucherDtoName = voucherDtoName;
        this.benefit = benefit;
    }

    public String getVoucherDtoName() {
        return voucherDtoName;
    }

    public long getBenefit() {
        return benefit;
    }
}
