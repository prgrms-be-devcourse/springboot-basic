package org.prgms.springbootbasic.repository;

import org.prgms.springbootbasic.domain.BlacklistedCustomer;
import org.prgms.springbootbasic.util.BlackListedCustomerFileManipulator;
import org.springframework.stereotype.Repository;

import java.util.*;


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
