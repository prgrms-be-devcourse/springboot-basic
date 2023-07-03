package me.kimihiqq.vouchermanagement.domain.voucher.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kimihiqq.vouchermanagement.domain.voucher.Voucher;
import me.kimihiqq.vouchermanagement.domain.voucher.dto.VoucherDto;
import me.kimihiqq.vouchermanagement.domain.voucher.repository.VoucherRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Profile("db")
@Slf4j
@RequiredArgsConstructor
@Service
public class JDBCVoucherService implements VoucherService {
    private final VoucherRepository voucherRepository;

    @Override
    public Voucher createVoucher(VoucherDto voucherDto) {
        Voucher voucher = voucherDto.toVoucher();
        return voucherRepository.save(voucher);
    }

    @Override
    public List<Voucher> listVouchers() {
        return voucherRepository.findAll();
    }

    @Override
    public Optional<Voucher> findVoucherById(UUID voucherId) {
        return voucherRepository.findById(voucherId);
    }
}
