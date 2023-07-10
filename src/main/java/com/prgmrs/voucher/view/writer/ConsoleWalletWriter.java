package com.prgmrs.voucher.view.writer;

import com.prgmrs.voucher.dto.response.WalletResponse;
import com.prgmrs.voucher.enums.WalletSelectionType;
import com.prgmrs.voucher.model.Wallet;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Component
public class ConsoleWalletWriter {

    public void write(String message) {
        System.out.println(message);
    }

    public void showWalletType() {
        write("=== Choosing wallet type ===");
        write("Type 'assign' to assign voucher to a user");
        write("Type 'free' to deallocate voucher from a user.");
        write("Type 'back' to return to previous step");
    }

    public void showNameUser(WalletSelectionType walletSelectionType) {
        switch(walletSelectionType) {
            case ASSIGN_VOUCHER -> {
                write("=== Selecting a user for wallet ===");
                write("Type existing username to assign voucher");
            }
            case FREE_VOUCHER -> {
                write("=== Selecting a user to take away from ===");
                write("Type existing username to take voucher away from");
            }
        }
    }

    public void showNumberVoucher(WalletSelectionType walletSelectionType) {
        switch(walletSelectionType) {
            case ASSIGN_VOUCHER -> {
                write("=== Selecting a voucher to assign ===");
                write("Type row number of the voucher you want to assign");
            }
            case FREE_VOUCHER -> {
                write("=== Selecting a voucher to free ===");
                write("Type row number of the voucher you want to free");
            }
        }
    }

    public void showWalletResult(WalletResponse walletResponse, WalletSelectionType walletSelectionType) {
        Wallet wallet = walletResponse.getWallet();

        switch(walletSelectionType) {
            case ASSIGN_VOUCHER -> write("=== Successfully assigned the voucher to the user ===");
            case FREE_VOUCHER -> write("=== Successfully freed the voucher from user ===");

        }

        write(MessageFormat.format("user id : {0}", wallet.getUserId()));
        write(MessageFormat.format("username : {0}", walletResponse.getUsername()));
        write(MessageFormat.format("voucher id: {0}", wallet.getVoucherId()));
    }
}
