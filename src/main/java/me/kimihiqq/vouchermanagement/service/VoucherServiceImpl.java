package me.kimihiqq.vouchermanagement.service;


import lombok.RequiredArgsConstructor;
import me.kimihiqq.vouchermanagement.domain.FixedAmountVoucher;
import me.kimihiqq.vouchermanagement.domain.PercentDiscountVoucher;
import me.kimihiqq.vouchermanagement.domain.Voucher;
import me.kimihiqq.vouchermanagement.dto.VoucherDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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
        String discount = voucherDto.getDiscount();

        if (type.equalsIgnoreCase("fixed")) {
            long discountAmount = Long.parseLong(discount);
            voucher = new FixedAmountVoucher(UUID.randomUUID(), type, discountAmount);
        } else if (type.equalsIgnoreCase("percent")) {
            double discountRate = Double.parseDouble(discount);
            voucher = new PercentDiscountVoucher(UUID.randomUUID(), type, discountRate);
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