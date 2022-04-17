package org.prgrms.kdt.command;

import org.springframework.stereotype.Component;

@Component
public class ErrorCommand implements Command {

  @Override
  public String execute() {
    return ErrorType.INVALID_MENU.getErrorMessage();
  }
}