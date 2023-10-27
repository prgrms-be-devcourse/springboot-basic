package team.marco.vouchermanagementsystem.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import team.marco.vouchermanagementsystem.model.Customer;
import team.marco.vouchermanagementsystem.repository.JdbcCustomerRepository;

@Service
public class CustomerService {
    private final JdbcCustomerRepository customerRepository;

    public CustomerService(JdbcCustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public boolean create(String name, String email) {
        return false;
    }

    public List<Customer> getCustomers() {
        return null;
    }

    public boolean update(String id, String name, String email) {
        return false;
    }

    public Optional<Customer> findById(String id) {
        return Optional.empty();
    }

    public List<Customer> findByName(String name) {
        return null;
    }

    public List<Customer> findByEmail(String email) {
        return null;
    }
}
