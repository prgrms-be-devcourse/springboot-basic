package org.prgrms.kdt.command;

import org.prgrms.kdt.type.CommandType;
import org.springframework.stereotype.Component;

@Component
public class CommandFactory {

  private final InitCommand initCommand;
  private final ExitCommand exitCommand;
  private final BlacklistCommand blacklistCommand;
  private final ListVoucherCommand listVoucherCommand;
  private final CreateVoucherCommand createVoucherCommand;
  private final RegisterCustomerCommand registerCustomerCommand;
  private final DeleteVoucherCommand deleteVoucherCommand;
  private final AssignVoucherCommand assignVoucherCommand;
  private final SearchCommand searchCommand;

  public CommandFactory(
      InitCommand initCommand,
      ExitCommand exitCommand,
      BlacklistCommand blacklistCommand,
      ListVoucherCommand listVoucherCommand,
      CreateVoucherCommand createVoucherCommand,
      RegisterCustomerCommand registerCustomerCommand,
      DeleteVoucherCommand deleteVoucherCommand,
      AssignVoucherCommand assignVoucherCommand,
      SearchCommand searchCommand) {
    this.initCommand = initCommand;
    this.exitCommand = exitCommand;
    this.blacklistCommand = blacklistCommand;
    this.listVoucherCommand = listVoucherCommand;
    this.createVoucherCommand = createVoucherCommand;
    this.registerCustomerCommand = registerCustomerCommand;
    this.deleteVoucherCommand = deleteVoucherCommand;
    this.assignVoucherCommand = assignVoucherCommand;
    this.searchCommand = searchCommand;
  }

  public Command getCommand(CommandType commandType) {
    return switch (commandType) {
      case INIT -> initCommand;
      case EXIT -> exitCommand;
      case LIST -> listVoucherCommand;
      case BLACKLIST -> blacklistCommand;
      case CREATE -> createVoucherCommand;
      case REGISTER -> registerCustomerCommand;
      case DELETE -> deleteVoucherCommand;
      case ASSIGN -> assignVoucherCommand;
      case SEARCH -> searchCommand;
    };
  }
}