package org.programmers.springorder.customer.service;

import org.programmers.springorder.customer.dto.CustomerRequestDto;
import org.programmers.springorder.customer.dto.CustomerResponseDto;
import org.programmers.springorder.customer.model.Customer;
import org.programmers.springorder.customer.repository.CustomerRepository;
import org.programmers.springorder.exception.ErrorCode;
import org.programmers.springorder.exception.VoucherException;
import org.programmers.springorder.voucher.model.Voucher;
import org.programmers.springorder.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final VoucherRepository voucherRepository;

    public CustomerService(CustomerRepository customerRepository, VoucherRepository voucherRepository) {
        this.customerRepository = customerRepository;
        this.voucherRepository = voucherRepository;
    }

    public List<CustomerResponseDto> getBlackList() {
        return customerRepository.findAllBlackList()
                .stream()
                .map(CustomerResponseDto::of)
                .toList();
    }

    public CustomerResponseDto findOwnerOfVoucher(UUID voucherId){
        Voucher voucher = voucherRepository.findById(voucherId)
                .orElseThrow(() -> new VoucherException(ErrorCode.VOUCHER_NOT_FOUND));
        if(voucher.getCustomerId() == null) {
            throw new VoucherException(ErrorCode.VOUCHER_OWNER_NOT_EXIST);
        }
        return customerRepository.findByID(voucher.getCustomerId())
                .map(CustomerResponseDto::of)
                .orElseThrow(() -> new VoucherException(ErrorCode.VOUCHER_NOT_FOUND));
    }

    public List<CustomerResponseDto> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(CustomerResponseDto::of)
                .toList();
    }

    public void newCustomer(CustomerRequestDto requestDto) {
        Customer customer = Customer.toCustomer(
                UUID.randomUUID(),
                requestDto.name(),
                requestDto.customerType());
        customerRepository.insert(customer);
    }

    public CustomerResponseDto findCustomer(UUID customerId){
        return customerRepository.findByID(customerId)
                .map(CustomerResponseDto::of)
                .orElseThrow(() -> new VoucherException(ErrorCode.CUSTOMER_NOT_FOUND));
    }
}
