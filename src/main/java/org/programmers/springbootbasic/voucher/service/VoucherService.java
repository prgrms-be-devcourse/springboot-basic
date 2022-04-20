package org.programmers.springbootbasic.voucher.service;

import org.programmers.springbootbasic.voucher.model.Voucher;
import org.programmers.springbootbasic.voucher.model.VoucherDTO;
import org.programmers.springbootbasic.voucher.model.VoucherType;
import org.programmers.springbootbasic.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Optional<Voucher> getVoucher(UUID voucherId) {
        return voucherRepository
                .findById(voucherId);
    }

    public List<Voucher> getVoucherList() {
        return voucherRepository.findAll();
    }

    public Voucher createVoucher(VoucherType voucherType, long value) {
        VoucherDTO voucherDTO = new VoucherDTO(UUID.randomUUID(), value, LocalDateTime.now());
        var voucher = voucherType.create(voucherDTO);
        return voucherRepository.insert(voucher);
    }
}
