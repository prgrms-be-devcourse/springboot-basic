package com.example.demo.service;

import com.example.demo.domain.voucher.FixedAmountVoucher;
import com.example.demo.domain.voucher.PercentDiscountVoucher;
import com.example.demo.domain.voucher.Voucher;
import com.example.demo.domain.voucher.VoucherRepository;
import com.example.demo.dto.VoucherDto;
import com.example.demo.util.VoucherType;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class VoucherService {

    VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public void createVoucher(VoucherType voucherType, int amount) {
        Voucher voucher = switch (voucherType) {
            case FIXED_AMOUNT_VOUCHER -> new FixedAmountVoucher(amount);
            case PERCENT_DISCOUNT_VOUCHER -> new PercentDiscountVoucher(amount);
        };

        voucherRepository.save(voucher);
    }

    public VoucherDto readVoucher(UUID id) {
        VoucherDto voucherDto = voucherRepository.findById(id)
                .orElseThrow(() -> {
                    return new RuntimeException("id에 해당하는 바우처가 없습니다.");
                }).convertToVoucherDto();

        return voucherDto;
    }

    public List<VoucherDto> readVoucherList() {
        List<Voucher> voucherList = voucherRepository.findAll();

        return voucherList.stream()
                .map(Voucher::convertToVoucherDto)
                .toList();
    }
}
