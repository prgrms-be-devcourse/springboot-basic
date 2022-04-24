package org.prgrms.voucherapp.engine.customer.service;

import org.prgrms.voucherapp.engine.customer.entity.Customer;
import org.prgrms.voucherapp.engine.customer.repository.CustomerRepository;
import org.prgrms.voucherapp.engine.wallet.repository.WalletRepository;
import org.prgrms.voucherapp.engine.wallet.service.WalletService;
import org.prgrms.voucherapp.exception.NullCustomerException;

import org.prgrms.voucherapp.global.enums.CustomerStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final WalletRepository walletRepository;
    private final static Logger logger = LoggerFactory.getLogger(CustomerService.class);

    public CustomerService(CustomerRepository customerRepository, WalletRepository walletRepository) {
        this.customerRepository = customerRepository;
        this.walletRepository = walletRepository;
    }

    public Customer getCustomer(UUID customerId) {
        return customerRepository
                .findById(customerId)
                .orElseThrow(() -> new NullCustomerException(MessageFormat.format("{0}는 존재하지 않는 고객 id입니다.", customerId)));
    }

    @Transactional
    public Customer createCustomer(UUID uuid, String name, String email, Optional<CustomerStatus> status) {
        while (customerRepository.findById(uuid).isPresent()) {
            uuid = UUID.randomUUID();
        }
        Customer newCustomer = status.isEmpty() ? new Customer(uuid, name, email) : new Customer(uuid, name, email, status.get().toString());
        return customerRepository.insert(newCustomer);
    }

    public String getCustomerListByStr() {
        StringBuilder sb = new StringBuilder();
        for (Customer customer : customerRepository.findAll()) {
            sb.append(customer.toString()).append("\n");
        }
        if (sb.isEmpty()) return "Customer Repository is empty.";
        sb.deleteCharAt(sb.lastIndexOf("\n"));
        return sb.toString();
    }

    @Transactional
    //Q. Service Layer에서 단위테스트를 지정할 때 서비스 단계에서 예외처리를 하지 않았기 때문에 예외테스트를 진행하기 어려웠다.
    // Controller에서 예외처리를 한 경우 Service Layer에서도 단위테스트를 만족하기 위해 예외처리를 해야하는가?
    public void removeCustomer(Customer customer) {
        customerRepository.deleteById(customer.getCustomerId());
        walletRepository.deleteByCustomerId(customer.getCustomerId());
        logger.info("--- 삭제된 고객 정보 --- \n%s".formatted(customer));
    }

    @Transactional
    public void updateCustomer(Customer customer, String name, Optional<CustomerStatus> status){
        Customer oldCustomer = new Customer(customer.getCustomerId(), customer.getName(), customer.getEmail(), customer.getStatus());
        customer.changeName(name);
        customer.changeStatus(status.isEmpty() ? null : status.get().toString());
        customerRepository.update(customer);
        logger.info("--- 수정된 고객 정보 ---\n변경 전 : %s\n변경 후 : %s".formatted(oldCustomer, customer));
    }
}
