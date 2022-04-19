package org.prgrms.part1.engine.service;

import org.prgrms.part1.engine.domain.Customer;
import org.prgrms.part1.engine.domain.Voucher;
import org.prgrms.part1.engine.repository.CustomerRepository;
import org.prgrms.part1.engine.repository.VoucherRepository;
import org.prgrms.part1.exception.VoucherException;
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

    public List<Voucher> getAllVouchers() {
        return voucherRepository.findAll();
    }

    public Voucher getVoucher(UUID voucherId) {
        Optional<Voucher> voucher = voucherRepository.findById(voucherId);
        if (voucher.isEmpty()) {
            throw new VoucherException("Invalid VoucherId.");
        }
        return voucher.get();
    }

    public Voucher insertVoucher(Voucher voucher) {
        return voucherRepository.insert(voucher);
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
}
