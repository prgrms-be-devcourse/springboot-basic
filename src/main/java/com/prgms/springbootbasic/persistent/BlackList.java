package com.prgms.springbootbasic.persistent;

import com.prgms.springbootbasic.domain.Customer;
import com.prgms.springbootbasic.exception.CantReadFileException;
import com.prgms.springbootbasic.util.ExceptionMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.nio.file.Files;
import java.util.List;
import java.util.UUID;

@Repository
public class BlackList {

    @Value("${file.blacklist}")
    private String filePath;

    public List<Customer> findBlackList() {
        File file = new File(filePath);
        try {
            List<String> customerOfString = Files.readAllLines(file.toPath());
            return customerOfString.stream()
                        .map(c -> getCustomer(c.split(",")))
                        .toList();
        } catch (Exception e) {
            throw new CantReadFileException(ExceptionMessage.CANT_READ_FILE);
        }
    }

    private Customer getCustomer(String[] customer) {
        UUID customerId = UUID.fromString(customer[0]);
        String name = customer[1];
        return new Customer(customerId, name);
    }

}
