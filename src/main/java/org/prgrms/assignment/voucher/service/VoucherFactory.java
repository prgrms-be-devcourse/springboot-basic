package org.prgrms.assignment.voucher.service;

import org.prgrms.assignment.voucher.dto.VoucherServiceRequestDTO;
import org.prgrms.assignment.voucher.model.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class VoucherFactory {

    public Voucher createVoucher(VoucherServiceRequestDTO voucherServiceRequestDTO) {
        VoucherType voucherType = voucherServiceRequestDTO.voucherType();
        switch (voucherType) {
            case FIXED -> {
                return new FixedAmountVoucher(
                    voucherServiceRequestDTO.voucherId(),
                    voucherServiceRequestDTO.benefit(),
                    voucherServiceRequestDTO.createdAt(),
                    voucherServiceRequestDTO.expireDate()
                );
            }
            case PERCENT -> {
                return new PercentDiscountVoucher(
                    voucherServiceRequestDTO.voucherId(),
                    voucherServiceRequestDTO.benefit(),
                    voucherServiceRequestDTO.createdAt(),
                    voucherServiceRequestDTO.expireDate()
                );
            }
        }
        throw new RuntimeException("Failed to create Voucher!");
    }

}
