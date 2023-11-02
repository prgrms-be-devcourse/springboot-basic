package devcourse.springbootbasic.service;

import devcourse.springbootbasic.domain.customer.Customer;
import devcourse.springbootbasic.dto.customer.CustomerCreateRequest;
import devcourse.springbootbasic.dto.customer.CustomerFindResponse;
import devcourse.springbootbasic.dto.customer.CustomerUpdateBlacklistRequest;
import devcourse.springbootbasic.exception.CustomerErrorMessage;
import devcourse.springbootbasic.exception.CustomerException;
import devcourse.springbootbasic.repository.customer.CustomerRepository;
import devcourse.springbootbasic.util.UUIDUtil;
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
    public Customer createCustomer(CustomerCreateRequest customerCreateRequest) {
        return customerRepository.save(customerCreateRequest.toEntity(UUIDUtil.generateRandomUUID()));
    }

    @Transactional
    public Customer updateBlacklistStatus(CustomerUpdateBlacklistRequest customerUpdateBlacklistRequest) {
        Customer findCustomer = this.getByCustomerId(customerUpdateBlacklistRequest.getId());

        if (customerUpdateBlacklistRequest.isBlacklisted()) return persist(findCustomer.applyBlacklist());
        else return persist(findCustomer.releaseBlacklist());
    }

    public Customer getByCustomerId(UUID customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> CustomerException.of(CustomerErrorMessage.NOT_FOUND));
    }

    private Customer persist(Customer customer) {
        if (!customerRepository.update(customer)) {
            throw CustomerException.of(CustomerErrorMessage.NOT_FOUND);
        }

        return customer;
    }
}
