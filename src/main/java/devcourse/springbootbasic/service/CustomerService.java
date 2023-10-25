package devcourse.springbootbasic.service;

import devcourse.springbootbasic.domain.customer.Customer;
import devcourse.springbootbasic.dto.customer.CustomerCreateRequest;
import devcourse.springbootbasic.dto.customer.CustomerFindResponse;
import devcourse.springbootbasic.dto.customer.CustomerUpdateBlacklistRequest;
import devcourse.springbootbasic.exception.CustomerErrorMessage;
import devcourse.springbootbasic.exception.CustomerException;
import devcourse.springbootbasic.repository.customer.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public List<CustomerFindResponse> findAllBlacklistedCustomers() {
        return customerRepository.findAllBlacklistedCustomers()
                .stream()
                .map(CustomerFindResponse::new)
                .toList();
    }

    @Transactional
    public Customer save(CustomerCreateRequest customerCreateRequest) {
        return customerRepository.save(customerCreateRequest.toEntity());
    }

    @Transactional
    public Customer updateBlacklistStatus(CustomerUpdateBlacklistRequest customerUpdateBlacklistRequest) {
        Customer customer = customerRepository.findById(customerUpdateBlacklistRequest.getId())
                .orElseThrow(() -> CustomerException.of(CustomerErrorMessage.NOT_FOUND))
                .updateBlacklistStatus(customerUpdateBlacklistRequest.isBlacklisted());

        if (customerRepository.update(customer) == 0) {
            throw CustomerException.of(CustomerErrorMessage.NOT_FOUND);
        }

        return customer;
    }
}
