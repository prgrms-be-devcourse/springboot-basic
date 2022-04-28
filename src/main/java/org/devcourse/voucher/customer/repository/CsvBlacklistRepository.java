package org.devcourse.voucher.customer.repository;

import org.devcourse.voucher.configuration.FilePathProperties;
import org.devcourse.voucher.customer.Customer;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CsvBlacklistRepository implements BlacklistRepository {

    private final FilePathProperties filePath;

    public CsvBlacklistRepository(FilePathProperties filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<Customer> findAll() {
        String path = filePath.getBlacklist();
        return null;
    }
}

