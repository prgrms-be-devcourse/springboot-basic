package com.voucher.vouchermanagement.repository.utils;

import com.voucher.vouchermanagement.model.customer.Customer;
import java.util.StringTokenizer;
import java.util.UUID;
import org.springframework.stereotype.Component;

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
