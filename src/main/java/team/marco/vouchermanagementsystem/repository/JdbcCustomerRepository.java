package team.marco.vouchermanagementsystem.repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import team.marco.vouchermanagementsystem.model.Customer;

@Repository
public class JdbcCustomerRepository {
    public int create(Customer customer) {
        return 0;
    }

    public List<Customer> findAll() {
        return Collections.emptyList();
    }

    public int update(String id, String name) {
        return 0;
    }

    public Optional<Customer> findById(String id) {
        return Optional.empty();
    }

    public List<Customer> findByName(String name) {
        return Collections.emptyList();
    }

    public List<Customer> findByEmail(String email) {
        return Collections.emptyList();
    }
}
