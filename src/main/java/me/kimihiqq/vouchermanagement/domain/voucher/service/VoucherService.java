package me.kimihiqq.vouchermanagement.domain.voucher.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kimihiqq.vouchermanagement.domain.voucher.Voucher;
import me.kimihiqq.vouchermanagement.domain.voucher.dto.VoucherDto;
import me.kimihiqq.vouchermanagement.domain.voucher.repository.VoucherRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Profile({"db", "test"})
@Slf4j
@RequiredArgsConstructor
@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public Voucher createVoucher(VoucherDto voucherDto) {
        Voucher voucher = voucherDto.toVoucher();
        return voucherRepository.save(voucher);
    }

    public List<Voucher> listVouchers() {
        return voucherRepository.findAll();
    }

    public Optional<Voucher> findVoucherById(UUID voucherId) {
        return voucherRepository.findById(voucherId);
    }

    public void deleteById(UUID voucherId) { voucherRepository.deleteById(voucherId);}

    public List<Voucher> listVouchersByCreationDateTimeBetween(LocalDateTime start, LocalDateTime end) {
        return voucherRepository.findAllByCreationDateTimeBetween(start, end);
    }
}