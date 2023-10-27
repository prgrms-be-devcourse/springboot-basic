package org.programmers.springorder.customer.service;

import org.programmers.springorder.customer.dto.CustomerResponseDto;
import org.programmers.springorder.customer.model.Customer;
import org.programmers.springorder.customer.repository.CustomerRepository;
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

    public Customer findOwnerOfVoucher(UUID voucherId){
        Voucher voucher = voucherRepository.findById(voucherId)
                .orElseThrow(() -> new RuntimeException("찾으시는 voucher가 존재하지 않습니다."));
        if(voucher.getCustomerId() == null) {
            throw new RuntimeException("해당 voucher는 주인이 존재하지 않습니다.");
        }
        return customerRepository.findByID(voucher.getCustomerId())
                .orElseThrow(() -> new RuntimeException("해당 고객을 찾을 수 없습니다."));
    }
}
