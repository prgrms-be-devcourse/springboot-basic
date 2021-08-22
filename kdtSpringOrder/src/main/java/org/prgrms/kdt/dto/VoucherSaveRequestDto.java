package org.prgrms.kdt.dto;

import org.prgrms.kdt.enums.VoucherType;
import org.prgrms.kdt.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VoucherSaveRequestDto {

    private final static Logger logger = LoggerFactory.getLogger(VoucherSaveRequestDto.class);
    private VoucherType voucherType;
    private long discount;

    public VoucherSaveRequestDto(VoucherType voucherType, long discount) {
        this.voucherType = voucherType;
        this.discount = discount;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public long getDiscount() {
        return discount;
    }

}