package com.prgrms.vouchermanager.repository.customer;

import com.prgrms.vouchermanager.domain.customer.Customer;
import com.prgrms.vouchermanager.io.FileIO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public class BlacklistFileRepository implements BlacklistRepository {
    private final Map<UUID, Customer> customerMap = new HashMap<>();
    @Autowired
    public BlacklistFileRepository(@Value("${csv.blacklist}") String fileName) {
        FileIO fileIO = new FileIO(fileName);
        fileIO.readCustomerFile(customerMap);
    }

    @Override
    public List<Customer> findBlacklist() {
        return customerMap
                .values()
                .stream().toList();
    }
}
