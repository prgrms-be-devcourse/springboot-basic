package devcourse.springbootbasic.commandline;

import devcourse.springbootbasic.commandline.console.ConsoleIOHandler;
import devcourse.springbootbasic.commandline.constant.ConsoleConstants;
import devcourse.springbootbasic.exception.InputErrorMessage;
import devcourse.springbootbasic.exception.InputException;
import devcourse.springbootbasic.commandline.function.Function;
import devcourse.springbootbasic.commandline.function.FunctionHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Component
@RequiredArgsConstructor
public class CommandLineExecutor implements CommandLineRunner {

    private final ConsoleIOHandler consoleIOHandler;
    private final FunctionHandler functionHandler;

    private boolean isRunning = true;

    @Override
    public void run(String... args) {
        consoleIOHandler.printExecuteMode();
        while (isRunning) {
            progress();
        }
    }

    private void progress() {
        try {
            consoleIOHandler.printMenuTitle(ConsoleConstants.VOUCHER_PROGRAM_START_MESSAGE);
            consoleIOHandler.printEnumString(Function.class);
            String command = consoleIOHandler.getInputWithPrint();

            Function.fromString(command)
                    .ifPresentOrElse(
                            function -> function.execute(functionHandler),
                            () -> {
                                throw InputException.of(InputErrorMessage.INVALID_COMMAND);
                            });
        } catch (RuntimeException e) {
            log.warn(e.getMessage());
        } catch (Exception e) {
            isRunning = false;
            log.error(Arrays.toString(e.getStackTrace()));
        }
    }
}
