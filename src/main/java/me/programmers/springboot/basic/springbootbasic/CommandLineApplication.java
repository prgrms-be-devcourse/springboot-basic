package me.programmers.springboot.basic.springbootbasic;

import me.programmers.springboot.basic.springbootbasic.command.Command;
import me.programmers.springboot.basic.springbootbasic.command.CommandStrategy;
import me.programmers.springboot.basic.springbootbasic.command.CommandType;
import me.programmers.springboot.basic.springbootbasic.config.AppConfig;
import me.programmers.springboot.basic.springbootbasic.io.ConsoleInput;
import me.programmers.springboot.basic.springbootbasic.io.ConsoleOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class CommandLineApplication {

    private static final Logger logger = LoggerFactory.getLogger(CommandLineApplication.class);
    private AnnotationConfigApplicationContext context;

    public void run() {
        logger.info("CommandLineApplication Start");
        context = new AnnotationConfigApplicationContext(AppConfig.class);

        ConsoleOutput outputConsole = context.getBean(ConsoleOutput.class);
        ConsoleInput inputConsole = context.getBean(ConsoleInput.class);

        boolean isExit = false;
        while (!isExit) {
            outputConsole.showMenu();
            String inputMenu = inputConsole.inputCommand("명령어 입력: ");
            CommandType commandType = getCommand(inputMenu);
            isExit = doCommand(commandType);
        }
    }

    private boolean doCommand(CommandType commandType) {
        if (commandType == null)
            return false;
        if (commandType == CommandType.EXIT)
            return true;

        CommandStrategy commandStrategy = commandType.execute(context);

        Command command = new Command(commandStrategy);
        command.operateCommand();
        return false;
    }

    private CommandType getCommand(String inputMenu) {
        CommandType type = null;
        try {
            type = CommandType.getCommandType(inputMenu);
        } catch (IllegalArgumentException e) {
            logger.error("잘못된 메뉴 명령어 입력: {}", inputMenu);
        }
        return type;
    }
}
