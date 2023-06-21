package org.prgrms.kdt.voucher.service;

import org.prgrms.kdt.voucher.domain.FixedAmountVoucher;
import org.prgrms.kdt.voucher.domain.PercentDiscountVoucher;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.voucher.domain.VoucherType;
import org.prgrms.kdt.voucher.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {

    @Qualifier("file")
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> new RuntimeException("can not find a voucher for" + voucherId));
    }

    public void useVoucher(Voucher voucher) {

    }

    public List<Voucher> getVouchers() {
        List<Voucher> all = voucherRepository.findAll();
        return all;
    }

    public Voucher save(VoucherType voucherType, Long discount) {
        if(voucherType.equals(VoucherType.Fixed_AMOUNT_VOUCHER)){
            Voucher createdVoucher = voucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID(), discount));
            return createdVoucher;
        }
        else if (voucherType.equals(VoucherType.PERCENT_DISCOUNT_VOUCHER)) {
            Voucher createdVoucher = voucherRepository.insert(new PercentDiscountVoucher(UUID.randomUUID(), discount));
            return createdVoucher;
        }else{
            return null;
        }
    }
}
