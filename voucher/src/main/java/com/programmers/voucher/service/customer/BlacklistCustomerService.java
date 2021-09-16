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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BlacklistCustomerService implements CustomerService {

    private final Path file;
    private List<Customer> list = new LinkedList<>();
    private static final Logger log = LoggerFactory.getLogger(BlacklistCustomerService.class);

    public BlacklistCustomerService(
            @Value("${voucher.file.blacklist.location}") String directory,
            @Value("${voucher.file.blacklist.filename}") String filename
    ) {
        log.info("Using directory '{}' as blacklist location.", directory);
        log.info("Using filename '{}' as blacklist file.", filename);

        Path fileDirectory = Paths.get(directory);
        if(!Files.exists(fileDirectory)) {
            try {
                Files.createDirectory(fileDirectory);
                log.debug("Created blacklist directory '{}'", fileDirectory);
            } catch (IOException ex) {
                log.error("Failed to create blacklist directory at '{}'", fileDirectory);
                System.exit(1);
            }
        }

        this.file = fileDirectory.resolve(filename);
        if(!Files.exists(file)) {
            try {
                Files.createFile(file);
                log.debug("Created blacklist file '{}'", file);
            } catch (IOException ex) {
                log.error("Failed to create blacklist file at '{}'", file);
                System.exit(1);
            }
        }
    }

    @Override
    public void openStorage() {
        log.debug("Loading blacklisted customers from file '{}'", file);

        try {
            List<String> customers = Files.readAllLines(file);
            list = customers.stream()
                    .map(customerString -> customerString.split(","))
                    .map(customerElement -> {
                        String[] yyyymmdd = customerElement[3].split("-");
                        return new Customer(
                                Long.parseLong(customerElement[0]),
                                customerElement[1],
                                customerElement[2],
                                true,
                                LocalDate.of(Integer.parseInt(yyyymmdd[0]), Integer.parseInt(yyyymmdd[1]), Integer.parseInt(yyyymmdd[2])));
                    })
                    .collect(Collectors.toList());
        } catch (IOException ex) {
            log.error("IOException occur when reading file {}", file);
            list = new ArrayList<>(0);
        } catch (Exception ex) {
            log.error("Exception occur when loading customers from {} - {}", file, ex.getLocalizedMessage());
            list = new ArrayList<>(0);
        } finally {
            log.debug("Loaded blacklisted customers from file {}", file);
        }
    }

    @Override
    public void closeStorage() {
        throw new UnsupportedOperationException("Closing storage does not supported by blacklist customer service.");
    }

    @Override
    public List<Customer> listAll() {
        return list;
    }

    @Override
    public Optional<Customer> findById(long id) {
        return list.stream().filter(c -> c.getId() == id).findAny();
    }

    @Override
    public Customer create(String username, String alias) {
        throw new UnsupportedOperationException("Creating blacklisted user not allowed from appication.");
    }
}
