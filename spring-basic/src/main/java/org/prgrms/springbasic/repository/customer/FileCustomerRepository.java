package org.prgrms.springbasic.repository.customer;

import lombok.extern.slf4j.Slf4j;
import org.prgrms.springbasic.domain.customer.Customer;
import org.prgrms.springbasic.utils.io.converter.FileManager;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.prgrms.springbasic.utils.enumm.path.FilePath.BLACK_CUSTOMER_FILE_PATH;

@Slf4j
@Profile("dev")
@Repository
public class FileCustomerRepository implements CustomerRepository {

    private final FileManager<Customer> fileManager = new FileManager<>(BLACK_CUSTOMER_FILE_PATH.getPath());

    @Override
    public Customer save(Customer customer) {
        fileManager.toFile(customer);

        return customer;
    }

    @Override
    public Optional<Customer> findByCustomerId(UUID customerId) {
        return Optional.empty();
    }

    @Override
    public Optional<Customer> findByVoucherId(UUID voucherId) {
        //JDBC만 구현
        return Optional.empty();
    }

    @Override
    public List<Customer> findCustomers() {
        return fileManager.objectToList(Customer.class);
    }

    @Override
    public int countCustomers() {
        return fileManager.countLines();
    }

    @Override
    public Customer update(Customer customer) {
        //JDBC만 구현
        return null;
    }

    @Override
    public void deleteByCustomerId(UUID customerId) {
        //JDBC만 구현
    }

    @Override
    public void deleteCustomers() {
        fileManager.clear();
    }
}
