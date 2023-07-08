package com.prgmrs.voucher.view.render;

import com.prgmrs.voucher.enums.CreationSelectionType;
import com.prgmrs.voucher.exception.NoSuchChoiceException;
import com.prgmrs.voucher.view.ConsoleReader;
import com.prgmrs.voucher.view.writer.ConsoleCreationWriter;
import org.springframework.stereotype.Component;

@Component
public class ConsoleCreationView {
    private final ConsoleCreationWriter consoleCreationWriter;
    private final ConsoleVoucherCreationView consoleVoucherCreationView;
    private final ConsoleReader consoleReader;
    private final ConsoleUserCreationView consoleUserCreationView;

    public ConsoleCreationView(ConsoleCreationWriter consoleCreationWriter, ConsoleVoucherCreationView consoleVoucherCreationView, ConsoleReader consoleReader, ConsoleUserCreationView consoleUserCreationView) {
        this.consoleCreationWriter = consoleCreationWriter;
        this.consoleVoucherCreationView = consoleVoucherCreationView;
        this.consoleReader = consoleReader;
        this.consoleUserCreationView = consoleUserCreationView;
    }

    void selectCreationType() {
        boolean continueRunning = true;
        while (continueRunning) {
            consoleCreationWriter.showCreationType();
            try {
                CreationSelectionType creationSelectionType = CreationSelectionType.of(consoleReader.read());
                redirectCreationType(creationSelectionType);
                continueRunning = false;
            } catch (NoSuchChoiceException e) {
                consoleCreationWriter.write("such creation type not exist");
            }
        }
    }

    private void redirectCreationType(CreationSelectionType creationSelectionType) {
        switch (creationSelectionType) {
            case CREATE_USER -> consoleUserCreationView.createUser();
            case CREATE_VOUCHER -> consoleVoucherCreationView.selectVoucher();
            case BACK -> {}
        }
    }
}
