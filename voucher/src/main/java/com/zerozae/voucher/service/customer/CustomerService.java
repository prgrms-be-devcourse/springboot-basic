package com.zerozae.voucher.service.customer;

import com.zerozae.voucher.domain.customer.Customer;
import com.zerozae.voucher.domain.customer.CustomerType;
import com.zerozae.voucher.dto.customer.CustomerRequest;
import com.zerozae.voucher.dto.customer.CustomerResponse;
import com.zerozae.voucher.exception.ExceptionHandler;
import com.zerozae.voucher.repository.customer.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.zerozae.voucher.common.message.MessageConverter.getMessage;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerResponse createCustomer(CustomerRequest customerRequest){
        try{
            validateDuplicateCustomer(customerRequest);
            Customer customer = new Customer(customerRequest.getCustomerName(), customerRequest.getCustomerType());
            customerRepository.save(customer);

            return CustomerResponse.toDto(customer);
        }catch (ExceptionHandler e){
            throw ExceptionHandler.err(e.getMessage());
        }
    }
    public List<CustomerResponse> findAllCustomers(){
        return customerRepository.findAll()
                .stream()
                .map(CustomerResponse::toDto)
                .toList();
    }
    public List<CustomerResponse> findAllBlacklistCustomer() {
        return customerRepository.findAll()
                .stream()
                .filter(customer -> customer.getCustomerType().equals(CustomerType.BLACKLIST))
                .map(CustomerResponse::toDto)
                .toList();
    }
    private void validateDuplicateCustomer(CustomerRequest customerRequest) {
        Optional<Customer> findCustomer = customerRepository.findAll()
                .stream()
                .filter(customer -> customer.getCustomerName().equals(customerRequest.getCustomerName()))
                .findAny();
        if(findCustomer.isPresent()){
            throw ExceptionHandler.err(getMessage("DUPLICATE_CUSTOMER_ERROR.MSG"));
        }
    }
}
