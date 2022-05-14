package org.prgrms.vouchermanagement.console;

import java.util.List;

public interface Output {

  void printCommandList();

  void printNotExistingCommand();

  void printListType();

  void printList(List list);
}