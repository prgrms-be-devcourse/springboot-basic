package devcourse.springbootbasic.commandline;

import devcourse.springbootbasic.commandline.console.ConsoleIOHandler;
import devcourse.springbootbasic.commandline.console.constant.ConsoleConstants;
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

    private boolean isRunning = true;

    @Override
    public void run(String... args) {
        while (isRunning) {
            progress();
        }
    }

    private void progress() {
        try {
            consoleIOHandler.printMenuTitle(ConsoleConstants.VOUCHER);
        } catch (RuntimeException e) {
            log.warn(Arrays.toString(e.getStackTrace()));
        } catch (Exception e) {
            isRunning = false;
            log.error(Arrays.toString(e.getStackTrace()));
        }
    }
}
