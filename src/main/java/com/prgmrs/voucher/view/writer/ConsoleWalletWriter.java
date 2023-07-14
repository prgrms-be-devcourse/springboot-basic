package com.prgmrs.voucher.view.writer;

import com.prgmrs.voucher.dto.response.WalletResponse;
import com.prgmrs.voucher.enums.WalletAssignmentSelectionType;
import com.prgmrs.voucher.model.Wallet;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Component
public class ConsoleWalletWriter {

    public void write(String message) {
        System.out.println(message);
    }

    public void showVoucherAssignmentSelectionType() {
        write("=== Choosing voucher assignment type ===");
        write("Type 'assign' to assign voucher to a user");
        write("Type 'remove' to deallocate voucher from a user.");
        write("Type 'back' to return to previous step");
    }

    public void showNameUser(WalletAssignmentSelectionType walletAssignmentSelectionType) {
        switch (walletAssignmentSelectionType) {
            case ASSIGN_VOUCHER -> {
                write("=== Selecting a user for wallet ===");
                write("Type existing username to assign voucher");
            }
            case REMOVE_VOUCHER -> {
                write("=== Selecting a user to take away from ===");
                write("Type existing username to take voucher away from");
            }
        }
    }

    public void showNumberVoucher(WalletAssignmentSelectionType walletAssignmentSelectionType) {
        switch (walletAssignmentSelectionType) {
            case ASSIGN_VOUCHER -> {
                write("=== Selecting a voucher to assign ===");
                write("Type row number of the voucher you want to assign");
            }
            case REMOVE_VOUCHER -> {
                write("=== Selecting a voucher to remove ===");
                write("Type row number of the voucher you want to remove");
            }
        }
    }

    public void showWalletResult(WalletResponse walletResponse, WalletAssignmentSelectionType walletAssignmentSelectionType) {
        Wallet wallet = walletResponse.wallet();

        switch (walletAssignmentSelectionType) {
            case ASSIGN_VOUCHER -> write("=== Successfully assigned the voucher to the user ===");
            case REMOVE_VOUCHER -> write("=== Successfully remove the voucher from user ===");

        }

        write(MessageFormat.format("user id : {0}", wallet.userId()));
        write(MessageFormat.format("username : {0}", walletResponse.username()));
        write(MessageFormat.format("voucher id: {0}", wallet.voucherId()));
    }
}
