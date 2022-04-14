package org.prgrms.springbasic.repository.customer;

import lombok.extern.slf4j.Slf4j;
import org.prgrms.springbasic.domain.customer.Customer;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.*;

import static org.prgrms.springbasic.utils.enumm.FilePath.BLACK_CUSTOMER_FILE_PATH;

@Repository
@Slf4j
public class BlackCustomerRepository implements CustomerRepository {

    private static final String path = BLACK_CUSTOMER_FILE_PATH.getPath();
    private final File file = new File(path);
    private final BufferedWriter output = new BufferedWriter(new FileWriter(file));
    private final BufferedReader input = new BufferedReader(new FileReader(file));
    private final Map<UUID, Customer> storage = new LinkedHashMap<>();
    private final List<Customer> customers = new ArrayList<>();

    private BlackCustomerRepository() throws IOException {
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        return Optional.ofNullable(storage.get(customerId));
    }

    @Override
    public void save(Customer customer) {
        String template = customer.toString();
        storage.put(customer.getCustomerId(), customer);

        try {
            output.write(template);
            output.flush();
        } catch (IOException e) {
            log.error("IOException: {}", e.getMessage());
        }
    }

    @Override
    public List<Customer> findAll() {
        String sLine;

        try {
            while ((sLine = input.readLine()) != null) {
                fillCustomers(sLine);
            }
        } catch (IOException e) {
            log.error("IOException: {}", e.getMessage());
        }

        return customers;
    }

    @Override
    public int countStorageSize() {
        return storage.size();
    }

    @Override
    public void clear() {
        file.delete();
        storage.clear();
        customers.clear();
    }

    private void fillCustomers(String fileCustomer) {
        String customerId = fileCustomer.split("/")[0]; //customerId, customerType, name 순서
        customers.add(storage.get(UUID.fromString(customerId)));
    }
}
