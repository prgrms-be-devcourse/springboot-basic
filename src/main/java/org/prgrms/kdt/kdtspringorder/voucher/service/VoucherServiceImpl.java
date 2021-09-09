package org.prgrms.kdt.kdtspringorder.voucher.service;

import org.prgrms.kdt.kdtspringorder.common.enums.VoucherType;
import org.prgrms.kdt.kdtspringorder.common.exception.VoucherNotFoundException;
import org.prgrms.kdt.kdtspringorder.voucher.domain.Voucher;
import org.prgrms.kdt.kdtspringorder.voucher.repository.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * 요구사항에 따라 바우처 데이터를 가공하여 반환합니다.
 */
@Service
@Transactional
public class VoucherServiceImpl implements VoucherService {

    private static final Logger logger = LoggerFactory.getLogger(VoucherServiceImpl.class);

    private final VoucherRepository voucherRepository;

    public VoucherServiceImpl(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    @Override
    public List<Voucher> getVouchers() {
        logger.info("Access getVouchers()");
        return this.voucherRepository.findAll();
    }

    @Override
    public Voucher getVoucher(UUID voucherId) {
        logger.info("Access getVoucher()");
        logger.info("[Param] voucherId = " + voucherId);
        return this.voucherRepository
                .findById(voucherId)
                .orElseThrow( () -> new VoucherNotFoundException(voucherId));
    }

    @Override
    public List<Voucher> getVouchersByCustomer(UUID customerId) {
        return this.voucherRepository.findByCustomerId(customerId);
    }

    @Override
    public UUID saveVoucher(VoucherType type, long discount) {

        logger.info("Access saveVoucher()");
        logger.info("[Param] VoucherType = " + type);
        logger.info("[Param] discount = " + discount);

        Voucher createdVoucher = type.createVoucher(discount);
        return this.voucherRepository.insert(createdVoucher);

    }

    @Override
    public void updateVoucherDiscountAmount(UUID voucherId, long amount) {
        this.voucherRepository.updateDiscount(voucherId, amount);
    }

    @Override
    public void allocateVoucherToCustomer(UUID voucherId, UUID customerId) {
        this.voucherRepository.updateCustomerId(voucherId, customerId);
    }

    @Override
    public void deleteVoucher(UUID voucherId) {
        this.voucherRepository.delete(voucherId);
    }

}
