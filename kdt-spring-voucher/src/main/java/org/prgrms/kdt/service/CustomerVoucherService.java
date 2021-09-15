package org.prgrms.kdt.service;


import org.prgrms.kdt.domain.CreateCustomerVoucherDto;
import org.prgrms.kdt.domain.CustomerEntity;
import org.prgrms.kdt.domain.CustomerVoucherEntity;
import org.prgrms.kdt.domain.VoucherEntity;
import org.prgrms.kdt.repository.CustomerRepository;
import org.prgrms.kdt.repository.CustomerVoucherRepository;
import org.prgrms.kdt.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerVoucherService {

    private final CustomerVoucherRepository customerVoucherRepository;

    private final CustomerRepository customerRepository;

    private final VoucherRepository voucherRepository;

    public CustomerVoucherService(CustomerVoucherRepository customerVoucherRepository, CustomerRepository customerRepository, VoucherRepository voucherRepository) {
        this.customerVoucherRepository = customerVoucherRepository;
        this.customerRepository = customerRepository;
        this.voucherRepository = voucherRepository;
    }

    public List<CreateCustomerVoucherDto> findAll() {
        var cvList = customerVoucherRepository.findAll();
        var customerVoucherDtoList = new ArrayList<CreateCustomerVoucherDto>();
        for (var cv : cvList) {
            var customer = customerRepository.findById(cv.getCustomerId());
            var voucher = voucherRepository.findById(cv.getVoucherId());
            customerVoucherDtoList.add(CreateCustomerVoucherDto.builder().customerVoucherId(cv.getCustomerVoucherId())
                    .customerId(customer.get().getCustomerId())
                    .voucherId(voucher.get().getVoucherId())
                    .name(customer.get().getName())
                    .voucherType(voucher.get().getVoucherType())
                    .discount(voucher.get().getDiscount())
                    .allocatedAt(LocalDateTime.now())
                    .build()
            );
        }
        return customerVoucherDtoList;
    }

    public List<VoucherEntity> findNotAllocateVouchers() {
        var notVoucherList = voucherRepository.findNotAllocateList();
        return notVoucherList;
    }

//    public List<CustomerEntity> findNotAllocateCustomers() {
//        var notCustomerList = customerRepository.findNotAllocateList();
//        return notCustomerList;
//    }

    public Optional<CustomerVoucherEntity> createAllocateVoucher(UUID customerId, UUID voucherId) {
        var entity = CustomerVoucherEntity.builder().customerVoucherId(UUID.randomUUID()).customerId(customerId)
                .voucherId(voucherId)
                .createdAt(LocalDateTime.now())
                .build();
        customerVoucherRepository.insert(entity);
        return Optional.of(entity);
    }

    public void deleteCustomerVoucher(UUID customerId, UUID voucherId) {
        customerVoucherRepository.deleteById(customerId,voucherId);
    }
}
