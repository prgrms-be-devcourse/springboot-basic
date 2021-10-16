package org.prgrms.kdt.dto;

import org.prgrms.kdt.enums.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class VoucherSaveRequestDto {

    private final static Logger logger = LoggerFactory.getLogger(VoucherSaveRequestDto.class);
    private UUID customerId;
    private VoucherType voucherType;
    private long discount;

    public VoucherSaveRequestDto(UUID customerId, VoucherType voucherType, long discount) {
        logger.info("Starts VoucherSaveRequestDto()");
        logger.info("voucherType : {}, discount : {}", voucherType.toString(), discount);
        this.customerId = customerId;
        this.voucherType = voucherType;
        this.discount = discount;
        logger.info("Finished VoucherSaveRequestDto()");

    }

    public UUID getCustomerId() {
        logger.info("Starts getVoucherType()");
        logger.info("customerId : {}", customerId.toString());
        return customerId;
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