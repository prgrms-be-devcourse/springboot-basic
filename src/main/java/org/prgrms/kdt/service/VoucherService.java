package org.prgrms.kdt.service;

import org.prgrms.kdt.model.voucher.Voucher;
import org.prgrms.kdt.model.voucher.VoucherType;
import org.prgrms.kdt.model.voucher.VoucherTypeMapping;
import org.prgrms.kdt.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class VoucherService {

    VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher createVoucher(UUID voucherId, int voucherTypeNumber, int discountAmount) {
        Voucher voucher = getVoucherTypeByNumber(voucherTypeNumber).newVoucher(voucherId, discountAmount);
        voucherRepository.insert(voucher);
        return voucher;
    }

    public Map getVoucherList() {
        return voucherRepository.getVoucherList();
    }

    private VoucherType getVoucherTypeByNumber(int voucherTypeNumber) {
        return VoucherTypeMapping.valueOf(voucherTypeNumber);
    }
}
