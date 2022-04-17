package org.prgrms.kdt.command;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Qualifier("ERROR")
@Component
public class ErrorCommand implements Command {

  @Override
  public String execute() {
    return ErrorType.INVALID_MENU.getErrorMessage();
  }
}