package com.programmers.vouchermanagement.voucher.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.programmers.vouchermanagement.customer.domain.Customer;
import com.programmers.vouchermanagement.customer.repository.CustomerRepository;
import com.programmers.vouchermanagement.voucher.domain.Voucher;
import com.programmers.vouchermanagement.voucher.domain.VoucherType;
import com.programmers.vouchermanagement.voucher.dto.CreateVoucherRequest;
import com.programmers.vouchermanagement.voucher.dto.SearchCreatedAtRequest;
import com.programmers.vouchermanagement.voucher.dto.UpdateVoucherRequest;
import com.programmers.vouchermanagement.voucher.dto.VoucherCustomerRequest;
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
        VoucherType voucherType = VoucherType.findVoucherType(request.voucherType());
        Voucher voucher = new Voucher(UUID.randomUUID(), new BigDecimal(request.discountValue()), voucherType);
        voucherRepository.save(voucher);
        return VoucherResponse.from(voucher);
    }

    public List<VoucherResponse> readAllVouchers() {
        List<Voucher> vouchers = voucherRepository.findAll();
        return vouchers.stream()
                .map(VoucherResponse::from)
                .toList();
    }

    public List<VoucherResponse> findByType(VoucherType voucherType) {
        List<Voucher> vouchers = voucherRepository.findByType(voucherType);
        return vouchers.stream()
                .map(VoucherResponse::from)
                .toList();
    }

    public List<VoucherResponse> findByCreatedAt(SearchCreatedAtRequest request) {
        validateDateRange(request);
        LocalDateTime startDateTime = request.startDate().atStartOfDay();
        LocalDateTime endDateTime = request.endDate().atTime(LocalTime.MAX);
        List<Voucher> vouchers = voucherRepository.findByCreatedAt(startDateTime, endDateTime);
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
        VoucherType voucherType = VoucherType.findVoucherType(request.voucherType());
        Voucher voucher = new Voucher(request.voucherId(), new BigDecimal(request.discountValue()), voucherType);
        Voucher updatedVoucher = voucherRepository.save(voucher);
        return VoucherResponse.from(updatedVoucher);
    }

    public void deleteById(UUID voucherId) {
        validateVoucherIdExisting(voucherId);
        voucherRepository.deleteById(voucherId);
    }

    public void grantToCustomer(VoucherCustomerRequest request) {
        validateCustomerIdExisting(request.customerId());
        VoucherResponse foundVoucher = findById(request.voucherId());
        Voucher voucher = new Voucher(
                request.voucherId(),
                foundVoucher.createdAt(),
                foundVoucher.discountValue(),
                foundVoucher.voucherType(),
                request.customerId()
        );
        voucherRepository.save(voucher);
    }

    public void releaseFromCustomer(VoucherCustomerRequest request) {
        validateCustomerIdExisting(request.customerId());
        VoucherResponse foundVoucher = findById(request.voucherId());
        Voucher voucher = new Voucher(
                foundVoucher.voucherId(),
                foundVoucher.createdAt(),
                foundVoucher.discountValue(),
                foundVoucher.voucherType(),
                null
        );
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

    private void validateCustomerIdExisting(UUID customerId) {
        if (!customerRepository.existById(customerId)) {
            throw new NoSuchElementException("There is no customer with %s".formatted(customerId));
        }
    }

    private void validateDateRange(SearchCreatedAtRequest request) {
        if (request.endDate().isBefore(request.startDate())) {
            throw new IllegalArgumentException("The end date should be at least equal to the start date.");
        }
    }
}
