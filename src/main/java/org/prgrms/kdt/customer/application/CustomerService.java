package org.prgrms.kdt.customer.application;

import org.prgrms.kdt.customer.domain.Customer;
import org.prgrms.kdt.customer.domain.vo.Email;
import org.prgrms.kdt.customer.dto.CustomerSignDto;
import org.prgrms.kdt.customer.repository.CustomerRepository;
import org.prgrms.kdt.exception.ErrorMessage;
import org.prgrms.kdt.exception.NotUpdateException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    public Customer createCustomer(CustomerSignDto dto) {
        return customerRepository.insert(dto);
    }

    @Transactional
    public Email changeRole(Email email) {
        Optional<Customer> customer = customerRepository.findByEmail(email);

        boolean result = true;
        if (customer.isPresent()) {
            Customer updateCustomer = customer.get().isBlackList();
            result = customerRepository.updateRoleByEmail(updateCustomer);
        }

        if (result) {
            return email;
        }
        throw new NotUpdateException(ErrorMessage.CAM_NOT_UPDATE_ROLE);
    }

    @Transactional(readOnly = true)
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }
}
