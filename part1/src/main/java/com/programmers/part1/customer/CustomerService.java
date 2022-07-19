package com.programmers.part1.customer;

import com.programmers.part1.domain.VoucherWallet;
import com.programmers.part1.domain.customer.Customer;
import com.programmers.part1.customer.repository.CustomerRepository;
import com.programmers.part1.domain.customer.RequestCustomerDto;
import com.programmers.part1.exception.ListEmptyException;
import com.programmers.part1.exception.ResponseEmptyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {

    private final CustomerRepository<UUID, Customer> customerRepository;

    public CustomerService(CustomerRepository<UUID, Customer> customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer createCustomer(String name, String email) {
        Customer newCustomer = Customer.builder()
                .customerId(UUID.randomUUID())
                .name(name)
                .email(email)
                .createdAt(LocalDateTime.now())
                .build();
        return customerRepository.save(newCustomer);
    }

    public Customer getCustomer(UUID customerId) {
        Optional<Customer> maybeCustomer = customerRepository.findById(customerId);
        return maybeCustomer.orElseThrow(RuntimeException::new);
    }

    public List<Customer> getAllCustomer() {
        List<Customer> customerList = customerRepository.findAllCustomer();

        if (customerList.size() == 0)
            throw new ListEmptyException("조회 결과가 없습니다.");

        return customerList;
    }

    public List<Customer> getCustomerAtVoucherWallet(UUID voucherID) {
        List<Customer> customerList = customerRepository.findCustomerByVoucherId(voucherID);

        if (customerList.size() == 0)
            throw new ListEmptyException("조회 결과가 없습니다.");

        return customerList;
    }

    @Transactional
    public Customer updateCustomer(UUID customerId, RequestCustomerDto requestCustomerDto) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(()->new ResponseEmptyException("고객이 없습니다."));
        customer.changeName(requestCustomerDto.getName());
        customer.changeEmail(requestCustomerDto.getEmail());
        return customerRepository.update(customer);
    }

    public void deleteCustomer(UUID customerId) {
        customerRepository.deleteById(customerId);
    }

    public VoucherWallet createVoucherWallet(UUID customerId, UUID voucherId) {
        return customerRepository.insertVoucherWallet(new VoucherWallet(UUID.randomUUID(), customerId, voucherId));
    }

    public List<Customer> getCustomersWithVoucher(UUID voucherId) {
        List<Customer> customerList = customerRepository.findCustomerByVoucherId(voucherId);

        if (customerList.size() == 0)
            throw new ListEmptyException("조회 결과가 없습니다.");
        return customerList;
    }

    public void deleteVoucherWalletWithIds(UUID customerId, UUID voucherId) {
        customerRepository.deleteVoucherWalletByCustomerAndVoucherId(customerId, voucherId);
    }
}
