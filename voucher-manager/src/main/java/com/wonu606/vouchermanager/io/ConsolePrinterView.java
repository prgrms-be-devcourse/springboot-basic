package com.wonu606.vouchermanager.io;

import com.wonu606.vouchermanager.domain.Voucher;
import java.util.List;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;

public class ConsolePrinterView {

    private final TextTerminal textTerminal = TextIoFactory.getTextTerminal();

    private String menuDescription;
    private String creationDescription;
    private String exitMessage;

    public ConsolePrinterView(String menuDescription, String creationDescription, String exitMessage) {
        this.menuDescription = menuDescription;
        this.creationDescription = creationDescription;
        this.exitMessage = exitMessage;
    }

    public void printMenu() {
        textTerminal.println(menuDescription);
    }

    public void printCreatableList() {

    }

    public void printVoucherList(List<Voucher> voucherList) {
        textTerminal.println("=== 바우처 리스트 ===");
        voucherList.forEach(v -> textTerminal.println(v.toString()));
    }

    public void printMessage(String message) {
        textTerminal.println(message);
    }

    public void printExitMessage() {
        textTerminal.println(exitMessage);
    }
    public void close() {
        textTerminal.dispose();
    }
}
