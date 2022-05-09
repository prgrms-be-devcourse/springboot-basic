package com.prgrms.vouchermanagement.voucher.service;

import com.prgrms.vouchermanagement.voucher.Voucher;
import com.prgrms.vouchermanagement.voucher.VoucherType;
import com.prgrms.vouchermanagement.voucher.repository.VoucherRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    /**
     * @throws DataAccessException : Repository에서 쿼리 실행에 문제가 발생한 경우 던져진다.
     */
    public Long addVoucher(VoucherType voucherType, long amount) throws IllegalArgumentException, DataAccessException {
        Voucher newVoucher = voucherType.constructor(amount, LocalDateTime.now());
        return voucherRepository.save(newVoucher);
    }

    /**
     * @throws DataAccessException : Repository에서 쿼리 실행에 문제가 발생한 경우 던져진다.
     */
    public List<Voucher> findAllVouchers() throws DataAccessException {
        return voucherRepository.findAll();
    }

    /**
     * @throws DataAccessException : Repository에서 쿼리 실행에 문제가 발생한 경우 던져진다.
     */
    public boolean isRegisteredVoucher(Long voucherId) throws DataAccessException {
        return voucherRepository.findById(voucherId).isPresent();
    }

    public Optional<Voucher> findVoucherById(Long voucherId) {
        return voucherRepository.findById(voucherId);
    }

    public List<Voucher> findVoucherByType(VoucherType voucherType) {
        return voucherRepository.findByType(voucherType);
    }

    public List<Voucher> findVoucherByPeriod(LocalDateTime from, LocalDateTime end) {
        return voucherRepository.findByPeriod(from, end);
    }

    public List<Voucher> findVoucherByCustomer(Long customerId) throws IllegalArgumentException, DataAccessException {
        return voucherRepository.findVoucherByCustomer(customerId);
    }

    public void removeVoucher(Long voucherId) {
        voucherRepository.remove(voucherId);
    }

}
