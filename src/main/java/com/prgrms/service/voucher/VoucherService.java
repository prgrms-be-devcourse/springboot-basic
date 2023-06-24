package com.prgrms.service.voucher;

import com.prgrms.model.voucher.FixedAmountVoucher;
import com.prgrms.model.voucher.PercentDiscountVoucher;
import com.prgrms.model.voucher.Voucher;
import com.prgrms.model.voucher.VoucherPolicy;
import com.prgrms.repository.voucher.VoucherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository.findById(voucherId)
                .orElseThrow(() -> new RuntimeException(MessageFormat.format("can not find a voucher for {0}", voucherId)));
    }

    public void useVoucher(Voucher voucher) {
    }

    public Voucher createVoucher(VoucherPolicy voucherPolicy, long discount) {
        Voucher voucher = null;
        switch (voucherPolicy) {
            case FixedAmountVoucher -> voucher = new FixedAmountVoucher(UUID.randomUUID(), discount);
            case PercentDiscountVoucher -> voucher = new PercentDiscountVoucher(UUID.randomUUID(), discount);
        }
        return voucher;
    }

    public boolean isEmptyRepository(List<Voucher> voucherList) {
        return voucherList.size() == 0;
    }
}
