package com.prgrms.vouchermanagement.voucher.service;

import com.prgrms.vouchermanagement.voucher.Voucher;
import com.prgrms.vouchermanagement.voucher.VoucherType;
import com.prgrms.vouchermanagement.voucher.repository.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VoucherService {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    /**
     * @throws DataAccessException : Repository에서 쿼리 실행에 문제가 발생한 경우 던져진다.
     */
    public UUID addVoucher(VoucherType voucherType, long amount) throws IllegalArgumentException, DataAccessException {
        Voucher newVoucher = voucherType.constructor(UUID.randomUUID(), amount, LocalDateTime.now());
        voucherRepository.save(newVoucher);
        log.info("voucher is saved - {}", newVoucher);
        return newVoucher.getVoucherId();
    }

    /**
     * @throws DataAccessException : Repository에서 쿼리 실행에 문제가 발생한 경우 던져진다.
     */
    public List<Voucher> findAllVouchers() throws DataAccessException {
        List<Voucher> allVouchers = voucherRepository.findAll();
        log.info("find all vouchers. size={}", allVouchers.size());
        return allVouchers;
    }

    /**
     * @throws DataAccessException : Repository에서 쿼리 실행에 문제가 발생한 경우 던져진다.
     */
    public boolean isRegisteredVoucher(UUID voucherId) throws DataAccessException {
        return voucherRepository.findById(voucherId).isPresent();
    }

    public Optional<Voucher> findVoucherById(UUID voucherId) {
        return voucherRepository.findById(voucherId);
    }

    public List<Voucher> findVoucherByType(VoucherType voucherType) {
        return voucherRepository.findByType(voucherType);
    }

    public List<Voucher> findVoucherByPeriod(LocalDateTime from, LocalDateTime end) {
        return voucherRepository.findByPeriod(from, end);
    }

    public List<Voucher> findVoucherByCustomer(UUID customerId) throws IllegalArgumentException, DataAccessException {
        return voucherRepository.findVoucherByCustomer(customerId);
    }

    public void removeVoucher(UUID voucherId) {
        voucherRepository.remove(voucherId);
    }

}
