package org.prgms.springbootbasic.repository.customer;

import org.prgms.springbootbasic.domain.customer.BlacklistedCustomer;
import org.prgms.springbootbasic.util.BlackListedCustomerFileManipulator;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Repository
public class BlackListedCustomerFileRepository {
    private final Map<UUID, BlacklistedCustomer> fileCache;

    public BlackListedCustomerFileRepository(BlackListedCustomerFileManipulator blackListedCustomerFileManipulator) {
        blackListedCustomerFileManipulator.initFile();
        fileCache = blackListedCustomerFileManipulator.getFileCache();
    }

    public List<BlacklistedCustomer> findAll() {
        return new ArrayList<>(fileCache.values());
    }
}
