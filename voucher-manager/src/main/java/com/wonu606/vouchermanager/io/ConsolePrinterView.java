package com.wonu606.vouchermanager.io;

import com.wonu606.vouchermanager.domain.Voucher;
import java.util.List;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;

public class ConsolePrinterView {

    private final TextTerminal textTerminal = TextIoFactory.getTextTerminal();

    private String menuDescription;
    private String creationDescription;
    private String exitDescription;

    public ConsolePrinterView(String menuDescription, String creationDescription, String exitDescription) {
        this.menuDescription = menuDescription;
        this.creationDescription = creationDescription;
        this.exitDescription = exitDescription;
    }

    public void printMenu() {
        textTerminal.println(menuDescription);
    }

    public void printCreatableList() {
        textTerminal.println(creationDescription);
    }

    public void printVoucherList(List<Voucher> voucherList) {
        textTerminal.println("=== 바우처 리스트 ===");
        voucherList.forEach(v -> textTerminal.println(v.toString()));
    }

    public void printMessage(String message) {
        textTerminal.println(message);
    }

    public void printExitMessage() {
        textTerminal.println(exitDescription);
    }
    public void close() {
        textTerminal.dispose();
    }
}
