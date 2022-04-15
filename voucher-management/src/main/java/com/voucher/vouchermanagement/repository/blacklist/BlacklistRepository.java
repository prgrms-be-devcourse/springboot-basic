package com.voucher.vouchermanagement.repository.blacklist;

import com.voucher.vouchermanagement.model.user.User;
import com.voucher.vouchermanagement.repository.io.FileInput;
import com.voucher.vouchermanagement.repository.io.FileOutput;
import com.voucher.vouchermanagement.repository.utils.CsvDeserializer;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class BlacklistRepository {

  private final File blacklistDb;
  private final FileInput input;
  private final FileOutput output;
  private final CsvDeserializer<User> csvDeserializer;

  public BlacklistRepository(@Qualifier("blacklistDb") File blacklistDb, FileInput input, FileOutput output,
      @Qualifier("blacklistDeserializer") CsvDeserializer<User> csvDeserializer) {
    this.blacklistDb = blacklistDb;
    this.input = input;
    this.output = output;
    this.csvDeserializer = csvDeserializer;
  }

  public List<User> findAll() throws IOException {
    return input
        .readAllLine(blacklistDb)
        .stream().map(csvDeserializer::deserialize)
        .collect(Collectors.toList());
  }
}
