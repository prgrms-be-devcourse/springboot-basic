package org.programmers.devcourse.voucher.engine.blacklist;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;


@Repository
public class FileBlackListRepository implements
    BlackListRepository {

  private final List<BlackList> cache = new ArrayList<>();


  public FileBlackListRepository(ApplicationContext ctx) {
    var blackListResource = ctx.getResource("customer_blacklist.csv");
    BufferedReader blackListReader;

    try {
      var is = blackListResource.getInputStream();
      blackListReader = new BufferedReader(
          new InputStreamReader(is));

      blackListReader.lines().forEach(line -> {
        var arr = Arrays.stream(line.split(",")).map(String::trim).toArray(String[]::new);
        var name = arr[0];
        var reason = arr[1];
        cache.add(new BlackList(name, reason));
      });
      blackListReader.close();
    } catch (IOException ignored) {
    }

  }

  @Override
  public List<BlackList> getAll() {
    return Collections.unmodifiableList(cache);
  }

}
