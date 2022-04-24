package org.prgrms.kdt.domain.voucher.service;

import org.prgrms.kdt.domain.voucher.dto.VoucherCreateRequest;
import org.prgrms.kdt.domain.voucher.dto.VoucherUpdateRequest;
import org.prgrms.kdt.domain.voucher.exception.VoucherDataException;
import org.prgrms.kdt.domain.voucher.model.Voucher;
import org.prgrms.kdt.domain.voucher.model.VoucherType;
import org.prgrms.kdt.domain.voucher.repository.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.prgrms.kdt.domain.common.exception.ExceptionType.NOT_SAVED;

@Service
@Transactional(readOnly = true)
public class VoucherService {
    private final Logger logger = LoggerFactory.getLogger(VoucherService.class);
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    @Transactional
    public UUID save(VoucherCreateRequest createRequest) {
        Voucher voucher = createRequest.toEntity();
        UUID savedId = voucherRepository.save(voucher);
        logger.info("save Voucher id: {}", voucher.getVoucherId());
        return savedId;
    }

    public Optional<Voucher> getVoucherById(UUID voucherId) {
        Optional<Voucher> voucher = voucherRepository.findById(voucherId);
        logger.info("find Voucher By Id {}", voucherId);
        return voucher;
    }

    public List<Voucher> getAllVouchers() {
        List<Voucher> vouchers = voucherRepository.findAll();
        logger.info("find All Voucher size: {}", vouchers.size());
        return vouchers;
    }

    public List<Voucher> getVouchersByCustomerId(UUID customerId) {
        List<Voucher> vouchers = voucherRepository.findByCustomerId(customerId);
        logger.info("Get voucher by customerId size: {}", vouchers.size());
        return vouchers;
    }

    public List<Voucher> getVoucherByTypeAndDate(VoucherType voucherType, LocalDate date) {
        List<Voucher> vouchers = voucherRepository.findByTypeAndDate(voucherType, date);
        logger.info("Get voucher by type and date size: {}", vouchers.size());
        return vouchers;
    }

    @Transactional
    public void updateCustomerId(UUID voucherId, UUID customerId) {
        voucherRepository.findById(voucherId)
                .orElseThrow(() -> new VoucherDataException(NOT_SAVED));
        int update = voucherRepository.updateCustomerId(voucherId, customerId);
        logger.info("Update Voucher's customerId count: {}", update);
    }

    @Transactional
    public void update(VoucherUpdateRequest updateRequest, UUID voucherId) {
        voucherRepository.findById(voucherId)
                .orElseThrow(() -> new VoucherDataException(NOT_SAVED));
        Voucher voucher = updateRequest.toEntity();
        voucherRepository.update(voucher);
    }

    @Transactional
    public void remove(UUID voucherId) {
        voucherRepository.findById(voucherId)
                .orElseThrow(() -> new VoucherDataException(NOT_SAVED));
        voucherRepository.deleteById(voucherId);
        logger.info("Delete Voucher id: {}", voucherId);
    }
}
