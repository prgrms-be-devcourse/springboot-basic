package devcourse.springbootbasic;

import devcourse.springbootbasic.commandline.console.ConsoleIOHandler;
import devcourse.springbootbasic.commandline.console.input.ConsoleInput;
import devcourse.springbootbasic.commandline.console.input.ScannerInput;
import devcourse.springbootbasic.commandline.console.output.ConsoleOutput;
import devcourse.springbootbasic.commandline.console.output.PrintStreamOutput;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ConsoleIOHandler consoleIOHandler(ConsoleInput consoleInput, ConsoleOutput consoleOutput) {
        return new ConsoleIOHandler(consoleInput, consoleOutput);
    }

    @Bean
    public ConsoleInput consoleInput() {
        return new ScannerInput();
    }

    @Bean
    public ConsoleOutput consoleOutput() {
        return new PrintStreamOutput();
    }
}
