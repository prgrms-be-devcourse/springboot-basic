package org.prgrms.kdt.customer;

import org.prgrms.kdt.voucher.FileVoucherManager;
import org.prgrms.kdt.voucher.Voucher;
import org.prgrms.kdt.voucher.VoucherAmount;
import org.prgrms.kdt.voucher.VoucherType;
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

    private static final String FILE_PATH = "src/main/resources/customer_blacklist.csv";


    private File loadFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            createFile(file);
        }
        return file;
    }

    private void createFile(File file) {
        try {
            file.createNewFile();
        } catch (IOException exception) {
            throw new RuntimeException("Cannot create file. Please check file name or path.", exception);
        }
    }

    public List<Customer> findAll() {
        File blacklistCsv = loadFile();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(blacklistCsv))) {
            return bufferedReader.lines()
                    .map(line -> line.split(", "))
                    .map(BlackCustomerManager::getCustomer)
                    .toList();
        } catch (IOException exception) {
            throw new RuntimeException("Cannot find file. Please check there is file those name is " + blacklistCsv.getName(), exception);
        }
    }

    private static Customer getCustomer(String[] t) {
        try {
            return new Customer(t[0], t[1]);
        } catch (ArrayIndexOutOfBoundsException exception) {
            throw new RuntimeException("Invalid File. Please write the file in following format. [Format]: Id, Name", exception);
        }
    }
}
