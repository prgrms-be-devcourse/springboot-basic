package org.prgms.springbootbasic.repository;

import org.prgms.springbootbasic.config.FileBlackListConfig;
import org.prgms.springbootbasic.domain.BlacklistedCustomer;
import org.prgms.springbootbasic.exception.CommandLineIOException;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@Repository
public class BlackListedCustomerFileRepository implements FileRepository {
    private final Map<UUID, BlacklistedCustomer> fileCache = new HashMap<>();
    private final FileBlackListConfig immutableBlackList;

    public BlackListedCustomerFileRepository(FileBlackListConfig immutableBlackList) {
        this.immutableBlackList = immutableBlackList;
        this.initFile();
    }

    @Override
    public void initFile() {
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(this.immutableBlackList.fileName()))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] customerString = line.split(",");
                fileCache.putIfAbsent(UUID.fromString(customerString[0]), new BlacklistedCustomer(UUID.fromString(customerString[0]), UUID.fromString(customerString[1])));
            }
        } catch (IOException e) {
            throw new CommandLineIOException("error occurred reading line with buffered reader", e);
        }

    }

    public List<BlacklistedCustomer> findAll() {
        return new ArrayList<>(fileCache.values());
    }
}
