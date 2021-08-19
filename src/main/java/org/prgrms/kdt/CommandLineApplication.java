package org.prgrms.kdt;

import org.prgrms.kdt.exception.InvalidIOMessageException;
import org.prgrms.kdt.io.Input;
import org.prgrms.kdt.io.Output;
import org.prgrms.kdt.voucher.Voucher;
import org.prgrms.kdt.voucher.VoucherService;
import org.prgrms.kdt.voucher.VoucherType;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

public class CommandLineApplication {
    private final Input inputStream;
    private final Output outputStream;
    private final VoucherService voucherService;
    private Map<Integer, VoucherType> voucherMap = new HashMap<>();

    public CommandLineApplication(Input inputStream, Output outputStream, VoucherService voucherService) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
        this.voucherService = voucherService;
    }

    public void run(String startMessage) throws InvalidIOMessageException {
        String command;

        while (true) {
            outputStream.write(startMessage);
            command = inputStream.readLine().toUpperCase();

            if (Commands.CREATE.toString().equals(command)) {
                var voucher = createVoucher();
                if (voucher == null) {
                    outputStream.write("Voucher Creation Fail\n");
                    continue;
                }
                outputStream.write("Voucher Creation Success\n\n");
            }
            else if (Commands.LIST.toString().equals(command)) {
                if (listAllVouchers() == false) {
                    outputStream.write("There are not any vouchers\n\n");
                    continue;
                }
            }
            else if (Commands.EXIT.toString().equals(command))
                break;
        }
    }

    private boolean listAllVouchers() throws InvalidIOMessageException {
        var vouchers = voucherService.getAllVouchers();

        if (vouchers.size() == 0) {
            return false;
        }

        for (var voucher : vouchers) {
            outputStream.write(voucher.toString());
        }
        outputStream.write("\n");

        return true;
    }

    private Voucher createVoucher() throws InvalidIOMessageException {
        printVoucherList();
        int number = Integer.parseInt(inputStream.readLine()); //printVoucherList에서 보여준 바우처 번호를 입력받도록 함.
        var voucherType = voucherMap.getOrDefault(number, null); // 입력받은 바우처 번호에 대한 바우처 타입을가져옴

        if (voucherType == null) {
            return null;
        }

        outputStream.write("Please input new Voucher's value: ");
        long size = Long.parseLong(inputStream.readLine());
        // TODO: 값 범위에 대한 입력 제한.

        Voucher newVoucher = null;
        if ((newVoucher = voucherService.save(voucherType, size)) == null) {
            return null;
        }

        return newVoucher;
    }

    private void printVoucherList() throws InvalidIOMessageException {
        int voucherNumber = 1;

        outputStream.write("Select Voucher Number\n");
        for (var voucherType : VoucherType.values()) {
            outputStream.write(MessageFormat.format("   {0}. {1}\n", voucherNumber, voucherType));

            voucherMap.put(voucherNumber, voucherType);
            voucherNumber++;
        }
    }
}
