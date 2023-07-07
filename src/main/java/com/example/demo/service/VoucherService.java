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

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public VoucherDto save(VoucherType voucherType, int amount) {
        Voucher voucher = switch (voucherType) {
            case FIX -> new FixedAmountVoucher(amount);
            case PERCENT -> new PercentDiscountVoucher(amount);
        };
        voucherRepository.save(voucher);
        return VoucherDto.from(voucher);
    }

    public VoucherDto readAll(UUID id) {
        return VoucherDto.from(voucherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("id에 해당하는 바우처가 없습니다.")));
    }

    public List<VoucherDto> readVoucherList() {
        List<Voucher> voucherList = voucherRepository.findAll();

        return voucherList.stream()
                .map(VoucherDto::from)
                .toList();
    }
}
