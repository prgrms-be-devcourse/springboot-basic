package com.voucher.vouchermanagement.repository.blacklist;

import com.voucher.vouchermanagement.model.customer.Customer;
import com.voucher.vouchermanagement.repository.io.FileInput;
import com.voucher.vouchermanagement.repository.io.FileOutput;
import com.voucher.vouchermanagement.repository.utils.CsvDeserializer;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;

@Repository
public class BlacklistRepository {

  private final String blacklistDbName = "customer_blacklist.csv";
  private final Resource blackListDb = new ClassPathResource("db/" + blacklistDbName);
  private final FileInput input;
  private final FileOutput output;
  private final CsvDeserializer<Customer> csvDeserializer;

  public BlacklistRepository(FileInput input, FileOutput output,
      @Qualifier("blacklistDeserializer") CsvDeserializer<Customer> csvDeserializer) {
    this.input = input;
    this.output = output;
    this.csvDeserializer = csvDeserializer;
  }

  public List<Customer> findAll() throws IOException {
    return input
        .readAllLine(blackListDb.getFile())
        .stream().map(csvDeserializer::deserialize)
        .collect(Collectors.toList());
  }
}
