package org.prgrms.kdt.command.service;

import org.prgrms.kdt.command.domain.Command;
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