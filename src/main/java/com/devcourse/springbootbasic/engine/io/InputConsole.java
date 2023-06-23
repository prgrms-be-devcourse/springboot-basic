package com.devcourse.springbootbasic.engine.io;

import com.devcourse.springbootbasic.engine.exception.InvalidDataException;
import com.devcourse.springbootbasic.engine.model.ListMenu;
import com.devcourse.springbootbasic.engine.model.Menu;
import com.devcourse.springbootbasic.engine.model.VoucherType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

@Component
public class InputConsole {

    private final Scanner scanner = new Scanner(System.in);

    @Autowired
    private ConfigurableApplicationContext applicationContext;

    public Menu inputMenu() {
        System.out.println("\n=== Voucher Program ===\n" +
                        "Type exit(0) to exit the program.\n" +
                        "Type create(1) to create a new voucher.\n" +
                        "Type list(2) to list all vouchers.");
        System.out.print("Menu Selection: ");
        return Menu.getMenu(inputString());
    }

    private String inputString() {
        return scanner.nextLine()
                .toLowerCase();
    }

    public VoucherType inputVoucherType() {
        System.out.println("\n--- Voucher Type ---\n" +
                "1. FixedAmountVoucher\n" +
                "2. PercentDiscountVoucher");
        System.out.print("Voucher Selection: ");
        return VoucherType.getVoucherType(inputString());
    }

    public ListMenu inputListMenu() {
        System.out.println("\n--- List Menu ---\n" +
                "1. Voucher List\n" +
                "2. Black Customer List");
        System.out.print("List Menu Selection: ");
        return ListMenu.getListMenu(inputString());
    }

    public double inputVoucherDiscount(VoucherType voucherType) {
        System.out.print("Voucher Discount " + voucherType.getTypeString() + ": ");
        return Stream.of(inputString())
                .map(Double::parseDouble)
                .filter(d -> d >= 0.0 && d <= 100.0)
                .findAny()
                .orElseThrow(() -> new InvalidDataException(InvalidDataException.INVALID_DISCOUNT_VALUE));
    }

    public List<String> getBlackCustomers() throws IOException {
        return Files.readAllLines(getBlackCustomerFile().toPath());
    }

    private File getBlackCustomerFile() throws IOException {
        return applicationContext.getResource("file:src/main/resources/customer_blacklist.csv")
                .getFile();
    }

}
