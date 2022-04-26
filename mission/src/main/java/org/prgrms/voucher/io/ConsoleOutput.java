package org.prgrms.voucher.io;

import org.prgrms.voucher.CommandType;
import org.prgrms.voucher.dto.VoucherDto;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class ConsoleOutput implements Output {

    private final String COMMAND_ERROR = "Type error, please retry";

    @Override
    public void printPrompt() {

        System.out.println("====Voucher Program====");
        Arrays.stream(CommandType.values())
                .forEach(v -> System.out.println(v.getPrompt()));
    }

    @Override
    public void printInvalidInputError() {

        System.out.println(COMMAND_ERROR);
    }

    @Override
    public void printVoucherType() {

        System.out.print("""
                1.FixedAmount
                2.PercentDiscount (0 ~ 100)
                """);
        System.out.print("Select : ");
    }

    @Override
    public void printVoucherDiscountType() {

        System.out.print("Discount : ");
    }

    @Override
    public void printVoucherList(List<VoucherDto.VoucherResponse> data) {

        data.forEach(v -> System.out.println(v.toString()));
    }
}