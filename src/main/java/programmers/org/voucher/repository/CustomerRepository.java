package programmers.org.voucher.repository;

import org.springframework.stereotype.Repository;
import programmers.org.voucher.domain.Customer;

import java.util.Optional;

@Repository
public interface CustomerRepository {

    void save(Customer customer);

    void update(Customer customer);

    Optional<Customer> findById(Long id);

    Optional<Customer> findByEmail(String email);
}
