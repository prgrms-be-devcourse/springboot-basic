package com.prgms.VoucherApp.domain.customer.model;

import com.prgms.VoucherApp.domain.customer.dto.CustomerCreateRequest;
import com.prgms.VoucherApp.domain.customer.dto.CustomerResponse;
import com.prgms.VoucherApp.domain.customer.dto.CustomerUpdateRequest;
import com.prgms.VoucherApp.domain.customer.dto.CustomersResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class CustomerService {

    private final BlackListFileDao blackListStorage;
    private final CustomerDao customerDao;

    public CustomerService(BlackListFileDao blackListStorage, CustomerDao customerDao) {
        this.blackListStorage = blackListStorage;
        this.customerDao = customerDao;
    }

    @Transactional
    public CustomerResponse save(CustomerCreateRequest customerCreateRequest) {
        Customer customer = new Customer(UUID.randomUUID(), customerCreateRequest.customerStatus());

        customerDao.save(customer);

        return new CustomerResponse(customer);
    }

    public CustomersResponse findAll() {
        List<Customer> findCustomers = customerDao.findAll();

        List<CustomerResponse> convertCustomerResponse = findCustomers.stream()
                .map(CustomerResponse::new)
                .toList();

        return new CustomersResponse(convertCustomerResponse);
    }

    public CustomerResponse findOne(UUID id) {
        return customerDao.findById(id)
                .map(CustomerResponse::new)
                .orElseThrow(() -> new RuntimeException("고객이 조회되지 않았습니다."));
    }

    public CustomersResponse findByStatus(CustomerCreateRequest customerCreateRequest) {
        List<Customer> findCustomers = customerDao.findByCustomerStatus(customerCreateRequest.customerStatus());

        List<CustomerResponse> convertCustomerResponse = findCustomers.stream()
                .map(CustomerResponse::new)
                .toList();

        return new CustomersResponse(convertCustomerResponse);
    }

    @Transactional
    public void update(CustomerUpdateRequest customerUpdateRequest) {
        customerDao.updateStatus(customerUpdateRequest);
    }

    @Transactional
    public void deleteById(UUID id) {
        customerDao.deleteById(id);
    }

    public CustomersResponse readBlackLists() {
        List<Customer> findBlacklists = blackListStorage.findBlacklist();

        List<CustomerResponse> convertCustomerResponse = findBlacklists.stream()
                .map(CustomerResponse::new)
                .toList();

        return new CustomersResponse(convertCustomerResponse);
    }
}
