package com.prgrms.voucher_manager;

import com.prgrms.voucher_manager.exception.EmptyVoucherException;
import com.prgrms.voucher_manager.io.Input;
import com.prgrms.voucher_manager.io.Output;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class VoucherManagerConsole {
    private final Input input;
    private final Output output;
    private final VoucherManager voucherManager;

    public VoucherManagerConsole(Input input, Output output, VoucherManager voucherManager) {
        this.input = input;
        this.output = output;
        this.voucherManager = voucherManager;
    }

    public void run() throws IOException {
        while(true){
            output.consoleMenu();
            String command = input.selectOption();
            switch (command){
                case "create":
                    output.selectVoucher();
                    String voucherType = input.selectOption();
                    try{
                        voucherManager.checkType(voucherType);
                        Long value = Long.parseLong(input.input("value : "));
                        voucherManager.createVoucher(voucherType,value);
                    }catch (IllegalArgumentException e){
                        output.wrongInput();
                    }
                    break;
                case "list":
                    try{
                      voucherManager.getFindAllVoucher();
                    }catch(EmptyVoucherException e){
                        output.emptyVoucherRepository();
                    }
                    break;
                case "exit":
                    output.exitProgram();
                    return;
                default:
                    output.wrongInput();
                    break;
            }
        }
    }
}
