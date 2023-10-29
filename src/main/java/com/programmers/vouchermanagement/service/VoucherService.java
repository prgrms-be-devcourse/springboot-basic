package com.programmers.vouchermanagement.service;

import com.programmers.vouchermanagement.domain.voucher.Voucher;
import com.programmers.vouchermanagement.domain.voucher.VoucherFactory;
import com.programmers.vouchermanagement.dto.VoucherDto;
import com.programmers.vouchermanagement.message.ErrorMessage;
import com.programmers.vouchermanagement.repository.voucher.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public List<Voucher> findAllVouchers() {
        return voucherRepository.findAll();
    }

    public Voucher findByVoucherId(UUID id) {
        return voucherRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(ErrorMessage.VOUCHER_NOT_FOUND_MESSAGE.getMessage()));
    }

    public List<Voucher> findByVoucherName(String name) {
        return voucherRepository.findByNameLike(name);
    }

    public Voucher createVoucher(VoucherDto.Create createDto) {
        voucherRepository.findByName(createDto.voucherName).ifPresent(voucher -> {
            throw new IllegalArgumentException(ErrorMessage.VOUCHER_ALREADY_EXISTS_MESSAGE.getMessage());
        });
        Voucher voucher = VoucherFactory.createVoucher(createDto);
        return voucherRepository.save(voucher);
    }

    public void deleteVoucher(UUID id) {
        int affectedRow = voucherRepository.delete(id);
        if (affectedRow == 0) {
            throw new NoSuchElementException(ErrorMessage.VOUCHER_NOT_FOUND_MESSAGE.getMessage());
        }
    }
}
