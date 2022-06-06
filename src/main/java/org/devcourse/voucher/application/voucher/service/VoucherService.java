package org.devcourse.voucher.application.voucher.service;

import org.devcourse.voucher.application.voucher.controller.dto.VoucherResponse;
import org.devcourse.voucher.application.voucher.model.Voucher;
import org.devcourse.voucher.application.voucher.model.VoucherType;
import org.devcourse.voucher.application.voucher.repository.VoucherRepository;
import org.devcourse.voucher.core.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    public VoucherResponse createVoucher(VoucherType voucherType, long discount) {
        logger.info("Service : Create Voucher");
        Voucher voucher = voucherType.voucherCreator(UUID.randomUUID(), discount);
        return VoucherResponse.of(voucherRepository.insert(voucher));
    }

    @Transactional(readOnly = true)
    public List<VoucherResponse> recallAllVoucher(Pageable pageable) {
        logger.info("Service : Voucher Inquiry");
        return voucherRepository.findAll(pageable)
                .stream()
                .map(VoucherResponse::of)
                .toList();
    }

    @Transactional(readOnly = true)
    public VoucherResponse recallVoucherById(UUID voucherId) {
        return voucherRepository
                .findById(voucherId)
                .map(VoucherResponse::of)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_VOUCHER, voucherId));
    }

    public VoucherResponse updateVoucher(UUID voucherId, long discount) {
        Voucher voucher = voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_VOUCHER, voucherId));
        voucher.setDiscount(discount);
        return VoucherResponse.of(voucherRepository.update(voucher));
    }

    public void deleteVoucher(UUID voucherId) {
        voucherRepository.deleteById(voucherId);
    }
}
