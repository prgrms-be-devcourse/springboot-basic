package org.prgms.springbootbasic.util;

import org.prgms.springbootbasic.config.FileBlackListConfig;
import org.prgms.springbootbasic.domain.BlacklistedCustomer;
import org.prgms.springbootbasic.exception.CommandLineIOException;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class BlackListedCustomerFileManipulator implements FileManipulator {

    private final FileBlackListConfig immutableBlackList;

    private final Map<UUID, BlacklistedCustomer> fileCache = new HashMap<>();

    public BlackListedCustomerFileManipulator(FileBlackListConfig immutableBlackList) {
        this.immutableBlackList = immutableBlackList;
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

    public Map<UUID, BlacklistedCustomer> getFileCache() {
        return fileCache;
    }
}
