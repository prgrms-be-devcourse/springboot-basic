package org.prgrms.kdt.service;

import org.prgrms.kdt.model.Voucher;
import org.prgrms.kdt.model.VoucherTypeMapping;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class VoucherService {

    public Voucher createVoucher(UUID voucherId, int voucherType, int discountAmount) {
        Voucher voucher = VoucherTypeMapping.getVoucherType(voucherType).createVoucherByType(voucherId, discountAmount);
        return voucher;
    }
}
