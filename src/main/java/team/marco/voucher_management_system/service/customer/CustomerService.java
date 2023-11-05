package team.marco.voucher_management_system.service.customer;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.marco.voucher_management_system.domain.customer.Customer;
import team.marco.voucher_management_system.repository.custromer.CustomerRepository;

import java.util.List;
import java.util.UUID;

import static team.marco.voucher_management_system.error.ErrorMessage.CUSTOMER_ID_INVALID;
import static team.marco.voucher_management_system.error.ErrorMessage.INVALID_EMAIL;

@Transactional(readOnly = true)
@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> findAllCustomer() {
        return customerRepository.findAll();
    }

    public Customer findCustomerById(UUID customerId) {
        return customerRepository.findById(customerId).orElseThrow(() -> new IllegalArgumentException(CUSTOMER_ID_INVALID));
    }
    public Customer findCustomerByEmail(String email) {
        return customerRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException(INVALID_EMAIL));
    }
}
