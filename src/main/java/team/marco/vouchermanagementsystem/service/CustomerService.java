package team.marco.vouchermanagementsystem.service;

import org.springframework.stereotype.Service;
import team.marco.vouchermanagementsystem.repository.JdbcCustomerRepository;

@Service
public class CustomerService {
    private final JdbcCustomerRepository customerRepository;

    public CustomerService(JdbcCustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


}
