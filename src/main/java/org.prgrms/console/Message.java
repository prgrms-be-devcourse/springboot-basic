package org.prgrms.console;

import org.springframework.stereotype.Component;

@Component
public class Message {

  public String showSupportedCommands() {
    return """
        === Voucher Program ===
        Type exit to exit the program.
        Type create to create a new voucher.
        Type list to list all vouchers.""";
  }

  public String doNotMatch() {
    return "선택지에 없는 명령어 입니다.";
  }
}
