package org.prgrms.kdtspringorder.io.enums;

import java.text.MessageFormat;
import java.util.Arrays;

public enum CommandType {
  CREATE("create", new String[]{"fixed", "percent"}, true),
  LIST("list", new String[]{}, false),
  EXIT("exit", new String[]{}, false);

  private final String commandName;
  private final String[] options;
  private final boolean mustNeedOption;

  CommandType(String commandName, String[] options, boolean mustNeedOption) {
    this.commandName = commandName;
    this.options = options;
    this.mustNeedOption = mustNeedOption;
  }

  public boolean hasOption(String option) {
    int cnt = (int) Arrays.stream(this.options)
        .filter(possibleOption -> possibleOption.equals(option)).count();
    return cnt > 0;
  }

  public boolean mustNeedOption() {
    return mustNeedOption;
  }

  public String getTypeName() {
    return this.commandName;
  }

  public static CommandType of(String commandName) {
    return Arrays.stream(CommandType.values())
        .filter(commandType -> commandName.equals(commandType.commandName))
        .findFirst()
        .orElseThrow(() -> {
          throw new IllegalArgumentException(
              MessageFormat.format("{} : 해당 명령어는 지원하지 않는 명령어 입니다.", commandName));
        });
  }

  public String[] getOptions() {
    return this.options;
  }
}
