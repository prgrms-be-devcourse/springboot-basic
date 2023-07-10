package kr.co.programmers.springbootbasic.customer.service;

import kr.co.programmers.springbootbasic.customer.domain.Customer;
import kr.co.programmers.springbootbasic.customer.domain.CustomerStatus;
import kr.co.programmers.springbootbasic.customer.domain.impl.JdbcCustomer;
import kr.co.programmers.springbootbasic.customer.dto.CustomerResponse;
import kr.co.programmers.springbootbasic.customer.exception.NoExistCustomerException;
import kr.co.programmers.springbootbasic.customer.repository.CustomerRepository;
import kr.co.programmers.springbootbasic.util.ApplicationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Profile("web")
public class JdbcCustomerService {
    private static final Logger logger = LoggerFactory.getLogger(JdbcCustomerService.class);

    private final CustomerRepository customerRepository;

    public JdbcCustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    public CustomerResponse createCustomer(String customerName) {
        UUID customerId = ApplicationUtils.createUUID();
        CustomerStatus customerStatus = CustomerStatus.WHITE;
        UUID walletId = ApplicationUtils.createUUID();
        Customer customer = new JdbcCustomer(customerId, customerName, customerStatus, walletId);
        Customer createdCustomer = customerRepository.createCustomer(customer);

        return ApplicationUtils.convertToCustomerResponse(createdCustomer);
    }

    public Optional<CustomerResponse> findByCustomerId(String customerId) {
        Optional<Customer> customer = customerRepository.findByCustomerId(customerId);

        return customer.map(ApplicationUtils::convertToCustomerResponse);
    }

    public Optional<CustomerResponse> findByVoucherId(String voucherId) {
        Optional<Customer> customer = customerRepository.findByVoucherId(voucherId);

        if (customer.isPresent()) {
            CustomerResponse responseDto = ApplicationUtils.convertToCustomerResponse(customer.get());

            return Optional.of(responseDto);
        }
        return Optional.empty();
    }

    public List<CustomerResponse> findAllCustomer() {
        List<Customer> customers = customerRepository.findAll();

        return customers.stream()
                .map(ApplicationUtils::convertToCustomerResponse)
                .toList();
    }

    @Transactional
    public CustomerResponse updateCustomer(String customerId, CustomerStatus customerStatus) {

        Optional<CustomerResponse> foundCustomer = findByCustomerId(customerId);

        if (foundCustomer.isEmpty()) {
            throw new NoExistCustomerException("업데이트하려는 유저가 존재하지 않습니다.");
        }

        CustomerResponse customerResponse = foundCustomer.get();
        UUID foundCustomerUUID = customerResponse.getId();
        String customerName = customerResponse.getName();
        UUID walletId = customerResponse.getWalletId();
        Customer customerForUpdate = new JdbcCustomer(foundCustomerUUID, customerName, customerStatus, walletId);
        Customer updatedCustomer = customerRepository.update(customerForUpdate);

        return ApplicationUtils.convertToCustomerResponse(updatedCustomer);
    }

    @Transactional
    public void deleteById(String customerId) {
        customerRepository.deleteById(customerId);
    }

}
