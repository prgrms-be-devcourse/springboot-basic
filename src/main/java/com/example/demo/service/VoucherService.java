package com.example.demo.service;

import com.example.demo.domain.voucher.FixedAmountVoucher;
import com.example.demo.domain.voucher.PercentDiscountVoucher;
import com.example.demo.domain.voucher.Voucher;
import com.example.demo.domain.voucher.VoucherRepository;
import com.example.demo.dto.voucher.VoucherResponseDto;
import com.example.demo.util.VoucherType;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public VoucherResponseDto save(VoucherType voucherType, int amount) {
        Voucher voucher = switch (voucherType) {
            case FIX -> new FixedAmountVoucher(amount);
            case PERCENT -> new PercentDiscountVoucher(amount);
        };
        voucherRepository.save(voucher);
        return VoucherResponseDto.from(voucher);
    }

    public VoucherResponseDto read(UUID id) {
        return VoucherResponseDto.from(voucherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("id에 해당하는 바우처가 없습니다.")));
    }

    public List<VoucherResponseDto> readVoucherList() {
        List<Voucher> voucherList = voucherRepository.findAll();

        return voucherList.stream()
                .map(VoucherResponseDto::from)
                .toList();
    }
}
