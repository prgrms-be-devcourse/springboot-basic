package org.prgrms.kdt.command;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Qualifier("INIT")
@Component
public class InitCommand implements Command {

  @Override
  public String execute() {
    return null;
  }
}