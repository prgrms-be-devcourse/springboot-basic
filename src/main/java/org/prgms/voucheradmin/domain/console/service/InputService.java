package org.prgms.voucheradmin.domain.console.service;

import org.prgms.voucheradmin.domain.console.service.enums.Command;
import org.prgms.voucheradmin.domain.console.service.enums.CommandAboutVoucher;
import org.prgms.voucheradmin.domain.voucher.entity.vo.VoucherType;
import org.prgms.voucheradmin.global.exception.WrongInputException;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;

import static org.prgms.voucheradmin.domain.console.service.enums.CommandAboutVoucher.findCommandAboutVoucher;
import static org.prgms.voucheradmin.domain.voucher.entity.vo.VoucherType.*;

@Service
public class InputService {
    private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public Command selectCommand() throws IOException, WrongInputException {
        String selectedCommand = br.readLine().trim().toUpperCase();

        try {
            Command command = Command.valueOf(selectedCommand);
            return command;
        } catch (IllegalArgumentException illegalArgumentException) {
            throw new WrongInputException();
        }
    }

    public CommandAboutVoucher selectCommandAboutVoucher() throws IOException, WrongInputException {
        System.out.print("voucher command> ");
        String selectedCommandId = br.readLine().trim();
        System.out.println();

        return findCommandAboutVoucher(selectedCommandId).orElseThrow(WrongInputException::new);
    }

    public UUID inputVoucherId() throws IOException{
        System.out.print("input voucher ID> ");
        UUID inputVoucherID = UUID.fromString(br.readLine().trim());
        System.out.println();

        return inputVoucherID;
    }

    public VoucherType selectVoucherType() throws IOException, WrongInputException {
        String selectedVoucherTypeId = br.readLine().trim();
        System.out.println();

        return findVoucherType(selectedVoucherTypeId).orElseThrow(WrongInputException::new);
    }

    public long inputAmount(VoucherType voucherType) throws IOException, WrongInputException {
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
}
