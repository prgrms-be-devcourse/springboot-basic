package com.prgrms.springbootbasic.service.voucher;

import com.prgrms.springbootbasic.domain.voucher.FixedDiscountVoucher;
import com.prgrms.springbootbasic.domain.voucher.PercentDiscountVoucher;
import com.prgrms.springbootbasic.domain.voucher.Voucher;
import com.prgrms.springbootbasic.enums.VoucherType;
import com.prgrms.springbootbasic.repository.voucher.VoucherRepository;
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
        try {
            Voucher voucher = switch (type) {
                case FIXED -> new FixedDiscountVoucher(discount);
                case PERCENT -> new PercentDiscountVoucher(discount);
            };
            return voucherRepository.insert(voucher);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    public Map<UUID, Voucher> fetchAllVouchers() {
        return voucherRepository.getAllVouchersList();
    }
}
