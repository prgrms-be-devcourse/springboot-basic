package com.prgmrs.voucher.view.writer;

import com.prgmrs.voucher.dto.response.AssignmentResponse;
import com.prgmrs.voucher.enums.AssignmentSelectionType;
import com.prgmrs.voucher.model.Assignment;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Component
public class ConsoleAssignmentWriter {

    public void write(String message) {
        System.out.println(message);
    }

    public void showAssignmentType() {
        write("=== Choosing assignment type ===");
        write("Type 'assign' to assign voucher to a user");
        write("Type 'free' to deallocate voucher from a user.");
        write("Type 'back' to return to previous step");
    }

    public void showNameUser(AssignmentSelectionType assignmentSelectionType) {
        switch(assignmentSelectionType) {
            case ASSIGN_VOUCHER -> {
                write("=== Selecting a user for assignment ===");
                write("Type existing username to assign voucher");
            }
            case FREE_VOUCHER -> {
                write("=== Selecting a user to take away from ===");
                write("Type existing username to take voucher away from");
            }
        }
    }

    public void showNumberVoucher(AssignmentSelectionType assignmentSelectionType) {
        switch(assignmentSelectionType) {
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

    public void showAssignmentResult(AssignmentResponse assignmentResponse, AssignmentSelectionType assignmentSelectionType) {
        Assignment assignment = assignmentResponse.getAssignment();

        switch(assignmentSelectionType) {
            case ASSIGN_VOUCHER -> write("=== Successfully assigned a voucher to the user ===");
            case FREE_VOUCHER -> write("=== Successfully freed a voucher to the user ===");

        }

        write(MessageFormat.format("user id : {0}", assignment.getUserId()));
        write(MessageFormat.format("username : {0}", assignmentResponse.getUsername()));
        write(MessageFormat.format("voucher id: {0}", assignment.getVoucherId()));
    }
}
