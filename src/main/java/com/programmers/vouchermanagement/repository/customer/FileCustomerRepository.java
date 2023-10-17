package com.programmers.vouchermanagement.repository.customer;

import com.programmers.vouchermanagement.common.ErrorMessage;
import com.programmers.vouchermanagement.domain.customer.Customer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Repository
public class FileCustomerRepository implements CustomerRepository {

    private final String csvSeparator;
    private final String blacklistFilePath;

    public FileCustomerRepository(@Value("${csv.file.customer.blacklist_path}") String blacklistFilePath, @Value("${csv.separator}") String csvSeparator) {
        this.blacklistFilePath = blacklistFilePath;
        this.csvSeparator = csvSeparator;
    }

    @Override
    public List<Customer> findAllBannedCustomers() {
        return readCustomerFile();
    }

    private List<Customer> readCustomerFile() {
        String line;
        List<Customer> bannedCustomers = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(blacklistFilePath))) {
            while ((line = br.readLine()) != null) {
                String[] strings = line.split(csvSeparator);
                Customer customer = new Customer(
                        UUID.fromString(strings[0]),
                        strings[1],
                        LocalDateTime.parse(strings[2]),
                        Boolean.parseBoolean(strings[3])
                );
                bannedCustomers.add(customer);
            }
        } catch (FileNotFoundException e) {
            throw new NoSuchElementException(ErrorMessage.FILE_NOT_FOUND_MESSAGE.getMessage());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        return bannedCustomers;
    }

}
