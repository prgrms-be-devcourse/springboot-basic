package org.prgms.voucheradmin.domain.console.service;

import static org.prgms.voucheradmin.domain.console.enums.CommandAboutCustomer.findCommandAboutCustomer;
import static org.prgms.voucheradmin.domain.console.enums.CommandAboutVoucher.findCommandAboutVoucher;
import static org.prgms.voucheradmin.domain.console.enums.CommandAboutVoucherWallet.findCommandAboutVoucherWallet;
import static org.prgms.voucheradmin.domain.voucher.entity.vo.VoucherType.*;
import static org.prgms.voucheradmin.global.util.Util.isValidEmail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;

import org.prgms.voucheradmin.domain.console.enums.Command;
import org.prgms.voucheradmin.domain.console.enums.CommandAboutCustomer;
import org.prgms.voucheradmin.domain.console.enums.CommandAboutVoucher;
import org.prgms.voucheradmin.domain.console.enums.CommandAboutVoucherWallet;
import org.prgms.voucheradmin.domain.voucher.entity.vo.VoucherType;
import org.prgms.voucheradmin.global.exception.customexception.WrongInputException;
import org.springframework.stereotype.Service;

/**
 * 입력을 담당하는 클래스 입니다.
 */
@Service
public class InputService {
    private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public Command selectCommand() throws IOException {
        String selectedCommand = br.readLine().trim().replace(" ", "_").toUpperCase();

        try {
            Command command = Command.valueOf(selectedCommand);
            return command;
        } catch (IllegalArgumentException illegalArgumentException) {
            throw new WrongInputException();
        }
    }

    public CommandAboutVoucher selectCommandAboutVoucher() throws IOException {
        System.out.print("voucher command> ");
        String selectedCommandId = br.readLine().trim();
        System.out.println();

        return findCommandAboutVoucher(selectedCommandId).orElseThrow(WrongInputException::new);
    }

    public UUID inputVoucherId() throws IOException{
        System.out.print("input voucher ID> ");
        UUID inputVoucherId = UUID.fromString(br.readLine().trim());
        System.out.println();

        return inputVoucherId;
    }

    public VoucherType selectVoucherType() throws IOException {
        System.out.print("select voucher type> ");
        String selectedVoucherTypeId = br.readLine().trim();
        System.out.println();

        return findVoucherType(selectedVoucherTypeId).orElseThrow(WrongInputException::new);
    }

    public long inputAmount(VoucherType voucherType) throws IOException {
        System.out.print(voucherType == FIXED_AMOUNT ? "amount> " : "percent> ");

        try {
            long input = Long.parseLong(br.readLine().trim());

            if (input < 0 || (voucherType == PERCENTAGE_DISCOUNT && input > 100)) {
                throw new WrongInputException();
            }

            return input;
        } catch (NumberFormatException numberFormatException) {
            throw new WrongInputException();
        }
    }

    public CommandAboutCustomer selectCommandAboutCustomer() throws IOException {
        System.out.print("customer command> ");
        String selectedCommandId = br.readLine().trim();
        System.out.println();

        return findCommandAboutCustomer(selectedCommandId).orElseThrow(WrongInputException::new);
    }

    public UUID inputCustomerId() throws IOException{
        System.out.print("input customer ID> ");
        UUID inputCustomerId = UUID.fromString(br.readLine().trim());
        System.out.println();

        return inputCustomerId;
    }

    public String inputName() throws IOException {
        System.out.print("input name> ");
        String name = br.readLine().trim();
        System.out.println();

        if(name.isBlank()) {
            throw new WrongInputException("wrong name input");
        }

        return name;
    }

    public String inputEmail() throws IOException {
        System.out.print("input email> ");
        String email = br.readLine().trim();
        System.out.println();

        if(isValidEmail(email)) {
            throw new WrongInputException("wrong email input");
        }

        return email;
    }

    public CommandAboutVoucherWallet selectCommandAboutVoucherWallet() throws IOException {
        System.out.print("voucher wallet command> ");
        String selectedCommandId = br.readLine().trim();
        System.out.println();

        return findCommandAboutVoucherWallet(selectedCommandId).orElseThrow(WrongInputException::new);
    }
}
