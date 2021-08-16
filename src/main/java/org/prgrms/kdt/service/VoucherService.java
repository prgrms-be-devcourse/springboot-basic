package org.prgrms.kdt.service;

import org.prgrms.kdt.domain.voucher.FixedAmountVoucher;
import org.prgrms.kdt.domain.voucher.PercentDiscountVoucher;
import org.prgrms.kdt.domain.voucher.Voucher;
import org.prgrms.kdt.dto.VoucherRequestDto;
import org.prgrms.kdt.repository.VoucherRepository;

import java.util.List;
import java.util.UUID;

public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    // 사용자가 선택한 숫자에따라 바우처를 저장하는 코드
    public Voucher saveVoucher(VoucherRequestDto requestDto) {
        if (requestDto.getVoucherType() == 1) {
            return voucherRepository.save(new FixedAmountVoucher(UUID.randomUUID(), requestDto.getDiscount()));
        }else {
            return voucherRepository.save(new PercentDiscountVoucher(UUID.randomUUID(), requestDto.getDiscount()));
        }
    }

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> new RuntimeException("Can not find a voucher for " + voucherId));
    }

    public List<Voucher> getAllVouchers() {
        return voucherRepository
                .findAll();
    }

    public void userVoucher(Voucher voucher) {

    }
}
