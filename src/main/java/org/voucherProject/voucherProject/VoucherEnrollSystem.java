package org.voucherProject.voucherProject;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.voucherProject.voucherProject.controller.voucher.VoucherController;
import org.voucherProject.voucherProject.controller.voucher.VoucherControllerImpl;
import org.voucherProject.voucherProject.entity.voucher.Voucher;
import org.voucherProject.voucherProject.entity.voucher.VoucherType;
import org.voucherProject.voucherProject.io.Console;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Slf4j
@RequiredArgsConstructor
@Component
public class VoucherEnrollSystem implements Runnable {

    private final Console console;
    private final VoucherController voucherController;

    @Override
    public void run() {
        while (true) {
            String inputString = console.input(
                    "Type exit to exit the program.\n" +
                    "Type create to create a new voucher.\n" +
                    "Type list to list all vouchers");
            try {
                validateInput(inputString);
                if (exitSystem(inputString)) break;
                if (createVoucher(inputString)) continue;
                showAllVoucherList(inputString);
            } catch (IllegalArgumentException | IOException e) {
                console.errorMessage();
            }
        }
    }

    protected void validateInput(String inputString) {
        boolean validateInputString = Arrays.stream(InputCommend.values()).map(String::valueOf).anyMatch(v -> v.equals(inputString.toUpperCase()));
        if (!validateInputString) {
            throw new IllegalArgumentException();
        }
    }

    protected boolean exitSystem(String inputString) {
        if ((inputString.equalsIgnoreCase(String.valueOf(InputCommend.EXIT)))) {
            console.endMessage();
            return true;
        }
        return false;
    }

    protected boolean createVoucher(String inputString) throws IOException {
        if ((inputString.equalsIgnoreCase(String.valueOf(InputCommend.CREATE)))) {
            String inputVoucherType = console.input("1. FixedAmountVoucher\n2. PercentDiscountVoucher");

            Optional<VoucherType> voucherType = checkVoucherType(inputVoucherType);
            long inputDiscountAmount = Long.parseLong(console.input("할인 수치를 입력해주세요"));
            voucherController.createVoucher(voucherType.get(), inputDiscountAmount);
            console.completeMessage();
        }
        return false;
    }

    protected void showAllVoucherList(String inputString) throws IOException {
        if ((inputString.equalsIgnoreCase(String.valueOf(InputCommend.LIST)))) {
            List<Voucher> vouchers = voucherController.findAll();
            vouchers.forEach(voucher -> System.out.println("voucher = " + voucher));
        }
    }

    protected Optional<VoucherType> checkVoucherType(String inputVoucherType) {
        Optional<VoucherType> voucherType = Optional.empty();
        int inputVoucherTypeInt = Integer.parseInt(inputVoucherType);

        if (inputVoucherTypeInt == VoucherType.FIXED.getNumber()) {
            voucherType = Optional.of(VoucherType.FIXED);
        }
        if (inputVoucherTypeInt == VoucherType.PERCENT.getNumber()) {
            voucherType = Optional.of(VoucherType.PERCENT);
        }
        if (voucherType.isEmpty()) throw new IllegalArgumentException();

        return voucherType;
    }
}
