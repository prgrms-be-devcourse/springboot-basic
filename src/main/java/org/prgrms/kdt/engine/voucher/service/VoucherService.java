package org.prgrms.kdt.engine.voucher.service;

import org.prgrms.kdt.engine.customer.domain.Customer;
import org.prgrms.kdt.engine.customer.repository.CustomerRepository;
import org.prgrms.kdt.engine.voucher.domain.Voucher;
import org.prgrms.kdt.engine.voucher.VoucherType;
import org.prgrms.kdt.engine.voucher.repository.VoucherRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;
    private final CustomerRepository customerRepository;

    public VoucherService(VoucherRepository voucherRepository, CustomerRepository customerRepository) {
        this.voucherRepository = voucherRepository;
        this.customerRepository = customerRepository;
    }

    public Voucher getVoucher(UUID voucherId) {
        validateVoucherId(voucherId);
        return voucherRepository.findById(voucherId).get();
    }

    public void useVoucher(Voucher voucher) {
        // 차후 구현
    }

    public Voucher createVoucher(VoucherType type, long rate) {
        var voucher = type.createVoucher(rate);
        voucherRepository.insert(voucher);
        return voucher;
    }

    public Optional<Map<UUID, Voucher>> listVoucher() {
        return voucherRepository.getAll();
    }

    public void setVoucherCustomer(UUID voucherId, UUID customerId) {
        validateVoucherId(voucherId);
        validateCustomerId(customerId);
        voucherRepository.setCustomerId(voucherId, customerId);
    }

    private void validateVoucherId(UUID voucherId) {
        try {
            Optional<Voucher> voucher = voucherRepository.findById(voucherId);
        } catch (DataAccessException e) {
            throw new RuntimeException("Cannot find the voucher for " + voucherId);
        }
    }

    private void validateCustomerId(UUID customerId) {
        try {
            Optional<Customer> customer = customerRepository.findById(customerId);
        } catch (DataAccessException e) {
            throw new RuntimeException("Cannot find the customer for " + customerId);
        }
    }

    public Optional<Map<UUID, Voucher>> listCustomerVoucher(UUID customerId) {
        return voucherRepository.getCustomerVoucher(customerId);
    }

    public void deleteCustomerVoucher(UUID customerId) {
        voucherRepository.deleteCustomerVoucher(customerId);
    }

    public Optional<UUID> getVoucherOwner(UUID voucherId) {
        return voucherRepository.findCustomerByVoucher(voucherId);
    }

    public void deleteVoucher(UUID voucherId) {
        voucherRepository.deleteVoucher(voucherId);
    }
}
