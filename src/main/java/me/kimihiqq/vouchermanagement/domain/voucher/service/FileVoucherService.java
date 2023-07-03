package me.kimihiqq.vouchermanagement.domain.voucher.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kimihiqq.vouchermanagement.domain.voucher.FixedAmountVoucher;
import me.kimihiqq.vouchermanagement.domain.voucher.PercentDiscountVoucher;
import me.kimihiqq.vouchermanagement.domain.voucher.Voucher;
import me.kimihiqq.vouchermanagement.domain.voucher.dto.VoucherDto;
import me.kimihiqq.vouchermanagement.domain.voucher.repository.VoucherRepository;
import me.kimihiqq.vouchermanagement.option.VoucherTypeOption;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Profile("file")
@Slf4j
@RequiredArgsConstructor
@Service
public class FileVoucherService implements VoucherService {
    private final VoucherRepository voucherRepository;

    @Override
    public Voucher createVoucher(VoucherDto voucherDto) {
        log.info("Creating voucher with type: {} and discount: {}", voucherDto.type(), voucherDto.discount());
        Voucher voucher;
        VoucherTypeOption type = VoucherTypeOption.valueOf(voucherDto.type().toUpperCase());
        long discount = voucherDto.discount();

        switch (type) {
            case FIXED:
                voucher = new FixedAmountVoucher(UUID.randomUUID(), discount);
                break;
            case PERCENT:
                voucher = new PercentDiscountVoucher(UUID.randomUUID(), discount);
                break;
            default:
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

    @Override
    public Optional<Voucher> findVoucherById(UUID voucherId) {
        return Optional.empty();
    }
}