package org.prgrms.springbootbasic.engine.service;

import org.prgrms.springbootbasic.engine.domain.Customer;
import org.prgrms.springbootbasic.engine.domain.Voucher;
import org.prgrms.springbootbasic.engine.enumtype.ErrorCode;
import org.prgrms.springbootbasic.engine.repository.VoucherRepository;
import org.prgrms.springbootbasic.exception.RecordNotFoundException;
import org.prgrms.springbootbasic.exception.VoucherException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    @Transactional
    public List<Voucher> getAllVouchers() {
        return voucherRepository.findAll();
    }

    @Transactional
    public Voucher getVoucher(UUID voucherId) {
        Optional<Voucher> voucher = voucherRepository.findById(voucherId);
        if (voucher.isEmpty()) {
            throw new RecordNotFoundException("Invalid VoucherId.", ErrorCode.VOUCHER_NOT_FOUND);
        }
        return voucher.get();
    }

    @Transactional
    public List<Voucher> getVouchersByCustomer(Customer customer) {
        return voucherRepository.findByCustomer(customer);
    }

    @Transactional
    public Voucher insertVoucher(Voucher voucher) {
        return voucherRepository.insert(voucher);
    }

    @Transactional
    public Voucher updateVoucher(Voucher voucher) {
        return voucherRepository.update(voucher);
    }

    @Transactional
    public void allocateToCustomer(Voucher voucher, Customer customer) {
        if (voucher.getCustomerId().isPresent()) {
            throw new VoucherException("This voucher is already deployed.");
        }
        voucher.changeOwner(customer);
        voucherRepository.update(voucher);
    }

    @Transactional
    public void deallocateFromCustomer(Voucher voucher) {
        voucher.revokeOwner();
        voucherRepository.update(voucher);
    }

    @Transactional
    public void removeVoucher(Voucher voucher) {
        voucherRepository.deleteById(voucher.getVoucherId());
    }

    @Transactional
    public void removeVoucherById(UUID voucherId) {
        voucherRepository.deleteById(voucherId);
    }
}
