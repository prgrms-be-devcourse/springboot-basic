package com.pgms.part1.domain.voucher.service;

import com.pgms.part1.domain.voucher.dto.VoucherCreateRequestDto;
import com.pgms.part1.domain.voucher.entity.FixedAmountDiscountVoucher;
import com.pgms.part1.domain.voucher.entity.PercentDiscountVoucher;
import com.pgms.part1.domain.voucher.entity.Voucher;
import com.pgms.part1.domain.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public void createFixedAmountVoucher(VoucherCreateRequestDto voucherCreateRequestDto) {
        FixedAmountDiscountVoucher fixedAmountDiscountVoucher = Voucher.newFixedAmountDiscountVoucher(voucherCreateRequestDto.discount());
        voucherRepository.add(fixedAmountDiscountVoucher);
    }

    public void createPercentDiscountVoucher(VoucherCreateRequestDto voucherCreateRequestDto) {
        PercentDiscountVoucher percentDiscountVoucher = Voucher.newPercentDiscountVoucher(voucherCreateRequestDto.discount());
        voucherRepository.add(percentDiscountVoucher);
    }

    public List<Voucher> listVoucher() {
        return voucherRepository.list();
    }
}
