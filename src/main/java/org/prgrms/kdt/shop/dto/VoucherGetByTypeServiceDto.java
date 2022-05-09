package org.prgrms.kdt.shop.dto;

import org.prgrms.kdt.shop.enums.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VoucherGetByTypeServiceDto {

    private final VoucherType type;
    private static final Logger logger = LoggerFactory.getLogger(VoucherGetByTypeServiceDto.class);

    public VoucherGetByTypeServiceDto(String type) {
        try {
            this.type = VoucherType.find(type);
        } catch (IllegalArgumentException e) {
            logger.error("invalid voucher type.", e);
            throw new IllegalArgumentException("invalid voucher type.");
        }
    }

    public VoucherType getType() {
        return type;
    }
}
