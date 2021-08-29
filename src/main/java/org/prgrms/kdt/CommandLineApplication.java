package org.prgrms.kdt;

import java.util.Optional;
import org.prgrms.kdt.command.CommandCollection;
import org.prgrms.kdt.command.CommandType;
import org.prgrms.kdt.io.Console;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

/**
 * Created by yhh1056
 * Date: 2021/08/18 Time: 12:34 오전
 */

@Configuration
public class CommandLineApplication implements ApplicationRunner {

    private final CommandCollection commandCollection;

    public CommandLineApplication(CommandCollection commandCollection) {
        this.commandCollection = commandCollection;
    }

    @Override
    public void run(ApplicationArguments args) {
        Console console = new Console();
        console.guide();

        while (true) {
            Optional<CommandType> commandType = console.inputCommand();
            commandType
                    .map(command -> commandCollection.findByCommandType(command))
                    .orElse(output -> output.commandError())
                    .operate(console);
        }
    }

}
