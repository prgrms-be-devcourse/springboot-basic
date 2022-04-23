package org.prgrms.kdt.type;

import java.util.Arrays;

public enum SearchType {
  SEARCH_VOUCHER(1),
  SEARCH_CUSTOMER(2);

  private final int code;

  SearchType(int code) {
    this.code = code;
  }

  public static SearchType of(int code) {
    var type = Arrays.stream(SearchType.values())
        .filter(searchType -> searchType.code == code)
        .findFirst();
    return type.orElseThrow(() -> new RuntimeException("ad"));
  }
}