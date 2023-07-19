package com.example.demo.voucher.presentation;

import com.example.demo.common.AppConfiguration;
import com.example.demo.common.io.Input;
import com.example.demo.common.io.Output;
import com.example.demo.common.command.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.Environment;

import java.util.ArrayList;
import java.util.List;

public class VoucherRunner {

    private static Logger logger = LoggerFactory.getLogger(VoucherRunner.class);

    public static void main(String[] args) {
        var applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.getEnvironment().setActiveProfiles("dev");
        applicationContext.register(AppConfiguration.class);
        applicationContext.refresh();

        var input = applicationContext.getBean(Input.class);
        var output = applicationContext.getBean(Output.class);

        var environment = applicationContext.getBean(Environment.class);
        String[] activeProfiles = environment.getActiveProfiles();
        if (activeProfiles.length == 0) {
            logger.info("No active profiles");
        } else {
            for (String profile : activeProfiles) {
                logger.info("Active profile: {}", profile);
            }
        }

        String command = "";

        while (!command.equals("exit")) {
            output.printLine("=== Voucher Program ===");
            output.printLine("Type exit to exit the program.");
            output.printLine("Type create to create a new voucher.");
            output.printLine("Type list to list all vouchers.");
            output.printLine("Type blacklist to list all blacklist.");

            command = input.readLine();

            try {
                Command strategy = applicationContext.getBean(command, Command.class);
                strategy.execute();
            } catch (NoSuchBeanDefinitionException ex) {
                logger.error(ex.getMessage());
                output.printLine("Unknown command");
            } catch (RuntimeException ex) {
                output.printLine(ex.getMessage());
                logger.error(ex.getMessage());
            }
        }
    }
}