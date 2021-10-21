package org.prgrms.kdt.voucher.service;

import org.prgrms.kdt.voucher.FixedAmountVoucher;
import org.prgrms.kdt.voucher.PercentDiscountVoucher;
import org.prgrms.kdt.voucher.Voucher;
import org.prgrms.kdt.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(final VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public void createVoucher(final String voucherType, final long discountValue) {
        System.out.println(MessageFormat.format("{0}가 생성되었습니다.", voucherType));
        if (voucherType.equals("FixedAmountVoucher")) {
            voucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID(), discountValue));
        } else if (voucherType.equals("PercentDiscountVoucher")) {
            voucherRepository.insert(new PercentDiscountVoucher(UUID.randomUUID(), discountValue));
        } else {
            System.out.println("Voucher Type 입력값이 잘못되었습니다.");
        }
    }

    public Voucher getVoucher(final UUID voucherId) {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> new RuntimeException(MessageFormat.format("Can not find a voucher for {0}", voucherId)));
    }

    public List<Voucher> getAllVoucher() {
        return voucherRepository.findAll();
    }

    public void useVoucher(final Voucher voucher) {

    }
}
