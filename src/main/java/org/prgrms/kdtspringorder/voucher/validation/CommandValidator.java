package org.prgrms.kdtspringorder.voucher.validation;

import java.util.Arrays;
import java.util.List;
import org.prgrms.kdtspringorder.io.enums.CommandType;
import org.prgrms.kdtspringorder.io.domain.Command;
import org.prgrms.kdtspringorder.io.exception.InvalidCommandException;

public class CommandValidator {

  public Command validate(Command inputCommand) throws InvalidCommandException {
    // 존재하는 명령인지 확인
    String commandName = inputCommand.getCommandName();
    List<String> commandOptions = inputCommand.getOptions();

    // 해당 명령어를 어플리케이션에서 지원하는지를 확인한다.
    CommandType commandType = Arrays.stream(CommandType.values())
        .filter(offeredCommand -> offeredCommand.getTypeName().equals(commandName))
        .findFirst().orElseThrow(() -> new InvalidCommandException(inputCommand.getCommandName() + " : 해당 명령어는 존재하지 않습니다."));

    // 명령어를 지원한다면 해당 명령어가 옵션이 반드시 필요한 명령어인지 확인한다.
    if(CommandType.of(inputCommand.getCommandName()).mustNeedOption()){
      // 옵션이 반드시 필요한 명령의 경우 입력받은 옵션이 0개라면 예외를 발생시킨다.
      if(inputCommand.getOptions().size() == 0) throw new InvalidCommandException(
          inputCommand.getCommandName() +
              " : 해당 명령은 옵션이 반드시 필요합니다.\n" +
              Arrays.toString(commandType.getOptions()) +
              "\n위의 옵션중 한가지를 선택해주세요\n"

      );
    }

    // 사용자가 입력한 명령에 대하여
    // 옵션을 제대로 입력했는지 확인한다.
    for (String inputOption : inputCommand.getOptions()) {
      if (!commandType.hasOption(inputOption)) {
        throw new InvalidCommandException(
            commandType.getTypeName() + " : 해당 명렁어에는 " + inputOption + " 옵션이 존재하지 않습니다.");
      }
    }
    // 이 부분을 스트림으로 고쳐보고 싶었는데 시도하다가 결국엔 하지 못했습니다.
    return inputCommand;
  }


}
