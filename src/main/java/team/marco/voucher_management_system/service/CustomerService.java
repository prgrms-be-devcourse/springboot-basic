package team.marco.voucher_management_system.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.jdbc.datasource.lookup.DataSourceLookupFailureException;
import org.springframework.stereotype.Service;
import team.marco.voucher_management_system.model.Customer;
import team.marco.voucher_management_system.repository.JdbcCustomerRepository;

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
            throw new DataSourceLookupFailureException("고객을 추가하는 과정에서 오류가 발생했습니다.");
        }
    }

    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    public void update(UUID id, String name) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        Customer customer = customerOptional.orElseThrow();

        customer.login();
        customer.changeName(name);

        int update = customerRepository.update(customer);

        if (update != 1) {
            throw new DataSourceLookupFailureException("이름을 변경하는 과정에서 오류가 발생했습니다.");
        }
    }

    public Customer findById(UUID id) {
        Optional<Customer> customerOptional = customerRepository.findById(id);

        return customerOptional.orElseThrow();
    }

    public void deleteById(UUID id) {
        int delete = customerRepository.deleteById(id);

        if (delete != 1) {
            throw new DataSourceLookupFailureException("고객을 삭제하는 과정에서 오류가 발생했습니다.");
        }
    }

    public List<Customer> findByName(String name) {
        return customerRepository.findByName(name);
    }

    public List<Customer> findByEmail(String email) {
        return customerRepository.findByEmail(email);
    }
}
