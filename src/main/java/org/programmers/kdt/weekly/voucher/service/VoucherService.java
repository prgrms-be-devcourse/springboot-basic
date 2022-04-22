package org.programmers.kdt.weekly.voucher.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.programmers.kdt.weekly.voucher.model.Voucher;
import org.programmers.kdt.weekly.voucher.VoucherDto;
import org.programmers.kdt.weekly.voucher.model.VoucherType;
import org.programmers.kdt.weekly.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Optional<Voucher> createVoucher(VoucherType voucherType, int value) {
        VoucherDto voucherDto = new VoucherDto(UUID.randomUUID(), value, LocalDateTime.now());
        Optional<Voucher> maybeVoucher = voucherType.create(voucherDto);

        if (maybeVoucher.isPresent()) {
            this.voucherRepository.insert(maybeVoucher.get());

            return maybeVoucher;
        }

        return Optional.empty();
    }

    public List<Voucher> getVoucherList() {
        return this.voucherRepository.findAll();
    }
}