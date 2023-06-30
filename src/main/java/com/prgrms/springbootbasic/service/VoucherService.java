package com.prgrms.springbootbasic.service;

import com.prgrms.springbootbasic.domain.FixedDiscountVoucher;
import com.prgrms.springbootbasic.domain.PercentDiscountVoucher;
import com.prgrms.springbootbasic.domain.Voucher;
import com.prgrms.springbootbasic.enums.VoucherType;
import com.prgrms.springbootbasic.repository.VoucherRepository;
import java.util.Map;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher createVoucher(VoucherType type, long discount) {
        Voucher voucher;
        switch (type) {
            case FIXED -> voucher = new FixedDiscountVoucher(UUID.randomUUID(), discount);
            case PERCENT -> voucher = new PercentDiscountVoucher(UUID.randomUUID(), discount);
            default -> throw new IllegalStateException("입력가능한 Voucher Type은 " + type + "입니다.");
        }
        return voucherRepository.insert(voucher);
    }

    public Map<UUID, Voucher> fetchAllVouchers() {
        return voucherRepository.getAllVouchersList();
    }
}
