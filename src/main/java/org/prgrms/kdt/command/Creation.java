package org.prgrms.kdt.command;

import org.prgrms.kdt.exception.InvalidIOMessageException;
import org.prgrms.kdt.io.Input;
import org.prgrms.kdt.io.Output;
import org.prgrms.kdt.voucher.Voucher;
import org.prgrms.kdt.voucher.VoucherService;
import org.prgrms.kdt.voucher.VoucherType;

import java.text.MessageFormat;
import java.util.Optional;

public class Creation implements Command{
    private final Input inputStream;
    private final Output outputStream;
    private final VoucherService voucherService;

    public Creation(Input inputStream, Output outputStream, VoucherService voucherService){
        this.inputStream = inputStream;
        this.outputStream = outputStream;
        this.voucherService=voucherService;
    }

    @Override
    public void doCommands() throws InvalidIOMessageException {
        var voucher = createVoucher();
        if (voucher.isEmpty()) {
            outputStream.write("Voucher Creation Fail\n\n");
            return;
        }
        outputStream.write("Voucher Creation Success\n\n");
    }

    private Optional<Voucher> createVoucher() throws InvalidIOMessageException {
        printVoucherList();

        int inputVoucherNumber = Integer.parseInt(inputStream.readLine()); //printVoucherList에서 보여준 바우처 번호를 입력받도록 함.
        VoucherType voucherType = VoucherType.NONE;

        for(var tmpVoucherType : VoucherType.values()){
            if(tmpVoucherType.ordinal() == inputVoucherNumber - 1){
                voucherType = tmpVoucherType;
                break;
            }
        }

        if(voucherType == VoucherType.NONE) {
            outputStream.write("canceled\n");
            return Optional.empty();
        }

        outputStream.write("Please input new Voucher's value: ");
        long size = Long.parseLong(inputStream.readLine());
        // TODO: 값 범위에 대한 입력 제한.

        var newVoucher = voucherService.save(voucherType, size);

        return newVoucher;
    }

    private void printVoucherList() throws InvalidIOMessageException {
        outputStream.write("Select Voucher Number\n");

        for (var voucherType : VoucherType.values()) {
            int voucherNumber = voucherType.ordinal() + 1;
            outputStream.write(MessageFormat.format("   {0}. {1}\n", voucherNumber, voucherType));
        }
    }
}
