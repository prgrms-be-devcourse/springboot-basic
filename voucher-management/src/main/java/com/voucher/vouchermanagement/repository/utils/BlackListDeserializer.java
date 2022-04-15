package com.voucher.vouchermanagement.repository.utils;

import com.voucher.vouchermanagement.model.customer.Customer;
import org.springframework.stereotype.Component;

import java.util.StringTokenizer;
import java.util.UUID;

@Component("blacklistDeserializer")
public class BlackListDeserializer implements CsvDeserializer<Customer> {

    @Override
    public Customer deserialize(String csvLine) {
        StringTokenizer stringTokenizer = new StringTokenizer(csvLine, ",");
        String id = stringTokenizer.nextToken().trim();
        String name = stringTokenizer.nextToken().trim();

        return new Customer(UUID.fromString(id), name);
    }
}
