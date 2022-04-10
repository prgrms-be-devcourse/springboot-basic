package org.prgrms.kdt.command.service;

import java.util.Locale;
import org.prgrms.kdt.command.domain.Command;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Qualifier("EXIT")
@Component
public class ExitCommand implements Command {

  private final MessageSource messageSource;

  public ExitCommand(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  @Override
  public String execute() {
    return messageSource.getMessage("menu.exit", null, Locale.ROOT);
  }
}