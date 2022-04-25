package com.voucher.vouchermanagement.repository.blacklist;

import com.voucher.vouchermanagement.model.customer.Customer;
import com.voucher.vouchermanagement.utils.deserializer.CsvMapper;
import com.voucher.vouchermanagement.utils.deserializer.CustomerCsvMapper;
import com.voucher.vouchermanagement.utils.file.FileIOUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;


import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
@Repository
@Profile({"prod", "dev"})
public class BlacklistFileRepository implements BlacklistRepository {

    private final Resource blacklistDb;

    private final CsvMapper<Customer> csvMapper = new CustomerCsvMapper();
    private static final Logger logger = LoggerFactory.getLogger(BlacklistFileRepository.class);

    public BlacklistFileRepository(@Value("${db.file.path}") String dbDirectory, @Value("${db.file.blacklist.name}") String blacklistDbName
            , ResourceLoader resourceloader) {
        this.blacklistDb = resourceloader.getResource(dbDirectory + blacklistDbName);
    }

    @Override
    public List<Customer> findAll() {
        try {

            return FileIOUtils.readAllLine(blacklistDb.getFile())
                    .stream()
                    .map(csvMapper::deserialize)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            logger.error(e.getMessage());
            System.out.println(e.getMessage());
        }

        return Collections.emptyList();
    }
}
