package org.prgrms.java.repository.customer;

import org.prgrms.java.common.Mapper;
import org.prgrms.java.domain.customer.Customer;
import org.prgrms.java.exception.CustomerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class FileCustomerRepository implements CustomerRepository {
    private final String DATA_PATH;
    private final String DATA_NAME_FOR_CUSTOMER;
    private final String DATA_NAME_FOR_BLACKLIST;

    private final static Logger logger = LoggerFactory.getLogger(FileCustomerRepository.class);
    public FileCustomerRepository(@Value("${prgrms.data.path}") String DATA_PATH, @Value("${prgrms.data.name.customer}") String DATA_NAME_FOR_CUSTOMER, @Value("${prgrms.data.name.blacklist}") String DATA_NAME_FOR_BLACKLIST) {
        logger.error("생성 중...");
        try {
            new File(DATA_PATH).mkdirs();
            new File(MessageFormat.format("{0}/{1}", DATA_PATH, DATA_NAME_FOR_CUSTOMER)).createNewFile();
            new File(MessageFormat.format("{0}/{1}", DATA_PATH, DATA_NAME_FOR_BLACKLIST)).createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.DATA_PATH = DATA_PATH;
        this.DATA_NAME_FOR_CUSTOMER = DATA_NAME_FOR_CUSTOMER;
        this.DATA_NAME_FOR_BLACKLIST = DATA_NAME_FOR_BLACKLIST;
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        for (boolean isBlocked: List.of(true, false)) {
            try (BufferedReader reader = new BufferedReader(new FileReader(MessageFormat.format("{0}/{1}", DATA_PATH, getDataName(isBlocked))))) {
                Optional<String> str = reader.lines()
                        .filter(line -> line.contains(customerId.toString()))
                        .findAny();
                if (str.isPresent()) {
                    return Optional.of(Mapper.mapToCustomer(str.get()));
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Customer> findByName(String name) {
        for (boolean isBlocked: List.of(true, false)) {
            try (BufferedReader reader = new BufferedReader(new FileReader(MessageFormat.format("{0}/{1}", DATA_PATH, getDataName(isBlocked))))) {
                Optional<String> str = reader.lines()
                        .filter(line -> line.split(",")[1].trim().equals(name))
                        .findAny();
                if (str.isPresent()) {
                    return Optional.of(Mapper.mapToCustomer(str.get()));
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        for (boolean isBlocked: List.of(true, false)) {
            try (BufferedReader reader = new BufferedReader(new FileReader(MessageFormat.format("{0}/{1}", DATA_PATH, getDataName(isBlocked))))) {
                Optional<String> str = reader.lines()
                        .filter(line -> line.split(",")[2].trim().equals(email))
                        .findAny();
                if (str.isPresent()) {
                    return Optional.of(Mapper.mapToCustomer(str.get()));
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Customer> findAll() {
        List<Customer> customers = new ArrayList<>();
        for (boolean isBlocked: List.of(true, false)) {
            try (BufferedReader reader = new BufferedReader(new FileReader(MessageFormat.format("{0}/{1}", DATA_PATH, getDataName(isBlocked))))) {
                customers.addAll(reader.lines()
                        .map(Mapper::mapToCustomer)
                        .collect(Collectors.toList()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return customers;
    }

    @Override
    public Customer insert(Customer customer) {
        if (findById(customer.getCustomerId()).isPresent()) {
            throw new CustomerException(String.format("Already exists customer having id %s", customer.getCustomerId()));
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(MessageFormat.format("{0}/{1}", DATA_PATH, getDataName(customer.isBlocked())), true))) {
            writer.write(customer.toString());
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return customer;
    }

    @Override
    public Customer update(Customer customer) {
        for (boolean isBlocked: List.of(true, false)) {
            List<String> lines;
            try (BufferedReader reader = new BufferedReader(new FileReader(MessageFormat.format("{0}/{1}", DATA_PATH, getDataName(isBlocked))))) {
                lines = reader.lines().collect(Collectors.toList());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(MessageFormat.format("{0}/{1}", DATA_PATH, getDataName(isBlocked))))) {
                for (String line : lines) {
                    if (line.contains(customer.getCustomerId().toString())) {
                        writer.write(customer.toString());
                    } else {
                        writer.write(line);
                    }
                    writer.newLine();
                }
                writer.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return customer;
    }

    @Override
    public void delete(UUID customerId) {
        for (boolean isBlocked: List.of(true, false)) {
            List<String> lines;
            try (BufferedReader reader = new BufferedReader(new FileReader(MessageFormat.format("{0}/{1}", DATA_PATH, getDataName(isBlocked))))) {
                lines = reader.lines().collect(Collectors.toList());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(MessageFormat.format("{0}/{1}", DATA_PATH, getDataName(isBlocked))))) {
                for (String line : lines) {
                    if (!line.contains(customerId.toString())) {
                        writer.write(line);
                        writer.newLine();
                    }
                }
                writer.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public long deleteAll() {
        AtomicLong count = new AtomicLong(0L);
        List<String> fileName = new ArrayList<>() {{
                add(MessageFormat.format("{0}/{1}", DATA_PATH, getDataName(true)));
                add(MessageFormat.format("{0}/{1}", DATA_PATH, getDataName(false)));
        }};

        fileName.forEach(file -> {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                count.addAndGet(reader.lines()
                        .filter(line -> !line.isBlank())
                        .count());
                new FileOutputStream(file).close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        return count.get();
    }

    private String getDataName(boolean isBlocked) {
        return (isBlocked) ? DATA_NAME_FOR_BLACKLIST : DATA_NAME_FOR_CUSTOMER;
    }
}
