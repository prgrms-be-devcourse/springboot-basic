package org.prgrms.kdt.engine.io;

import org.prgrms.kdt.engine.voucher.domain.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.*;

public class Console implements Input, Output {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Logger logger = LoggerFactory.getLogger(Console.class);

    @Override
    public String inputCommand(String prompt) {
        System.out.print(prompt);
        var command = scanner.nextLine();
        return command.replaceAll(" ", "_");
    }

    @Override
    public void help() {
        System.out.println("=== Voucher Program ===");
        System.out.println("Type 'create' to create a new voucher.");
        System.out.println("Type 'list' to list all vouchers.");
        System.out.println("Type 'allocate customer' to allocate voucher to a customer.");
        System.out.println("Type 'list customer vouchers' to list all vouchers of the customer.");
        System.out.println("Type 'delete customer voucher' to delete all vouchers of the customer.");
        System.out.println("Type 'get voucher owner' to show owner of the voucher");
        System.out.println("Type 'exit' to exit the program.");
    }

    @Override
    public void showVoucherOptions() {
        System.out.print("'fixed' for fixed voucher, 'percent' for percent voucher : ");
    }

    @Override
    public void printIllegalInputError() {
        logger.warn("illegalInputError");
        System.out.println("Wrong Input");
    }

    @Override
    public void createVoucher(Voucher voucher) {
        System.out.println("Created Voucher " + voucher);
    }

    @Override
    public void listVoucher(Map<UUID, Voucher> voucherList) {
        for (Voucher voucher : voucherList.values())
            System.out.println(voucher);
    }

    @Override
    public void allocateCustomer(String[] uuids) {
        String voucherId = uuids[0];
        String customerId = uuids[1];
        System.out.println(MessageFormat.format("Voucher : {0} is allocated to Customer {1}", voucherId, customerId));
    }

    @Override
    public void deleteCustomerVoucher(String customerUUID) {
        UUID customerId = UUID.fromString(customerUUID);
        System.out.println("Deleted All Vouchers of " + customerUUID);
    }

    @Override
    public void printVoucherListNotFoundError() {
        logger.warn("voucherListNotFoundError");
        System.out.println("No Vouchers Found");
    }
}
