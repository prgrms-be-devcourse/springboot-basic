package org.programmers.springbootbasic.io;

import org.programmers.springbootbasic.data.VoucherMainMenuCommand;
import org.programmers.springbootbasic.domain.Voucher;
import org.programmers.springbootbasic.dto.VoucherInputDto;
import org.programmers.springbootbasic.util.ConstantMessageUtil;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;


@Component
public class Console implements Input, Output{

    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    @Override
    public VoucherMainMenuCommand getVoucherMainMenuInput(String inputMessage) throws IOException {
        System.out.print(inputMessage);
        return VoucherMainMenuCommand.valueOfCommand(br.readLine());
    }

    @Override
    public VoucherInputDto getVoucherCreateMenuInput(String inputMessage) throws IOException {
        // type, amount 입력
        System.out.println(inputMessage);
        System.out.print(ConstantMessageUtil.TYPE_VOUCHER_INFO_TYPE);
        String type = br.readLine().strip();
        System.out.print(ConstantMessageUtil.TYPE_VOUCHER_INFO_AMOUNT);
        long amount = Long.parseLong(br.readLine());
        return new VoucherInputDto(type, amount);
    }

    @Override
    public void printMenu(String menu) {
        System.out.println(menu);
    }

    @Override
    public void printError() {
        System.out.println(ConstantMessageUtil.WRONG_INPUT);
    }

    @Override
    public void printVouchers(List<Voucher> vouchers) {

    }
}
