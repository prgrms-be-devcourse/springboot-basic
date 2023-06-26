package me.kimihiqq.vouchermanagement.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kimihiqq.vouchermanagement.domain.FixedAmountVoucher;
import me.kimihiqq.vouchermanagement.domain.PercentDiscountVoucher;
import me.kimihiqq.vouchermanagement.domain.Voucher;
import me.kimihiqq.vouchermanagement.dto.VoucherDto;
import me.kimihiqq.vouchermanagement.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j

@RequiredArgsConstructor
@Service
public class VoucherServiceImpl implements VoucherService {
    private final VoucherRepository voucherRepository;

    @Override
    public Voucher createVoucher(VoucherDto voucherDto) {
        log.info("Creating voucher with type: " + voucherDto.getType() + " and discount: " + voucherDto.getDiscount());
        Voucher voucher;
        String type = voucherDto.getType();
        long discount = voucherDto.getDiscount();

        if (type.equalsIgnoreCase("fixed")) {
            voucher = new FixedAmountVoucher(UUID.randomUUID(), type, discount);
        } else if (type.equalsIgnoreCase("percent")) {
            voucher = new PercentDiscountVoucher(UUID.randomUUID(), type, discount);
        } else {
            throw new IllegalArgumentException("Invalid voucher type: " + type);
        }
        log.info("Voucher created with id: " + voucher.getVoucherId());
        return voucherRepository.save(voucher);
    }

    @Override
    public List<Voucher> listVouchers() {
        log.info("Listing vouchers");
        return voucherRepository.findAll();
    }
}