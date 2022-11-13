package org.prgrms.kdt.customer;

import org.prgrms.kdt.voucher.FileVoucherManager;
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

    private static Logger logger = LoggerFactory.getLogger(FileVoucherManager.class);

    private static final String FILE_PATH = "src/main/resources/customer_blacklist.csv";

    private final File blacklistCsv;

    public BlackCustomerManager() {
        this.blacklistCsv = createFile();
    }

    private static File createFile() {
        File file = new File(FILE_PATH);
        validateFile(file);
        return file;
    }

    private static void validateFile(File file) {
        if (!file.exists()) {
            createFile(file);
        }
    }

    private static void createFile(File file) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            logger.error("Cannot create file. Please check file name or path.");
            throw new RuntimeException("File Create Error");
        }
    }

    public List<Customer> findAll() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(blacklistCsv))) {
            return bufferedReader.lines()
                    .map(line -> line.split(", "))
                    .map(t -> new Customer(t[0], t[1]))
                    .toList();
        } catch (IOException e) {
            logger.error("Cannot find file. Please check there is file those name is {}", blacklistCsv.getName());
            throw new RuntimeException("File Read Error");
        }
    }
}
