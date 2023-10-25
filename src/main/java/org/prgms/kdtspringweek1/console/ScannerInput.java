package org.prgms.kdtspringweek1.console;

import org.prgms.kdtspringweek1.voucher.entity.VoucherType;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ScannerInput implements ConsoleInput {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public SelectFunctionTypeDto getFunctionType() {
        return SelectFunctionTypeDto.getValueByName(scanner.nextLine());
    }

    @Override
    public VoucherType getVoucherType() {
        return SelectVoucherTypeDto.getVoucherTypeByNum(Long.parseLong(scanner.nextLine()));
    }

    @Override
    public Long getDiscountValue() {
        return Long.parseLong(scanner.nextLine());
    }
}
