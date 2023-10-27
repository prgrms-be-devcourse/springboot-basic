package team.marco.vouchermanagementsystem.service;

import java.util.List;
import java.util.Optional;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.stereotype.Service;
import team.marco.vouchermanagementsystem.model.Customer;
import team.marco.vouchermanagementsystem.repository.JdbcCustomerRepository;

@Service
public class CustomerService {
    private final JdbcCustomerRepository customerRepository;

    public CustomerService(JdbcCustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void create(String name, String email) {
        Customer customer = new Customer(name, email);

        int insert = customerRepository.create(customer);

        if (insert != 1) {
            throw new DataAccessResourceFailureException("고객을 추가하는 과정에서 오류가 발생했습니다.");
        }
    }

    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    public void update(String id, String name) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        Customer customer = customerOptional.orElseThrow();

        customer.changeName(name);

        int update = customerRepository.update(customer);

        if (update != 1) {
            throw new DataAccessResourceFailureException("고객을 추가하는 과정에서 오류가 발생했습니다.");
        }
    }

    public Customer findById(String id) {
        Optional<Customer> customerOptional = customerRepository.findById(id);

        return customerOptional.orElseThrow();
    }

    public List<Customer> findByName(String name) {
        return customerRepository.findByName(name);
    }

    public List<Customer> findByEmail(String email) {
        return customerRepository.findByEmail(email);
    }
}
