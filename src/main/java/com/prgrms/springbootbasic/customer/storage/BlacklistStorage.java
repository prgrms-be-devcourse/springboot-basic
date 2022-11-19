package com.prgrms.springbootbasic.customer.storage;

import com.prgrms.springbootbasic.common.exception.FileFormatException;
import com.prgrms.springbootbasic.common.exception.FileIOException;
import com.prgrms.springbootbasic.common.exception.FileNotExistException;
import com.prgrms.springbootbasic.customer.domain.Customer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;

import static com.prgrms.springbootbasic.common.exception.ExceptionMessage.*;

@Repository
public class BlacklistStorage {

    private static final String DELIMITER = ",";
    private static final int USER_ID_COLUMN_INDEX = 0;
    private static final int NAME_COLUMN_INDEX = 1;
    private static final int VOUCHER_COLUMN_SIZE = 2;

    private final String CLASSPATH_BLACKLIST;
    private final ResourceLoader resourceLoader;

    public BlacklistStorage(ResourceLoader resourceLoader, @Value("${classpath.customer-blacklist}") String classpathBlacklist) {
        this.resourceLoader = resourceLoader;
        this.CLASSPATH_BLACKLIST = classpathBlacklist;
    }

    public List<Customer> findAll() {
        try {
            File file = openFile();
            return readAll(file);
        } catch (IOException e) {
            String errorMessage = FATAL_FILE_IO_EXCEPTION_MESSAGE + e.getMessage();
            throw new FileIOException(errorMessage);
        }
    }

    private File openFile() {
        Resource resource = resourceLoader.getResource(CLASSPATH_BLACKLIST);
        try {
            return resource.getFile();
        } catch (FileNotFoundException e) {
            throw new FileNotExistException(FILE_NOT_EXIST_EXCEPTION_MESSAGE + e.getMessage());
        } catch (IOException e) {
            throw new FileIOException(FATAL_FILE_IO_EXCEPTION_MESSAGE + e.getMessage());
        }
    }

    private List<Customer> readAll(File file) throws IOException {
        try (Stream<String> lineStream = Files.lines(file.toPath())) {
            return lineStream.skip(1)
                    .map(this::mapToUser)
                    .collect(Collectors.toList());
        }
    }

    private Customer mapToUser(String line) {
        List<String> columns = List.of(line.split(DELIMITER));

        validateSize(columns);

        UUID id = UUID.fromString(columns.get(USER_ID_COLUMN_INDEX).trim());
        String name = columns.get(NAME_COLUMN_INDEX).trim();
        return new Customer(id, name);
    }

    private void validateSize(List<String> columns) {
        if (columns.size() != VOUCHER_COLUMN_SIZE) {
            throw new FileFormatException(FILE_NUMBER_OF_COLUMN_NOT_MATCHED);
        }
    }
}
