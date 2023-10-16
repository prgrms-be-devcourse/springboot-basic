package devcourse.springbootbasic.commandline.console;

import devcourse.springbootbasic.commandline.console.constant.InputMessage;
import devcourse.springbootbasic.commandline.console.input.ConsoleInput;
import devcourse.springbootbasic.commandline.console.output.ConsoleOutput;
import devcourse.springbootbasic.commandline.exception.InputException;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.function.Function;

import static devcourse.springbootbasic.commandline.exception.InputErrorMessage.NOT_NUMBER;


@RequiredArgsConstructor
public class ConsoleIOHandler {

    private static final String QUESTION_MESSAGE_PREFIX = "Q. ";
    private static final String INPUT_PREFIX = "> ";
    private static final String SEPARATOR = "------------------------------";
    private static final String MENU_TITLE = "=== %s ===";

    private final ConsoleInput consoleInput;
    private final ConsoleOutput consoleOutput;

    // Input
    public String getInputWithPrint() {
        consoleOutput.print(INPUT_PREFIX);
        return consoleInput.getInput();
    }

    public String inputStringWithMessage(InputMessage inputMessage) {
        this.printQuestionMessage(inputMessage.getMessage());

        return getInputWithPrint();
    }

    public int inputIntWithMessage(InputMessage inputMessage) {
        this.printQuestionMessage(inputMessage.getMessage());

        return getParseInputWithPrint(Integer::parseInt);
    }

    public long inputLongWithMessage(InputMessage inputMessage) {
        this.printQuestionMessage(inputMessage.getMessage());

        return getParseInputWithPrint(Long::parseLong);
    }

    private <T> T getParseInputWithPrint(Function<String, T> parseFunction) {
        try {
            return parseFunction.apply(getInputWithPrint());
        } catch (NumberFormatException e) {
            throw InputException.of(NOT_NUMBER);
        }
    }


    // Output
    public void printMenuTitle(String title) {
        consoleOutput.println(String.format(MENU_TITLE, title));
    }

    public void printQuestionMessage(String message) {
        consoleOutput.println(QUESTION_MESSAGE_PREFIX + message);
    }

    public <T> void printListString(List<T> list) {
        consoleOutput.printEmptyLine();
        for (T t : list) {
            consoleOutput.println(t.toString());
            consoleOutput.println(SEPARATOR);
        }
        consoleOutput.printEmptyLine();
    }

    public void printEnumString(Class<? extends Enum<?>> e) {
        for (Enum<?> enumConstant : e.getEnumConstants()) {
            consoleOutput.println(enumConstant.toString());
        }
    }
}
