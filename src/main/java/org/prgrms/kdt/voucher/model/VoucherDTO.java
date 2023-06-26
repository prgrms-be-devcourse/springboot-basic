package org.prgrms.kdt.voucher.model;

public class VoucherDTO {
    private final VoucherType voucherType;
    private final String voucherDTOName;
    private final long benefit;

    public VoucherDTO(VoucherType voucherType, String voucherDTOName, long benefit) {
        this.voucherType = voucherType;
        this.voucherDTOName = voucherDTOName;
        this.benefit = benefit;
    }

    @Override
    public String toString() {
        return voucherType.getVoucherTypeName() + "\t\t" +
                voucherDTOName + "\t\t" +
                benefit;
    }
}
