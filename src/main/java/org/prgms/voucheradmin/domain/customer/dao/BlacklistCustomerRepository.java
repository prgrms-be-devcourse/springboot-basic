package org.prgms.voucheradmin.domain.customer.dao;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.prgms.voucheradmin.domain.customer.entity.Customer;
import org.prgms.voucheradmin.global.properties.VoucherAdminProperties;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;

@Repository
public class BlacklistCustomerRepository implements CustomerRepository {
    private final VoucherAdminProperties voucherAdminProperties;
    private final ResourceLoader resourceLoader;

    public BlacklistCustomerRepository(VoucherAdminProperties voucherAdminProperties, ResourceLoader resourceLoader) {
        this.voucherAdminProperties = voucherAdminProperties;
        this.resourceLoader = resourceLoader;
    }

    @Override
    public List<Customer> getAll() throws IOException {
        Resource resource = resourceLoader.getResource(voucherAdminProperties.getBlacklistFilePath());
        List<String> records = Files.readAllLines(resource.getFile().toPath());

        List<Customer> customers = new ArrayList<>();
        for(int i = 1; i < records.size(); i++) {
            String[] columns = records.get(i).split(",");
            customers.add(new Customer(Long.parseLong(columns[0]), columns[1]));
        }

        return customers;
    }
}
