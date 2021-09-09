package org.prgrms.kdt.app;


import java.util.*;
import java.util.function.Function;
import javax.annotation.PostConstruct;

import org.prgrms.kdt.app.command.CommandOperator;
import org.prgrms.kdt.app.command.CommandType;
import org.prgrms.kdt.app.io.Console;
import org.prgrms.kdt.util.EnumUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLineApplication implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(CommandLineApplication.class);
    private static final Function<String, CommandType> commandTypeLookupFunction =
        EnumUtils.lookupMap(CommandType.values(), CommandType::getNum);

    private final List<CommandOperator> commandOperators;
    private final Map<CommandType, CommandOperator> commandMap;
    private final Console console;
    private boolean isRunning;

    public CommandLineApplication(
        List<CommandOperator> commandOperators,
        Console console) {
        this.commandOperators = commandOperators;
        this.console = console;
        this.commandMap = new EnumMap<>(CommandType.class);
        this.isRunning = true;
    }

    private void printMenu() {
        console.printMessage("=== Type the number of command===");
        console.printEnumValues(CommandType.values());
    }

    @PostConstruct
    public void init() {
        logger.info("CommandLineApplication init");
        commandOperators.forEach(x -> commandMap.put(x.getCommandType(), x));
        logger.info("Init Command types -> {}", commandMap);
    }

    @Override
    public void run(ApplicationArguments args) {
        var prompt = """
            === Voucher Management ===
            Type exit to exit the program.
            Type create to create a new object.
            Type list to list all object.
            """;
        console.printMessage(prompt);
        while (isRunning) {
            printMenu();
            Optional.ofNullable(commandTypeLookupFunction.apply(console.input()))
                .ifPresent(value -> {
                    if (value == CommandType.EXIT) {
                        isRunning = false;
                    }
                    commandMap.get(value).execute();
                });
        }
    }
}
