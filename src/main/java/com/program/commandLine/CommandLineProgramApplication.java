package com.program.commandLine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class CommandLineProgramApplication {

    static final Logger logger = LoggerFactory.getLogger(CommandLineProgramApplication.class);

    private final SystemController systemController;

    public CommandLineProgramApplication(SystemController systemController) {
        this.systemController = systemController;
    }

    public static void main(String[] args) {

        logger.info("프로그램 시작");
        SpringApplication.run(CommandLineProgramApplication.class, args);
        logger.info("프로그램 끝");
    }

    @PostConstruct
    public void start() {
        systemController.run();
    }

}
