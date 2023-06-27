package me.kimihiqq.vouchermanagement.domain.voucher.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kimihiqq.vouchermanagement.domain.voucher.FixedAmountVoucher;
import me.kimihiqq.vouchermanagement.domain.voucher.PercentDiscountVoucher;
import me.kimihiqq.vouchermanagement.domain.voucher.Voucher;
import me.kimihiqq.vouchermanagement.domain.voucher.dto.VoucherDto;
import me.kimihiqq.vouchermanagement.domain.voucher.repository.VoucherRepository;
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
        log.info("Creating voucher with type: {} and discount: {}", voucherDto.type(), voucherDto.discount());
        Voucher voucher;
        String type = voucherDto.type();
        long discount = voucherDto.discount();

        if (type.equalsIgnoreCase("fixed")) {
            if (discount < 0) {
                throw new IllegalArgumentException("Discount amount cannot be negative.");
            }
            voucher = new FixedAmountVoucher(UUID.randomUUID(), type, discount);
        } else if (type.equalsIgnoreCase("percent")) {
            if (discount < 0 || discount > 100) {
                throw new IllegalArgumentException("Discount rate must be between 0 and 100.");
            }
            voucher = new PercentDiscountVoucher(UUID.randomUUID(), type, discount);
        } else {
            throw new IllegalArgumentException("Invalid voucher type: " + type);
        }
        log.info("Voucher created with id: {}", voucher.getVoucherId());
        return voucherRepository.save(voucher);
    }

    @Override
    public List<Voucher> listVouchers() {
        log.info("Listing vouchers");
        return voucherRepository.findAll();
    }
}