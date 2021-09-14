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
    BANNED_UUID(0), EMAIL(1), NAME(2), DESCRIPTION(3);

    private final int index;

    BannedCustomerIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}

@Repository
public class FileCustomerRepository implements CustomerRepository {

    @Autowired
    ApplicationContext resourceLoader;

    @Override
    public List<BannedCustomer> getBlackListCSV() throws IOException {

        Resource resource = resourceLoader.getResource("file:customer/blacklist.csv");

        var items = Files.readAllLines(resource.getFile().toPath());
        return items.stream()
                .map(line -> Arrays.asList(line.split(",")))
                .map(list -> new BannedCustomer(
                        UUID.fromString(list.get(BannedCustomerIndex.BANNED_UUID.getIndex())),
                        list.get(BannedCustomerIndex.EMAIL.getIndex()),
                        list.get(BannedCustomerIndex.NAME.getIndex()),
                        list.get(BannedCustomerIndex.DESCRIPTION.getIndex())))
                .collect(Collectors.toList());
    }
}
