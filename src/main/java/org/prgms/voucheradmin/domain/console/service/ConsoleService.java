package org.prgms.voucheradmin.domain.console.service;

import static org.prgms.voucheradmin.domain.console.service.enums.CommandAboutVoucher.*;
import static org.prgms.voucheradmin.domain.voucher.entity.vo.VoucherType.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import org.prgms.voucheradmin.domain.console.service.enums.Command;
import org.prgms.voucheradmin.domain.console.service.enums.CommandAboutVoucher;
import org.prgms.voucheradmin.domain.customer.dto.CustomerDto;
import org.prgms.voucheradmin.domain.voucher.dto.VoucherCreateReqDto;
import org.prgms.voucheradmin.domain.voucher.entity.Voucher;
import org.prgms.voucheradmin.domain.voucher.entity.vo.VoucherType;
import org.prgms.voucheradmin.global.exception.WrongInputException;
import org.springframework.stereotype.Service;

/**
 * 입출력을 담당하는 클래스입니다.
 **/
@Service
public class ConsoleService {
    private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public void showCommandList() {
        StringBuilder commandListBuilder = new StringBuilder();

        commandListBuilder
                .append("\n=== Voucher Program ===\n")
                .append("Type exit to exit the program.\n")
                .append("Type voucher to CRUD about voucher.\n")
                .append("Type customer to CRUD about customer.\n")
                .append("Type blacklist to list all blacklisted customers.\n");

        System.out.println(commandListBuilder);
        System.out.print("command> ");
    }

    public void showCommandAboutVoucher() {
        StringBuilder voucherCommandListBuilder = new StringBuilder();
        Arrays.stream(CommandAboutVoucher.values())
                .forEach(commandAboutVoucher -> voucherCommandListBuilder.append(commandAboutVoucher.toString()).append("\n"));

        System.out.print(voucherCommandListBuilder);
    }


    public void showVoucherType() {
        StringBuilder voucherTypeStrBuilder = new StringBuilder();

        voucherTypeStrBuilder.append("\n");
        Arrays.stream(VoucherType.values()).forEach(voucherType -> voucherTypeStrBuilder.append(voucherType.toString()).append("\n"));
        voucherTypeStrBuilder.append("\nvoucher type> ");

        System.out.print(voucherTypeStrBuilder);
    }

    public void showVoucherCreated(Voucher createdVoucher) {
        System.out.println(createdVoucher + " created");
    }

    public void showVoucherList(List<Voucher> vouchers) {
        vouchers.forEach(voucher -> System.out.println(voucher));
    }

    public void showBlacklist(List<CustomerDto> blackListedCustomers) {
        blackListedCustomers.forEach(blackListedCustomer -> System.out.println(blackListedCustomer));
    }

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
        String selectedCommandId = br.readLine().trim();
        System.out.println();

        return findCommandAboutVoucher(selectedCommandId).orElseThrow(WrongInputException::new);
    }

    public VoucherType selectVoucherType() throws IOException, WrongInputException {
        String selectedVoucherTypeId = br.readLine().trim();
        System.out.println();

        return findVoucherType(selectedVoucherTypeId).orElseThrow(WrongInputException::new);
    }

    public VoucherCreateReqDto inputAmount(VoucherType voucherType) throws IOException, WrongInputException {
        System.out.print(voucherType == FIXED_AMOUNT ? "amount> " : "percent> ");

        try {
            long input = Long.parseLong(br.readLine().trim());

            if (input < 0 || (voucherType == PERCENTAGE_DISCOUNT && input > 100)) {
                throw new WrongInputException();
            }

            return new VoucherCreateReqDto(voucherType, input);
        } catch (NumberFormatException numberFormatException) {
            throw new WrongInputException();
        }
    }
}
