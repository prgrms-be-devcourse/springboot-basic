package com.blessing333.springbasic.ui;

import lombok.RequiredArgsConstructor;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;

import java.util.Scanner;

@RequiredArgsConstructor
public class ApacheCliUserInterface implements UserInterface{
    private final Scanner scanner = new Scanner(System.in);
    private final CommandOptionManager optionManager = new CommandOptionManager();
    private final CommandLineParser parser = new DefaultParser();
    private final HelpFormatter helpFormatter = new HelpFormatter();

    @Override
    public void showWelcomeText() {
        printMessage("=== Voucher Program ===");
        printMessage("Type exit to exit the program.");
        printMessage("Type create to create a new voucher.");
        printMessage("Type list to list all vouchers.");

    }

    @Override
    public void showHelpText() {
        helpFormatter.printHelp("Voucher 관리 ",optionManager.getSupportedOptions().getOptions());
    }

    @Override
    public void printMessage(String message) {
        System.out.println(message);
    }

    @Override
    public String inputMessage() {
        return scanner.nextLine();
    }

}
