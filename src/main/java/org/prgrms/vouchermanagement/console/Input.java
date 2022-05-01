package org.prgrms.vouchermanagement.console;

import java.util.Optional;

public interface Input {
  Optional<String> read();
  String readCommand();
  String readListType();
  Optional<String> readVoucherType();
}