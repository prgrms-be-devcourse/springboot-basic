package org.prgrms.kdt.domain.voucher.service;

import org.prgrms.kdt.domain.voucher.exception.VoucherDataException;
import org.prgrms.kdt.domain.voucher.model.Voucher;
import org.prgrms.kdt.domain.voucher.model.VoucherType;
import org.prgrms.kdt.domain.voucher.repository.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    public UUID save(Voucher voucher) {
        return voucherRepository.save(voucher);
    }

    public Optional<Voucher> getVoucherById(UUID voucherId) {
        return voucherRepository.findById(voucherId);
    }

    public List<Voucher> getAllVouchers() {
        return voucherRepository.findAll();
    }

    public List<Voucher> getVouchersByCustomerId(UUID customerId) {
        return voucherRepository.findByCustomerId(customerId);
    }

    public List<Voucher> getVouchersNotAssignedToCustomer() {
        return voucherRepository.findByCustomerIdIsNull();
    }

    public List<Voucher> getVoucherByTypeAndDate(VoucherType voucherType, LocalDate date) {
        return voucherRepository.findByTypeAndDate(voucherType, date);
    }

    @Transactional
    public void updateCustomerId(UUID voucherId, UUID customerId) {
        voucherRepository.findById(voucherId)
                .orElseThrow(() -> new VoucherDataException(NOT_SAVED));
        voucherRepository.updateCustomerId(voucherId, customerId);
        logger.info("Update voucher's customerId, voucher id: {}", voucherId);
    }

    @Transactional
    public void update(UUID voucherId, VoucherType voucherType, long discountValue) {
        voucherRepository.findById(voucherId)
                .orElseThrow(() -> new VoucherDataException(NOT_SAVED));
        LocalDateTime now = LocalDateTime.now();
        Voucher voucher = new Voucher(voucherId, voucherType, discountValue, now, now);
        voucherRepository.update(voucher);
        logger.info("Update voucher, voucher id: {}", voucher.getVoucherId());
    }

    @Transactional
    public void remove(UUID voucherId) {
        voucherRepository.findById(voucherId)
                .orElseThrow(() -> new VoucherDataException(NOT_SAVED));
        voucherRepository.deleteById(voucherId);
        logger.info("Delete voucher, voucher id: {}", voucherId);
    }
}
