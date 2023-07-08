package com.prgmrs.voucher.view.render;

import com.prgmrs.voucher.enums.ManagementType;
import com.prgmrs.voucher.exception.NoSuchChoiceException;
import com.prgmrs.voucher.view.ConsoleReader;
import com.prgmrs.voucher.view.writer.ConsoleCreationWriter;
import com.prgmrs.voucher.view.writer.ConsoleMainWriter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ConsoleMainView implements CommandLineRunner {
    private final ConsoleReader consoleReader;
    private final ConsoleMainWriter consoleMainWriter;
    private final ConsoleCreationWriter consoleCreationWriter;
    private final ConsoleListView consoleListView;
    private final ConsoleCreationView consoleCreationView;

    public ConsoleMainView(ConsoleReader consoleReader, ConsoleMainWriter consoleMainWriter, ConsoleCreationWriter consoleCreationWriter, ConsoleListView consoleListView, ConsoleCreationView consoleCreationView) {
        this.consoleReader = consoleReader;
        this.consoleMainWriter = consoleMainWriter;
        this.consoleCreationWriter = consoleCreationWriter;
        this.consoleListView = consoleListView;
        this.consoleCreationView = consoleCreationView;
    }

    @Override
    public void run(String... args) {
        boolean continueRunning = true;
        while (continueRunning) {
            consoleMainWriter.showManagementType();
            try {
                ManagementType managementType = ManagementType.of(consoleReader.read());
                continueRunning = redirectManagementType(managementType);
            } catch (NoSuchChoiceException e) {
                consoleCreationWriter.write("such option not exist");
            }
        }
    }

    private boolean redirectManagementType(ManagementType managementType) {
        boolean continueRunning = true;
        switch (managementType) {
            case CREATE_MODE -> consoleCreationView.selectCreationType();
            case LIST_MODE -> consoleListView.selectListType();
            case EXIT_THE_LOOP -> continueRunning = false;
        }
        return continueRunning;
    }
}
