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
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Profile("web")
public class NormalCustomerService {
    private static final Logger logger = LoggerFactory.getLogger(NormalCustomerService.class);

    private final CustomerRepository customerRepository;

    public NormalCustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    public CustomerResponse createCustomer(String customerName) {
        UUID customerId = ApplicationUtils.createUUID();
        CustomerStatus customerStatus = CustomerStatus.WHITE;
        UUID walletId = ApplicationUtils.createUUID();
        Customer customer = new JdbcCustomer(customerId, customerName, customerStatus, walletId);
        Customer createdCustomer = customerRepository.createCustomer(customer);

        return CustomerResponse.convertToCustomerResponse(createdCustomer);
    }

    public Optional<CustomerResponse> findByCustomerId(String customerId) {
        Optional<Customer> customer = customerRepository.findByCustomerId(customerId);

        return customer.map(CustomerResponse::convertToCustomerResponse);
    }

    public Optional<CustomerResponse> findByVoucherId(String voucherId) {
        Optional<Customer> customer = customerRepository.findByVoucherId(voucherId);

        return customer.map(CustomerResponse::convertToCustomerResponse)
                .or(Optional::empty);
    }

    public List<CustomerResponse> findAllCustomer() {
        List<Customer> customers = customerRepository.findAll();

        return customers.stream()
                .map(CustomerResponse::convertToCustomerResponse)
                .toList();
    }

    @Transactional
    public CustomerResponse updateCustomer(String customerId, CustomerStatus customerStatus) {
        Optional<CustomerResponse> response = findByCustomerId(customerId);
        Customer responseCustomer = response.map((customer) -> {
                    UUID id = customer.getId();
                    String name = customer.getName();
                    UUID walletId = customer.getWalletId();

                    return new JdbcCustomer(id, name, customerStatus, walletId);
                })
                .orElseThrow(() -> new NoExistCustomerException("업데이트하려는 유저가 존재하지 않습니다."));
        Customer updatedCustomer = customerRepository.update(responseCustomer);

        return CustomerResponse.convertToCustomerResponse(updatedCustomer);
    }

    @Transactional
    public void deleteById(String customerId) {
        Optional<Customer> customer = customerRepository.findByCustomerId(customerId);
        UUID walletId = customer.map(Customer::getWalletId)
                .orElseThrow(() -> new NoExistCustomerException("조회하려는 유저가 존재하지 않습니다."));

        customerRepository.deleteVoucherByWalletId(walletId);
        customerRepository.deleteWalletByWalletId(walletId);
        customerRepository.deleteCustomerById(customerId);
    }
}
