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


    // 생성자를 통한 자동 의존성 주입
    public VoucherService(@Qualifier("file") VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    // 사용자가 선택한 숫자에따라 바우처를 저장하는 코드
    public Voucher saveVoucher(VoucherRequestDto requestDto) {
        // enum활용
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

    // 수업에서 작성한 코드
    public void userVoucher(Voucher voucher) {
    }
}
