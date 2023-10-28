package com.prgms.vouchermanager.service.voucher;


import com.prgms.vouchermanager.domain.voucher.FixedAmountVoucher;
import com.prgms.vouchermanager.domain.voucher.PercentDiscountVoucher;
import com.prgms.vouchermanager.domain.voucher.Voucher;
import com.prgms.vouchermanager.dto.CreateVoucherDto;
import com.prgms.vouchermanager.dto.UpdateVoucherDto;
import com.prgms.vouchermanager.repository.voucher.VoucherRepository;
import com.prgms.vouchermanager.validation.InputValidation;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static com.prgms.vouchermanager.exception.ExceptionType.*;

@Service
public class VoucherService {

    private final InputValidation inputValidation;
    private final VoucherRepository voucherRepository;

    public VoucherService(InputValidation inputValidation, VoucherRepository voucherRepository) {
        this.inputValidation = inputValidation;
        this.voucherRepository = voucherRepository;
    }

    @Transactional
    public Voucher create(CreateVoucherDto dto) {

        Voucher voucher = null;

        if (dto.getVoucherType() == 1) {
            voucher = new FixedAmountVoucher(UUID.randomUUID(), dto.getValue());
        } else if (dto.getVoucherType() == 2) {
            if (!inputValidation.validVoucherPercent(dto.getValue())) {
                throw new RuntimeException(INVALID_VOUCHER_PERCENT.getMessage());
            }
            voucher = new PercentDiscountVoucher(UUID.randomUUID(), dto.getValue());
        } else throw new RuntimeException(INVALID_VOUCHER_INFO.getMessage());

        try {
            return voucherRepository.save(voucher);
        } catch (DuplicateKeyException e) {
            throw new DuplicateKeyException(DUPLICATED_KEY.getMessage());
        }
    }

    @Transactional
    public void update(UUID id, UpdateVoucherDto dto) {

        Voucher voucher = null;

        if (dto.getVoucherType() == 1) {
            voucher = new FixedAmountVoucher(id, dto.getValue());
        } else if (dto.getVoucherType() == 2) {
            if (!inputValidation.validVoucherPercent(dto.getValue())) {
                throw new RuntimeException(INVALID_VOUCHER_PERCENT.getMessage());
            }
            voucher = new PercentDiscountVoucher(id, dto.getValue());
        } else throw new RuntimeException(INVALID_VOUCHER_INFO.getMessage());

        voucherRepository.update(voucher);

    }
    @Transactional(readOnly = true)

    public Voucher findById(UUID id) {

        return voucherRepository.findById(id).orElseThrow(() -> new EmptyResultDataAccessException(INVALID_VOUCHER_ID.getMessage(),1));

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
}

