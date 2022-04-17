package org.prgrms.kdt.command;

import java.util.Locale;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

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