package org.prgms.voucheradmin.domain.customer.dao;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.prgms.voucheradmin.domain.customer.entity.Customer;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;

@Repository
public class BlacklistCustomerRepository implements CustomerRepository {
    private static final String FILE_LOCATION = "classpath:customer_blacklist.csv";

    private final ResourceLoader resourceLoader;

    public BlacklistCustomerRepository(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public List<Customer> getAll() throws IOException {
        Resource resource = resourceLoader.getResource(FILE_LOCATION);
        List<String> records = Files.readAllLines(resource.getFile().toPath());

        List<Customer> customers = new ArrayList<>();
        for(int i = 1; i < records.size(); i++) {
            String[] columns = records.get(i).split(",");
            customers.add(new Customer(Long.parseLong(columns[0]), columns[1]));
        }

        return customers;
    }
}
