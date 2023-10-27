package com.programmers.vouchermanagement.voucher.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.programmers.vouchermanagement.customer.domain.Customer;
import com.programmers.vouchermanagement.customer.repository.CustomerRepository;
import com.programmers.vouchermanagement.util.Validator;
import com.programmers.vouchermanagement.voucher.domain.Voucher;
import com.programmers.vouchermanagement.voucher.dto.AssignVoucherRequest;
import com.programmers.vouchermanagement.voucher.dto.CreateVoucherRequest;
import com.programmers.vouchermanagement.voucher.dto.UpdateVoucherRequest;
import com.programmers.vouchermanagement.voucher.dto.VoucherResponse;
import com.programmers.vouchermanagement.voucher.repository.VoucherRepository;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;
    private final CustomerRepository customerRepository;

    public VoucherService(VoucherRepository voucherRepository, CustomerRepository customerRepository) {
        this.voucherRepository = voucherRepository;
        this.customerRepository = customerRepository;
    }

    public VoucherResponse create(CreateVoucherRequest request) {
        Validator.validateDiscountValue(request);
        Voucher voucher = new Voucher(UUID.randomUUID(), request.discountValue(), request.voucherType());
        voucherRepository.save(voucher);
        return VoucherResponse.from(voucher);
    }

    public List<VoucherResponse> readAllVouchers() {
        List<Voucher> vouchers = voucherRepository.findAll();
        return vouchers.stream()
                .map(VoucherResponse::from)
                .toList();
    }

    public VoucherResponse findById(UUID voucherId) {
        Voucher voucher = voucherRepository.findById(voucherId)
                .orElseThrow(() -> new NoSuchElementException("There is no voucher with %s".formatted(voucherId)));
        return VoucherResponse.from(voucher);
    }

    public VoucherResponse update(UpdateVoucherRequest request) {
        validateVoucherIdExisting(request.voucherId());
        Voucher voucher = new Voucher(request.voucherId(), request.discountValue(), request.voucherType());
        Voucher updatedVoucher = voucherRepository.save(voucher);
        return VoucherResponse.from(updatedVoucher);
    }

    public void deleteById(UUID voucherId) {
        validateVoucherIdExisting(voucherId);
        voucherRepository.deleteById(voucherId);
    }

    public void assignToCustomer(AssignVoucherRequest request) {
        validateCustomerIdExisting(request.customerId());
        VoucherResponse foundVoucher = findById(request.voucherId());
        Voucher voucher = new Voucher(request.voucherId(), foundVoucher.getDiscountValue(), foundVoucher.getVoucherType(), request.customerId());
        voucherRepository.save(voucher);
    }

    public void releaseFromCustomer(AssignVoucherRequest request) {
        validateCustomerIdExisting(request.customerId());
        VoucherResponse foundVoucher = findById(request.voucherId());
        Voucher voucher = new Voucher(foundVoucher.getVoucherId(), foundVoucher.getDiscountValue(), foundVoucher.getVoucherType());
        voucherRepository.save(voucher);
    }

    public List<VoucherResponse> findByCustomerId(UUID customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NoSuchElementException("There is no customer with %s".formatted(customerId)));
        List<Voucher> vouchers = voucherRepository.findByCustomerId(customer.getCustomerId());

        return vouchers.stream()
                .map(VoucherResponse::from)
                .toList();
    }

    private void validateVoucherIdExisting(UUID voucherId) {
        if (!voucherRepository.existById(voucherId)) {
            throw new NoSuchElementException("There is no voucher with %s".formatted(voucherId));
        }
    }

    private void validateCustomerIdExisting(UUID voucherId) {
        if (!customerRepository.existById(voucherId)) {
            throw new NoSuchElementException("There is no voucher with %s".formatted(voucherId));
        }
    }
}
