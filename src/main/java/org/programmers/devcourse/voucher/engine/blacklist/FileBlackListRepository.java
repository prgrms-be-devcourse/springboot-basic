package org.programmers.devcourse.voucher.engine.blacklist;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Repository;


@Repository
public class FileBlackListRepository implements
    BlackListRepository {

  private static final Logger logger = LoggerFactory.getLogger(FileBlackListRepository.class);
  private final List<BlackList> cache = new ArrayList<>();


  public FileBlackListRepository(ConfigurableApplicationContext applicationContext) {

    var blackListResource = applicationContext.getResource("customer_blacklist.csv");
    BufferedReader blackListReader;

    try {
      var inputStream = blackListResource.getInputStream();
      blackListReader = new BufferedReader(
          new InputStreamReader(inputStream));

      blackListReader.lines().forEach(line -> {
        var fields = Arrays.stream(line.split(",")).map(String::trim).toArray(String[]::new);
        if (fields.length != 2) {
          throw new RuntimeException();
        }
        var name = fields[0];
        var reason = fields[1];
        cache.add(new BlackList(name, reason));
      });
      blackListReader.close();
    } catch (Exception exception) {
      throw new RuntimeException("BlackListRepository 읽기 실패", exception);
    }
  }

  @Override
  public List<BlackList> getAll() {
    return Collections.unmodifiableList(cache);
  }
}
