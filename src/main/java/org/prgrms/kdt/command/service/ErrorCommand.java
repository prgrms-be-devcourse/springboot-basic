package org.prgrms.kdt.command.service;

import org.prgrms.kdt.command.domain.Command;
import org.prgrms.kdt.command.domain.ErrorType;
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