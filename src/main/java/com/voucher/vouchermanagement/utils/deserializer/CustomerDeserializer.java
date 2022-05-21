package com.voucher.vouchermanagement.utils.deserializer;

import com.voucher.vouchermanagement.domain.customer.model.Customer;

import java.time.LocalDateTime;
import java.util.StringTokenizer;
import java.util.UUID;

public class CustomerDeserializer implements CsvDeserializer<Customer> {
    @Override
    public Customer deserialize(String csvLine) {
        StringTokenizer stringTokenizer = new StringTokenizer(csvLine, ",");
        String id = stringTokenizer.nextToken().trim();
        String name = stringTokenizer.nextToken().trim();
        String email = stringTokenizer.nextToken().trim();
        LocalDateTime lastLoginAt = LocalDateTime.parse(stringTokenizer.nextToken());
        LocalDateTime createdAt = LocalDateTime.parse(stringTokenizer.nextToken());

        return new Customer(UUID.fromString(id), name, email, lastLoginAt,createdAt);
    }
}
