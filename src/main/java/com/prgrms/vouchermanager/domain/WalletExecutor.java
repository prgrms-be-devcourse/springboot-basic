package com.prgrms.vouchermanager.domain;

import com.prgrms.vouchermanager.controller.WalletController;
import com.prgrms.vouchermanager.exception.NotCorrectId;
import com.prgrms.vouchermanager.io.ConsolePrint;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

@Component
public class WalletExecutor {

    private final ConsolePrint consolePrint;
    private final WalletController controller;
    private final Scanner sc = new Scanner(System.in);

    public WalletExecutor(ConsolePrint consolePrint, WalletController controller) {
        this.consolePrint = consolePrint;
        this.controller = controller;
    }
    public void create() {
        try {
            consolePrint.printGetCustomerId();
            UUID customerId = UUID.fromString(sc.nextLine());
            consolePrint.printGetVoucherId();
            UUID voucherId = UUID.fromString(sc.nextLine());
            controller.create(customerId, voucherId);
        } catch (IllegalArgumentException e) {
            throw new NotCorrectId();
        }
    }

    public void findByCustomerId() {
        try {
            consolePrint.printGetCustomerId();
            UUID customerId = UUID.fromString(sc.nextLine());
            controller.findByCustomerId(customerId);
        } catch (IllegalArgumentException e) {
            throw new NotCorrectId();
        }
    }

    public void findByVoucherId() {
        try {
            consolePrint.printGetVoucherId();
            UUID customerId = UUID.fromString(sc.nextLine());
            controller.findByVoucherId(customerId);
        } catch (IllegalArgumentException e) {
            throw new NotCorrectId();
        }
    }

    public void delete() {
        try {
            consolePrint.printGetCustomerId();
            UUID customerId = UUID.fromString(sc.nextLine());
            consolePrint.printGetVoucherId();
            UUID voucherId = UUID.fromString(sc.nextLine());
            controller.delete(customerId, voucherId);
        } catch (IllegalArgumentException e) {
            throw new NotCorrectId();
        }
    }
}
