package org.prgrms.kdtspringorder.io.enums;

import java.util.Arrays;

public enum CommandType {
  CREATE("create", new String[]{"fixed","percent"}, true),
  LIST("list", new String[]{}, false),
  EXIT("exit", new String[]{}, false);

  private final String TYPE;
  private final String[] options;
  private final boolean mustNeedOption;

  CommandType(String type ,String[] options, boolean mustNeedOption) {
    this.TYPE = type;
    this.options = options;
    this.mustNeedOption = mustNeedOption;
  }

  public boolean hasOption(String option){
    int cnt = (int) Arrays.stream(this.options)
        .filter(possibleOption -> possibleOption.equals(option)).count();
    return cnt > 0;
  }

  public boolean mustNeedOption(){
    return mustNeedOption;
  }

  public String getTypeName(){
    return this.TYPE;
  }

  public static CommandType of(String commandName){
    return Arrays.stream(CommandType.values())
        .filter(commandType -> commandName
        .equals(commandType.TYPE))
        .findFirst()
        .orElseThrow(IllegalArgumentException::new);
  }

  public String[] getOptions(){
    return this.options;
  }
}
