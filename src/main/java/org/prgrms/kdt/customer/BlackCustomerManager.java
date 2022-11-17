package org.prgrms.kdt.customer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;


@Repository
public class BlackCustomerManager {

    private static final Logger logger = LoggerFactory.getLogger(BlackCustomerManager.class);

    private static final String FILE_PATH = "src/main/resources/customer_blacklist.csv";
    public static final String DELIMITER = ", ";


    private File loadFile() {
        File file = new File(FILE_PATH);
        try {
            if (file.createNewFile()) {
                logger.info("file created. [FILE PATH]: " + FILE_PATH);
            }
        } catch (IOException exception) {
            throw new IllegalArgumentException("Cannot find file. Please check there is file those name is " + file.getName() + ".[File Path]: " + FILE_PATH, exception);
        }
        return file;
    }

    public List<Customer> findAll() {
        File blacklistCsv = loadFile();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(blacklistCsv))) {
            return bufferedReader.lines()
                    .map(BlackCustomerManager::mapToCustomer)
                    .toList();
        } catch (IOException exception) {
            throw new IllegalArgumentException("Cannot find file. Please check there is file those name is " + blacklistCsv.getName(), exception);
        }
    }

    private static Customer mapToCustomer(String line) {
        try {
            String[] tokens = line.split(DELIMITER);
            return new Customer(tokens[0], tokens[1]);
        } catch (ArrayIndexOutOfBoundsException exception) {
            throw new RuntimeException("Invalid File. Please write the file in following format. [Format]: Id, Name", exception);
        }
    }
}
