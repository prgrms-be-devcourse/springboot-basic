package com.programmers.voucher.service.customer;

import com.programmers.voucher.entity.customer.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BlacklistCustomerReader implements CustomerInitializer {

    private final Path file;
    private List<Customer> list = new LinkedList<>();
    private static final Logger log = LoggerFactory.getLogger(BlacklistCustomerReader.class);

    public BlacklistCustomerReader(
            @Value("${voucher.file.blacklist.location}") String directory,
            @Value("${voucher.file.blacklist.filename}") String filename
    ) {
        log.info("Using directory '{}' as blacklist location.", directory);
        log.info("Using filename '{}' as blacklist file.", filename);

        Path fileDirectory = Paths.get(directory);
        if(!Files.exists(fileDirectory)) {
            try {
                Files.createDirectory(fileDirectory);
                log.debug("Created blacklist directory '{}'", fileDirectory.toString());
            } catch (IOException ex) {
                log.error("Failed to create blacklist directory at '{}'", fileDirectory.toString());
                System.exit(1);
            }
        }

        this.file = fileDirectory.resolve(filename);
        if(!Files.exists(file)) {
            try {
                Files.createFile(file);
                log.debug("Created blacklist file '{}'", file.toString());
            } catch (IOException ex) {
                log.error("Failed to create blacklist file at '{}'", file.toString());
                System.exit(1);
            }
        }
    }

    @Override
    public void loadCustomers() {
        log.debug("Loading blacklisted customers from file '{}'", file.toString());

        try {
            List<String> customers = Files.readAllLines(file);
            list = customers.stream()
                    .map(customerString -> customerString.split(","))
                    .map(customerElement -> new Customer(
                            Long.parseLong(customerElement[0]),
                            customerElement[1],
                            customerElement[2],
                            true))
                    .collect(Collectors.toList());
        } catch (IOException ex) {
            log.error("IOException occur when reading file {}", file.toString());
            list = new ArrayList<>(0);
        } catch (Exception ex) {
            log.error("Exception occur when loading customers from {} - {}", file.toString(), ex.getLocalizedMessage());
            list = new ArrayList<>(0);
        } finally {
            log.debug("Loaded blacklisted customers from file {}", file.toString());
        }
    }

    @Override
    public List<Customer> readCustomers() {
        return list;
    }
}
