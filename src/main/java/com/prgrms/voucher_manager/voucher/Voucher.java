package com.prgrms.voucher_manager.voucher;

import com.prgrms.voucher_manager.customer.controller.CustomerDto;
import com.prgrms.voucher_manager.voucher.controller.VoucherDto;

import java.util.Map;
import java.util.UUID;

public interface Voucher {

    long discount(long beforeDiscount);

    UUID getVoucherId();

    void validateValue(Long value);

    boolean validateType(String type);

    void changeValue(Long value);

    VoucherDto toVoucherDto();

    Map<String, Object> toMap();
}
