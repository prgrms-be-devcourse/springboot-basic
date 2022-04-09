package org.prgms.voucheradmin.domain.customer.dao;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.prgms.voucheradmin.domain.customer.entity.Customer;
import org.prgms.voucheradmin.global.properties.VoucherAdminProperties;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;
import org.springframework.util.FileCopyUtils;

import static java.nio.charset.StandardCharsets.UTF_8;

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
        Reader reader = new InputStreamReader(resource.getInputStream(), UTF_8);
        String[] records = FileCopyUtils.copyToString(reader).split("\n");

        List<Customer> customers = new ArrayList<>();
        for(int i = 0; i < records.length; i++) {
            String[] columns = records[i].split(",");
            customers.add(new Customer(Long.parseLong(columns[0]), columns[1]));
        }

        return customers;
    }
}
