package org.prgrms.kdt.dto;

import org.prgrms.kdt.enums.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VoucherSaveRequestDto {

    private final static Logger logger = LoggerFactory.getLogger(VoucherSaveRequestDto.class);
    private VoucherType voucherType;
    private long discount;

    public VoucherSaveRequestDto(VoucherType voucherType, long discount) {
        logger.info("Starts VoucherSaveRequestDto()");
        logger.info("voucherType : {}, discount : {}", voucherType.toString(), discount);
        this.voucherType = voucherType;
        this.discount = discount;
        logger.info("Finished VoucherSaveRequestDto()");

    }

    public VoucherType getVoucherType() {
        logger.info("Starts getVoucherType()");
        logger.info("voucherType : {}", voucherType.toString());
        return voucherType;
    }

    public long getDiscount() {
        logger.info("Starts getDiscount()");
        logger.info("discount : {}", discount);
        return discount;
    }

}