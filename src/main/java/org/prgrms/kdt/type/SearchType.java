package org.prgrms.kdt.type;

import java.util.Arrays;
import org.prgrms.kdt.exception.InvalidInputException;

public enum SearchType {
  SEARCH_VOUCHER("voucher"),
  SEARCH_CUSTOMER("customer");

  private final String type;

  SearchType(String type) {
    this.type = type;
  }

  public static SearchType of(String type) {
    var matchedType = Arrays.stream(SearchType.values())
        .filter(searchType -> searchType.type.equals(type))
        .findFirst();
    return matchedType.orElseThrow(InvalidInputException::new);
  }
}