package org.prgrms.assignment.voucher.dto;

import org.prgrms.assignment.voucher.model.VoucherType;

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
        return String.format("%-25s %-25s %-25s", voucherType.getVoucherTypeName(),
                voucherDTOName,
                benefit);
    }
}
