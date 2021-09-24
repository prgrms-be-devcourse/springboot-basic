package org.prgrms.kdt.customer.repository;

import org.prgrms.kdt.customer.domain.BannedCustomer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

enum BannedCustomerIndex {
    BANNED_UUID, EMAIL, NAME, DESCRIPTION
}

@Repository
public class FileCustomerRepository implements CustomerRepository {
    private static final String COMMA = ",";
    private static final String RESOURCE_PATH = "file:customer/blacklist.csv";

    @Autowired
    ApplicationContext resourceLoader;

    @Override
    public List<BannedCustomer> getBlackListCSV() throws IOException {

        Resource resource = resourceLoader.getResource(RESOURCE_PATH);

        var items = Files.readAllLines(resource.getFile().toPath());
        return items.stream()
                .map(line -> Arrays.asList(line.split(COMMA)))
                .map(list -> new BannedCustomer(
                        UUID.fromString(list.get(BannedCustomerIndex.BANNED_UUID.ordinal())),
                        list.get(BannedCustomerIndex.EMAIL.ordinal()),
                        list.get(BannedCustomerIndex.NAME.ordinal()),
                        list.get(BannedCustomerIndex.DESCRIPTION.ordinal())))
                .collect(Collectors.toList());
    }
}
