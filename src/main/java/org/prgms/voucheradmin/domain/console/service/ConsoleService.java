package org.prgms.voucheradmin.domain.console.service;

import static org.prgms.voucheradmin.domain.voucher.entity.vo.VoucherType.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

import org.prgms.voucheradmin.domain.console.Command;
import org.prgms.voucheradmin.domain.voucher.dto.VoucherInputDto;
import org.prgms.voucheradmin.domain.voucher.entity.vo.VoucherType;
import org.prgms.voucheradmin.global.exception.WrongInputException;
import org.springframework.stereotype.Service;

/**
 * 입출력을 담당하는 클래스입니다.
 **/
@Service
public class ConsoleService {
    private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    /**
     * 선택 가능한 명령들을 사용자에게 보여주는 메서드입니다.
     **/
    public void showCommandList() {
        StringBuilder commandListBuilder = new StringBuilder();

        commandListBuilder
                .append("\n=== Voucher Program ===\n")
                .append("Type exit to exit the program.\n")
                .append("Type create to create a new voucher.\n")
                .append("Type list to list all vouchers.\n")
                .append("Type blacklist to list all blacklisted customers.\n");

        System.out.println(commandListBuilder);
        System.out.print("command> ");
    }

    /**
     * 사용자가 명령을 입력하는 메서드입니다.
     * findCommand 메서드를 통하여 옳바른 입력인지 검증하고 옳바른 입력일 경우 Commands를 반환합니다.
     * 만약 옳바른 입력이 아닌 경우 WrongInputException을 발생시킵니다.
     **/
    public Command selectCommand() throws IOException, WrongInputException {
        String selectedCommand = br.readLine().trim().toUpperCase();

        try{
            Command command =  Command.valueOf(selectedCommand);
            return command;
        }catch (IllegalArgumentException illegalArgumentException) {
            throw new WrongInputException();
        }
    }

    /**
     * 생성할 수 있는 바우처의 종류들을 사용자에게 보여주는 메서드입니다.
     **/
    public void showVoucherType() {
        StringBuilder voucherTypeStrBuilder = new StringBuilder();

        voucherTypeStrBuilder.append("\n");
        Arrays.stream(VoucherType.values()).forEach(voucherType -> voucherTypeStrBuilder.append(voucherType.toString()).append("\n"));
        voucherTypeStrBuilder.append("\nvoucher type> ");

        System.out.print(voucherTypeStrBuilder);
    }

    /**
     * 사용자가 생성할 바우처의 종류를 선택하는 메서드입니다.
     * 옳바른 입력일 경우 VoucherTypes를 반환하지만 그렇지 않은 경우 WrongInputException을 발생시킵니다.
     **/
    public VoucherType selectVoucherType() throws IOException, WrongInputException {
        String selectedVoucherTypeId = br.readLine().trim();
        System.out.println();

        return findVoucherType(selectedVoucherTypeId).orElseThrow(WrongInputException::new);
    }

    /**
     * 사용자가 생성할 바우처의 할인 amount 또는 percent를 입력하는 메서드입니다.
     * 정상적으로 입력이 이루어진 경우 VoucherInputDto 반환하지만 그렇지 않은 경우 WrongInputException을 발생시킵니다.
     **/
    public VoucherInputDto inputAmount(VoucherType voucherType) throws IOException, WrongInputException {
        System.out.print(voucherType == FIXED_AMOUNT ? "amount> " : "percent> ");

        try {
            long input = Long.parseLong(br.readLine().trim());

            if(input < 0 || (voucherType == PERCENTAGE_DISCOUNT && input > 100)) {
                throw new WrongInputException();
            }

            return new VoucherInputDto(voucherType, input);
        }catch (NumberFormatException numberFormatException) {
            throw new WrongInputException();
        }
    }
}
