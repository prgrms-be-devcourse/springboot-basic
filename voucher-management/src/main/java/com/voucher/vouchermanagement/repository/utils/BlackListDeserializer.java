package com.voucher.vouchermanagement.repository.utils;

import com.voucher.vouchermanagement.model.user.User;
import java.util.StringTokenizer;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("blacklistDeserializer")
public class BlackListDeserializer implements CsvDeserializer<User> {

  private static final Logger logger = LoggerFactory.getLogger(BlackListDeserializer.class);

  @Override
  public User deserialize(String csvLine) {
    StringTokenizer stringTokenizer = new StringTokenizer(csvLine, ",");
    String id = stringTokenizer.nextToken().trim();
    String name = stringTokenizer.nextToken().trim();

    logger.info("parse()");
    logger.info("csvLine {} -> [{}] [{}]", csvLine, id, name);

    return new User(UUID.fromString(id), name);
  }
}
