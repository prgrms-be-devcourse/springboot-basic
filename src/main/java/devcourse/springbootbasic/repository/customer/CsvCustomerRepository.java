package devcourse.springbootbasic.repository.customer;

import devcourse.springbootbasic.domain.customer.Customer;
import devcourse.springbootbasic.util.CsvFileHandler;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

@Profile("default")
@Repository
public class CsvCustomerRepository implements CustomerRepository {

    private static final String CSV_LINE_TEMPLATE = "%s,%s,%s";
    private final CsvFileHandler<Customer> csvFileHandler;
    private final Map<UUID, Customer> customerDatabase = new ConcurrentHashMap<>();

    public CsvCustomerRepository(CsvFileHandler<Customer> customerCsvFileHandler) {
        this.csvFileHandler = customerCsvFileHandler;
    }

    @Override
    public List<Customer> findAllBlacklistedCustomers() {
        return customerDatabase.values()
                .stream()
                .filter(Customer::isBlacklisted)
                .toList();
    }

    @PostConstruct
    public void init() {
        Function<String[], Customer> parser = line -> {
            UUID customerId = UUID.fromString(line[0]);
            String name = line[1];
            boolean isBlacklisted = Boolean.parseBoolean(line[2]);

            return Customer.createCustomer(customerId, name, isBlacklisted);
        };
        List<Customer> customers = csvFileHandler.readListFromCsv(parser, CSV_LINE_TEMPLATE);

        customers.forEach(customer -> customerDatabase.put(customer.getId(), customer));
    }

    @PreDestroy
    public void destroy() {
        List<Customer> customers = customerDatabase.values()
                .stream()
                .toList();
        Function<Customer, String> serializer = customer
                -> String.format(CSV_LINE_TEMPLATE,
                customer.getId(), customer.getName(), customer.isBlacklisted());

        csvFileHandler.writeListToCsv(customers, serializer);
    }
}
