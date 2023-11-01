package com.programmers.vouchermanagement.service;

import com.programmers.vouchermanagement.domain.voucher.Voucher;
import com.programmers.vouchermanagement.domain.voucher.VoucherFactory;
import com.programmers.vouchermanagement.domain.voucher.VoucherType;
import com.programmers.vouchermanagement.dto.VoucherDto;
import com.programmers.vouchermanagement.message.ErrorMessage;
import com.programmers.vouchermanagement.repository.voucher.VoucherRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    @Transactional(readOnly = true)
    public List<Voucher> findAllVouchers() {
        return voucherRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Voucher findVoucherById(UUID id) {
        return voucherRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(ErrorMessage.VOUCHER_NOT_FOUND_MESSAGE.getMessage()));
    }

    public List<Voucher> findVoucherByName(String name) {
        return voucherRepository.findByNameLike(name);
    }

    @Transactional(readOnly = false)
    public Voucher createVoucher(VoucherDto.CreateRequest voucherDto) {
        voucherRepository.findByName(voucherDto.name()).ifPresent(voucher -> {
            throw new IllegalArgumentException(ErrorMessage.VOUCHER_ALREADY_EXISTS_MESSAGE.getMessage());
        });
        Voucher voucher = VoucherFactory.createVoucher(voucherDto);
        return voucherRepository.save(voucher);
    }

    @Transactional(readOnly = false)
    public void deleteVoucher(UUID id) {
        int affectedRow = voucherRepository.delete(id);
        if (affectedRow == 0) {
            throw new NoSuchElementException(ErrorMessage.VOUCHER_NOT_FOUND_MESSAGE.getMessage());
        }
    }

    public List<Voucher> findVoucherByTypeAndDates(VoucherType voucherType, LocalDate startDate, LocalDate endDate) {
        if (startDate == null) startDate = LocalDate.of(0, 1, 1);
        if (endDate == null) endDate = LocalDate.of(9999, 12, 31);
        String typeStr = (voucherType != null) ? voucherType.toString() : "";
        return voucherRepository.findByTypeAndDates(typeStr, startDate, endDate);
    }
}
