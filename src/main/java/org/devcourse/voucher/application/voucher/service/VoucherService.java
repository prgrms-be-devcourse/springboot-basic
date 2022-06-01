package org.devcourse.voucher.application.voucher.service;

import org.devcourse.voucher.application.voucher.controller.dto.VoucherRequest;
import org.devcourse.voucher.application.voucher.model.Voucher;
import org.devcourse.voucher.application.voucher.model.VoucherType;
import org.devcourse.voucher.application.voucher.repository.VoucherRepository;
import org.devcourse.voucher.core.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.devcourse.voucher.core.exception.ErrorType.NOT_FOUND_VOUCHER;


@Transactional
@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;
    private final Logger logger = LoggerFactory.getLogger(VoucherService.class);

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher createVoucher(VoucherType voucherType, long discount) {
        logger.info("Service : Create Voucher");
        Voucher voucher = voucherType.voucherCreator(UUID.randomUUID(), discount);
        return voucherRepository.insert(voucher);
    }

    @Transactional(readOnly = true)
    public Page<Voucher> recallAllVoucher(Pageable pageable) {
        logger.info("Service : Voucher Inquiry");
        return voucherRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Voucher recallVoucherById(UUID voucherId) {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_VOUCHER, voucherId));
    }

    public Voucher updateVoucher(UUID voucherId, long discount) {
        Voucher voucher = voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_VOUCHER, voucherId));
        voucher.setDiscount(discount);
        return voucherRepository.update(voucher);
    }

    public void deleteVoucher(UUID voucherId) {
        voucherRepository.deleteById(voucherId);
    }
}
