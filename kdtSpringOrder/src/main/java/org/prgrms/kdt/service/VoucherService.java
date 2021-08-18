package org.prgrms.kdt.service;

import org.prgrms.kdt.domain.voucher.FixedAmountVoucher;
import org.prgrms.kdt.domain.voucher.PercentDiscountVoucher;
import org.prgrms.kdt.domain.voucher.Voucher;
import org.prgrms.kdt.dto.VoucherSaveRequestDto;
import org.prgrms.kdt.dto.VoucherType;
import org.prgrms.kdt.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher saveVoucher(VoucherSaveRequestDto voucherSaveRequestDto) {
        if(voucherSaveRequestDto.getVoucherType() == VoucherType.FIXED) {
            return voucherRepository.save(new FixedAmountVoucher(UUID.randomUUID(), voucherSaveRequestDto.getDiscount()));
        } else {
            return voucherRepository.save(new PercentDiscountVoucher(UUID.randomUUID(), voucherSaveRequestDto.getDiscount()));
        }
    }

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(()-> new RuntimeException(MessageFormat.format("Can not find a voucher for {0}", voucherId)));
    }

    public boolean isDuplicateVoucher(UUID voucherId) {
        return voucherRepository.findById(voucherId).isPresent();
    }

    public List<Voucher> getAllVouchers() {
        return voucherRepository
                .findAll();
    }

}