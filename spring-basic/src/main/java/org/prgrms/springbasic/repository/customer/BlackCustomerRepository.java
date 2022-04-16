package org.prgrms.springbasic.repository.customer;

import lombok.extern.slf4j.Slf4j;
import org.prgrms.springbasic.domain.customer.Customer;
import org.prgrms.springbasic.utils.converter.FileManager;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.prgrms.springbasic.utils.enumm.FilePath.BLACK_CUSTOMER_FILE_PATH;

@Repository
@Slf4j
public class BlackCustomerRepository implements CustomerRepository {

    private final FileManager<Customer> fileManager = new FileManager<>(BLACK_CUSTOMER_FILE_PATH.getPath());

    @Override
    public void save(Customer customer) {
        fileManager.toFile(customer);
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        return Optional.empty();
    }

    @Override
    public List<Customer> findAll() {
        return fileManager.objectToList(Customer.class);
    }

    @Override
    public int countStorageSize() {
        return fileManager.countLines();
    }

    @Override
    public void clear() {
        fileManager.clear();
    }
}
