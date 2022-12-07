package com.programmers.voucher.service;

import com.programmers.voucher.controller.voucher.dto.VoucherCreateRequest;
import com.programmers.voucher.controller.voucher.dto.VoucherUpdateRequest;
import com.programmers.voucher.exception.ErrorMessage;
import com.programmers.voucher.model.customer.Customer;
import com.programmers.voucher.model.voucher.Voucher;
import com.programmers.voucher.model.voucher.VoucherType;
import com.programmers.voucher.repository.customer.CustomerRepository;
import com.programmers.voucher.repository.voucher.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;
    private final CustomerRepository customerRepository;

    public VoucherService(VoucherRepository voucherRepository, CustomerRepository customerRepository) {
        this.voucherRepository = voucherRepository;
        this.customerRepository = customerRepository;
    }

    public Voucher create(VoucherCreateRequest voucherCreateRequest) {
        VoucherType voucherType = VoucherType.toVoucherType(voucherCreateRequest.voucherType());
        Voucher newVoucher = voucherType.convertToVoucher(UUID.randomUUID(), voucherCreateRequest.discountValue());
        return voucherRepository.save(newVoucher);
    }

    public List<Voucher> findAll() {
        return voucherRepository.findAll();
    }

    public List<Voucher> findAllByCustomer(String email) {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.NOT_EXIST_CUSTOMER.toString()));
        List<Voucher> vouchers = voucherRepository.findAllByEmail(email);
        vouchers.forEach(voucher -> voucher.setCustomer(customer));
        return vouchers;
    }

    public Voucher findById(UUID voucherId) {
        Customer customer = customerRepository.findByVoucher(voucherId)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.NOT_EXIST_CUSTOMER.toString()));
        Voucher voucher = voucherRepository.findById(voucherId)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.NOT_EXIST_VOUCHER.toString()));
        voucher.setCustomer(customer);
        return voucher;
    }

    public Voucher update(VoucherUpdateRequest voucherUpdateRequest) {
        UUID voucherId = voucherUpdateRequest.voucherId();
        Voucher voucher = findById(voucherId);
        voucher.setDiscountValue(voucherUpdateRequest.discountValue());
        voucherRepository.update(voucher);
        return findById(voucherId);
    }

    public Voucher assign(UUID voucherId, String email) {
        Voucher voucher = voucherRepository.findById(voucherId)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.NOT_EXIST_VOUCHER.toString()));
        customerRepository.findByEmail(email)
                .ifPresentOrElse(voucher::setCustomer,
                        () -> {
                            throw new IllegalArgumentException(ErrorMessage.NOT_EXIST_CUSTOMER.toString());
                        });
        voucherRepository.assign(voucher);
        return voucher;
    }

    public void deleteAll() {
        voucherRepository.deleteAll();
    }

    public void deleteByCustomer(String email) {
        voucherRepository.deleteByEmail(email);
    }

    public void deleteById(UUID voucherId) {
        voucherRepository.deleteById(voucherId);
    }
}
