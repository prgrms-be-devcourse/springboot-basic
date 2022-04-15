package com.voucher.vouchermanagement.repository.utils;

import com.voucher.vouchermanagement.model.user.User;
import java.util.StringTokenizer;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component("blacklistDeserializer")
public class BlackListDeserializer implements CsvDeserializer<User> {

  @Override
  public User deserialize(String csvLine) {
    StringTokenizer stringTokenizer = new StringTokenizer(csvLine, ",");
    String id = stringTokenizer.nextToken().trim();
    String name = stringTokenizer.nextToken().trim();

    return new User(UUID.fromString(id), name);
  }
}
