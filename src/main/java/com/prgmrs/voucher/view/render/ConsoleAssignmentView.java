package com.prgmrs.voucher.view.render;

import com.prgmrs.voucher.controller.AssignmentController;
import com.prgmrs.voucher.controller.UserController;
import com.prgmrs.voucher.controller.VoucherController;
import com.prgmrs.voucher.dto.request.AssignmentRequest;
import com.prgmrs.voucher.dto.response.AssignmentResponse;
import com.prgmrs.voucher.dto.response.UserListResponse;
import com.prgmrs.voucher.dto.response.VoucherListResponse;
import com.prgmrs.voucher.enums.AssignmentSelectionType;
import com.prgmrs.voucher.exception.NoSuchChoiceException;
import com.prgmrs.voucher.exception.WrongRangeFormatException;
import com.prgmrs.voucher.view.ConsoleReader;
import com.prgmrs.voucher.view.writer.ConsoleAssignmentWriter;
import com.prgmrs.voucher.view.writer.ConsoleListWriter;
import org.springframework.stereotype.Component;

@Component
public class ConsoleAssignmentView {
    private final ConsoleAssignmentWriter consoleAssignmentWriter;
    private final ConsoleReader consoleReader;
    private final ConsoleListWriter consoleListWriter;
    private final UserController userController;
    private final VoucherController voucherController;
    private final AssignmentController assignmentController;

    public ConsoleAssignmentView(ConsoleAssignmentWriter consoleAssignmentWriter, ConsoleReader consoleReader, ConsoleListWriter consoleListWriter, UserController userController, VoucherController voucherController, AssignmentController assignmentController) {
        this.consoleAssignmentWriter = consoleAssignmentWriter;
        this.consoleReader = consoleReader;
        this.consoleListWriter = consoleListWriter;
        this.userController = userController;
        this.voucherController = voucherController;
        this.assignmentController = assignmentController;
    }

    void selectAssignmentType() {
        boolean continueRunning = true;
        while (continueRunning) {
            consoleAssignmentWriter.showAssignmentType();
            try {
                AssignmentSelectionType assignmentSelectionType = AssignmentSelectionType.of(consoleReader.read());
                redirectAssignment(assignmentSelectionType);
                continueRunning = false;
            } catch (NoSuchChoiceException e) {
                consoleAssignmentWriter.write("such assignment type not exist");
            }
        }
    }

    private void redirectAssignment(AssignmentSelectionType assignmentSelectionType) {
        switch (assignmentSelectionType) {
            case ASSIGN_VOUCHER -> selectUser();
            case FREE_VOUCHER -> selectUserWithVoucher();
            case BACK -> {}
        }
    }

    private void selectUser() {
        boolean continueRunning = true;
        while (continueRunning) {
            UserListResponse userListResponse = userController.getAllUsers();
            consoleListWriter.showUserList(userListResponse);
            consoleAssignmentWriter.showNameUser(AssignmentSelectionType.ASSIGN_VOUCHER);
            String username = consoleReader.read();

            VoucherListResponse voucherListResponse = voucherController.getNotAssignedVoucher();
            consoleListWriter.showVoucherList(voucherListResponse);

            consoleAssignmentWriter.showNumberVoucher(AssignmentSelectionType.ASSIGN_VOUCHER);
            String order = consoleReader.read();

            AssignmentRequest assignmentRequest = new AssignmentRequest(username, order, voucherListResponse.getVoucherList());
            try {
                AssignmentResponse assignmentResponse = assignmentController.assignVoucher(assignmentRequest);
                consoleAssignmentWriter.showAssignmentResult(assignmentResponse, AssignmentSelectionType.ASSIGN_VOUCHER);
                continueRunning = false;
            } catch (WrongRangeFormatException e) {
                consoleAssignmentWriter.write("incorrect format or value out of range");
            }
        }
    }

    private void selectUserWithVoucher() {
        boolean continueRunning = true;
        while (continueRunning) {
            UserListResponse userListResponse = userController.getUserListWithVoucherAssigned();
            consoleListWriter.showUserList(userListResponse);
            consoleAssignmentWriter.showNameUser(AssignmentSelectionType.FREE_VOUCHER);
            String username = consoleReader.read();

            VoucherListResponse voucherListResponse = voucherController.getAssignedVoucherListByUsername(username);
            consoleListWriter.showVoucherList(voucherListResponse);

            consoleAssignmentWriter.showNumberVoucher(AssignmentSelectionType.FREE_VOUCHER);
            String order = consoleReader.read();

            AssignmentRequest assignmentRequest = new AssignmentRequest(username, order, voucherListResponse.getVoucherList());
            try {
                AssignmentResponse assignmentResponse = assignmentController.freeVoucher(assignmentRequest);
                consoleAssignmentWriter.showAssignmentResult(assignmentResponse, AssignmentSelectionType.FREE_VOUCHER);
                continueRunning = false;
            } catch (WrongRangeFormatException e) {
                consoleAssignmentWriter.write("incorrect format or value out of range");
            }
        }
    }
}
