package org.prgrms.kdt.voucher.service;

import org.prgrms.kdt.voucher.dto.VoucherRequestDto;
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

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher saveVoucher(VoucherRequestDto requestDto) {
        if (requestDto.getVoucherType() == VoucherType.FIXED) {
            return voucherRepository.save(new FixedAmountVoucher(UUID.randomUUID(), requestDto.getDiscount()));
        }
        else {
            return voucherRepository.save(new PercentDiscountVoucher(UUID.randomUUID(), requestDto.getDiscount()));
        }
    }

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> new RuntimeException("Can not find a voucher for " + voucherId));
    }
    public List<Voucher> getAllVouchers() {
        return voucherRepository.findAll();
    }

}
