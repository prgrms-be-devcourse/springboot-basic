package com.prgrms.kdt.springbootbasic.service;

import com.prgrms.kdt.springbootbasic.entity.Customer;
import com.prgrms.kdt.springbootbasic.exception.JdbcQueryFail;
import com.prgrms.kdt.springbootbasic.exception.NoSuchResource;
import com.prgrms.kdt.springbootbasic.exception.ResourceDuplication;
import com.prgrms.kdt.springbootbasic.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {
//    private final Logger logger = LoggerFactory.getLogger(CustomerService.class);
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer saveCustomer(String name, String email){
        Customer customer = new Customer(UUID.randomUUID(), name, email);
        if (checkDuplication(customer)){
            throw new ResourceDuplication("동일한 Customer가 이미 존재합니다");
        }

        Optional<Customer> saveResult =  customerRepository.saveCustomer(customer);
        if (saveResult.isEmpty())
            throw new JdbcQueryFail("Customer 저장이 실패하였습니다");

        return saveResult.get();
    }

    public boolean checkDuplication(Customer customer){
        return customerRepository.findCustomerByEmail(customer.getEmail()).isPresent();
    }

    public Customer findCustomerById(UUID customerId){
        Optional<Customer> findResult = customerRepository.findCustomerById(customerId);
        if (findResult.isEmpty())
            throw new NoSuchResource("일치하는 Customer가 없습니다");

        return findResult.get();
    }

    public List<Customer> getAllCustomers(){return customerRepository.getAllCustomers();}

    public Customer updateCustomer(Customer customer){
        Customer foundCustomer = findCustomerById(customer.getCustomerId());

        var updateResult = customerRepository.updateCustomer(customer);

        if (updateResult.isEmpty())
            throw new JdbcQueryFail("Customer Update가 실패하였습니다");

        return updateResult.get();
    }

    public boolean deleteCustomer(Customer customer){
        try {
            Customer foundCustomer = findCustomerById(customer.getCustomerId());
            return customerRepository.deleteCustomer(foundCustomer);
        }catch (NoSuchResource e){
            return true;
        }
    }
}
