package com.prgms.vouchermanager.service.voucher;


import com.prgms.vouchermanager.domain.voucher.FixedAmountVoucher;
import com.prgms.vouchermanager.domain.voucher.PercentDiscountVoucher;
import com.prgms.vouchermanager.domain.voucher.Voucher;
import com.prgms.vouchermanager.domain.voucher.VoucherType;
import com.prgms.vouchermanager.dto.CreateVoucherDto;
import com.prgms.vouchermanager.dto.UpdateVoucherDto;
import com.prgms.vouchermanager.repository.voucher.VoucherRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.prgms.vouchermanager.exception.ExceptionType.INVALID_VOUCHER_ID;
import static com.prgms.vouchermanager.exception.ExceptionType.INVALID_VOUCHER_INFO;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    @Transactional
    public Voucher create(CreateVoucherDto dto) {

        Voucher voucher = null;

        if (dto.getVoucherType() == 1) {
            voucher = new FixedAmountVoucher(UUID.randomUUID(), dto.getValue(), null);
        } else if (dto.getVoucherType() == 2) {
            voucher = new PercentDiscountVoucher(UUID.randomUUID(), dto.getValue(), null);
        } else throw new RuntimeException(INVALID_VOUCHER_INFO.getMessage());

        return voucherRepository.save(voucher);
    }

    @Transactional
    public void update(UUID id, UpdateVoucherDto dto) {

        Voucher voucher = null;

        if (dto.getVoucherType() == 1) {
            voucher = new FixedAmountVoucher(id, dto.getValue(), null);
        } else if (dto.getVoucherType() == 2) {
            voucher = new PercentDiscountVoucher(id, dto.getValue(), null);
        } else throw new RuntimeException(INVALID_VOUCHER_INFO.getMessage());

        voucherRepository.update(voucher);

    }

    @Transactional(readOnly = true)

    public Voucher findById(UUID id) {

        return voucherRepository.findById(id).orElseThrow(() -> new EmptyResultDataAccessException(INVALID_VOUCHER_ID.getMessage(), 1));

    }

    @Transactional(readOnly = true)

    public List<Voucher> findAll() {
        return voucherRepository.findAll();
    }

    @Transactional
    public void deleteById(UUID id) {
        voucherRepository.deleteById(id);
    }

    @Transactional
    public void deleteAll() {
        voucherRepository.deleteAll();
    }

    public List<Voucher> findByDate(LocalDateTime startTime, LocalDateTime endTime) {
        return voucherRepository.findByDate(startTime, endTime);
    }

    public List<Voucher> findByType(VoucherType type) {
        return voucherRepository.findByType(type);
    }
}

