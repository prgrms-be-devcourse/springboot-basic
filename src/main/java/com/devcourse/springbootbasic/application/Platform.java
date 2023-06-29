package com.devcourse.springbootbasic.application;

import com.devcourse.springbootbasic.application.exception.InvalidDataException;
import com.devcourse.springbootbasic.application.io.InputConsole;
import com.devcourse.springbootbasic.application.io.OutputConsole;
import com.devcourse.springbootbasic.application.template.CommandLineTemplate;
import com.devcourse.springbootbasic.application.template.CreateMenuTemplate;
import com.devcourse.springbootbasic.application.template.ListMenuTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Platform {

    private static final Logger logger = LoggerFactory.getLogger(Platform.class);

    private final CommandLineTemplate commandLineTemplate;

    public Platform(
            InputConsole input, OutputConsole output,
            CreateMenuTemplate createMenuTemplate, ListMenuTemplate listMenuTemplate
    ) {
        this.commandLineTemplate = new CommandLineTemplate(input, output, createMenuTemplate, listMenuTemplate);
    }

    public void run() {
        logger.debug("Program Started");
        while (true) {
            if (isEndGame()) {
                logger.debug("Program Terminated");
                break;
            }
        }
    }

    private boolean isEndGame() {
        try {
            return commandLineTemplate.isExitMenuBranch();
        } catch (InvalidDataException e) {
            commandLineTemplate.errorTask(e);
            logger.error(e.getMessage(), e.getCause());
        }
        return false;
    }

}
