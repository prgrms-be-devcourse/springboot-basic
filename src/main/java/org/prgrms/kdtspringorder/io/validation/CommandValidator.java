package org.prgrms.kdtspringorder.io.validation;

import org.prgrms.kdtspringorder.io.domain.Command;
import org.prgrms.kdtspringorder.io.enums.implementation.CommandType;
import org.prgrms.kdtspringorder.io.exception.InvalidCommandException;

import java.util.Arrays;
import java.util.List;

public class CommandValidator {

    public Command validate(Command inputCommand) throws InvalidCommandException {
        String commandName = inputCommand.getName();
        List<String> commandOptions = inputCommand.getOptions();

        // 존재하는 명령인지 확인
        validateCommand(commandName);

        // 해당 명령에가 지원하는 옵션을 입력했는지 확인
        CommandType commandType = CommandType.of(commandName);
        validateOption(commandType, commandOptions);

        return inputCommand;
    }

    private void validateCommand(String commandName) throws InvalidCommandException {
        Arrays
                .stream(CommandType.values())
                .filter(commandType -> commandType.getTypeName().equals(commandName))
                .findFirst()
                .orElseThrow(
                        () -> new InvalidCommandException("지원하지 않는 명령어 입니다.")
                );
    }

    private void validateOption(CommandType commandType, List<String> inputCommandOptions)
            throws InvalidCommandException {
        // 명령어를 지원한다면 해당 명령어가 옵션이 반드시 필요한 명령어인지 확인한다.
        // 옵션이 반드시 필요한 명령의 경우 입력받은 옵션이 0개라면 예외를 발생시킨다.
        if (commandType.mustNeedOption() && inputCommandOptions.isEmpty()) {
            throw new InvalidCommandException(generateInvalidCommandMessage(commandType));
        }

        boolean isCorrectOption = inputCommandOptions.stream()
                .allMatch(commandType::hasOption);

        if (!isCorrectOption) {
            throw new InvalidCommandException(
                    generateInvalidOptionMessage(commandType, inputCommandOptions));
        }

    }

    private String generateInvalidCommandMessage(CommandType commandType) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(commandType.getTypeName())
                .append(" : 해당 명령은 옵션이 반드시 필요합니다.\n")
                .append(Arrays.toString(commandType.getOptions()))
                .append("\n위의 옵션중 한가지를 선택해주세요\n");
        return stringBuffer.toString();
    }

    private String generateInvalidOptionMessage(CommandType commandType, List<String> inputOption) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(commandType.getTypeName())
                .append(" : 해당 명렁어에는 ")
                .append(inputOption)
                .append(" 옵션이 존재하지 않습니다.");
        return stringBuffer.toString();
    }
}
