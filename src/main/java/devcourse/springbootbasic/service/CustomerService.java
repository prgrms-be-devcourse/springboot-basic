package devcourse.springbootbasic.service;

import devcourse.springbootbasic.domain.customer.Customer;
import devcourse.springbootbasic.dto.customer.CustomerCreateRequest;
import devcourse.springbootbasic.dto.customer.CustomerFindResponse;
import devcourse.springbootbasic.exception.CustomerErrorMessage;
import devcourse.springbootbasic.exception.CustomerException;
import devcourse.springbootbasic.repository.customer.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

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
    public Customer updateBlacklistStatus(UUID customerId, boolean isBlacklisted) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> CustomerException.of(CustomerErrorMessage.NOT_FOUND))
                .updateBlacklistStatus(isBlacklisted);

        if (customerRepository.update(customer) == 0) {
            throw CustomerException.of(CustomerErrorMessage.NOT_FOUND);
        }

        return customer;
    }
}
