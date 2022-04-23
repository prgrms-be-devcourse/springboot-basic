package org.prgms.voucheradmin.domain.customer.service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.prgms.voucheradmin.domain.customer.dao.blacklist.BlackListRepository;
import org.prgms.voucheradmin.domain.customer.dao.customer.CustomerRepository;
import org.prgms.voucheradmin.domain.customer.dto.BlacklistCustomerDto;
import org.prgms.voucheradmin.domain.customer.dto.CustomerCreateReqDto;
import org.prgms.voucheradmin.domain.customer.dto.CustomerUpdateReqDto;
import org.prgms.voucheradmin.domain.customer.entity.Customer;
import org.prgms.voucheradmin.global.exception.CustomerNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 고객 조회와 관련된 로직을 담당하는 클래스입니다.
 */
@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final BlackListRepository blackListRepository;

    public CustomerService(CustomerRepository customerRepository, BlackListRepository blackListRepository) {
        this.customerRepository = customerRepository;
        this.blackListRepository = blackListRepository;
    }

    /**
     * 고객을 생성하는 메서드입니다.
     */
    @Transactional
    public Customer createCustomer(CustomerCreateReqDto customerCreateReqDto) {
        return customerRepository.create(customerCreateReqDto.toEntity());
    }

    /**
     * 고객을 목록을 조회하는 메서드입니다.
     */
    @Transactional(readOnly = true)
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Customer getCustomer(UUID customerId) {
        return customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException(customerId));
    }

    /**
     * id를 이용하여 고객의 이름을 수정하는 메서드입니다.
     */
    @Transactional
    public Customer updateCustomer(UUID customerId, CustomerUpdateReqDto customerUpdateReqDto) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException(customerId));

        customer.changeName(customerUpdateReqDto.getName());
        return customerRepository.update(customer);
    }

    /**
     * id를 이용해 고객을 제거하는 메서드입니다.
     */
    @Transactional
    public void deleteCustomer(UUID customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException(customerId));
        customerRepository.delete(customer);
    }

    /**
     * 블랙리스트를 반환 하는 메서드입니다.
     */
    public List<BlacklistCustomerDto> getBlackList() throws IOException {
        List<BlacklistCustomerDto> blackListedCustomers= blackListRepository.getAll().stream()
                .map(customer -> new BlacklistCustomerDto(customer.getId(), customer.getName()))
                .collect(Collectors.toList());
        return blackListedCustomers;
    }
}
