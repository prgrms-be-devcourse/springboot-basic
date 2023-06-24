package me.kimihiqq.vouchermanagement.service;


import lombok.RequiredArgsConstructor;
import me.kimihiqq.vouchermanagement.domain.FixedAmountVoucher;
import me.kimihiqq.vouchermanagement.domain.PercentDiscountVoucher;
import me.kimihiqq.vouchermanagement.domain.Voucher;
import me.kimihiqq.vouchermanagement.dto.VoucherDto;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class VoucherServiceImpl implements VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherServiceImpl(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    @Override
    public Voucher createVoucher(VoucherDto voucherDto) {
        Voucher voucher;
        String type = voucherDto.getType();
        long discount = voucherDto.getDiscount();

        if (type.equalsIgnoreCase("fixed")) {
            voucher = new FixedAmountVoucher(discount);
        } else if (type.equalsIgnoreCase("percent")) {
            voucher = new PercentDiscountVoucher(discount);
        } else {
            throw new IllegalArgumentException("Invalid voucher type: " + type);
        }
        return voucherRepository.save(voucher);
    }

    @Override
    public List<Voucher> listVouchers() {
        return voucherRepository.findAll();
    }
}