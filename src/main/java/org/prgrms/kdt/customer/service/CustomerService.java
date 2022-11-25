package org.prgrms.kdt.customer.service;

<<<<<<< HEAD:src/main/java/org/prgrms/kdt/customer/CustomerService.java
import org.prgrms.kdt.exception.NotPresentInRepositoryException;
=======
import org.prgrms.kdt.customer.exception.NotPresentInRepositoryException;
import org.prgrms.kdt.customer.repository.CustomerRepository;
>>>>>>> 7b4babe (feat: 기존 애플리케이션에서 디렉토리 구조를 변경하고 바우처를 삭제하는 기능을 추가하다.):src/main/java/org/prgrms/kdt/customer/service/CustomerService.java
import org.prgrms.kdt.model.customer.Customer;
import org.prgrms.kdt.util.ConvertUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.getAllStoredCustomer();
    }

    public List<Customer> getAllBlacklist() {
        return customerRepository.getAllStoredCustomer().stream()
                .filter(Customer::isBlacklist)
                .toList();
    }

    public Customer findCustomerById(String customerId) {
        return customerRepository.findById(ConvertUtil.toUUID(customerId))
                .orElseThrow(() -> new NotPresentInRepositoryException("입력된 customer ID가 존재하지 않습니다."));
    }
}
