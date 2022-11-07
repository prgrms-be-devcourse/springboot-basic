package org.programmers.spbw1;

import org.programmers.spbw1.io.Input;
import org.programmers.spbw1.io.Output;
import org.programmers.spbw1.voucher.Voucher;
import org.programmers.spbw1.voucher.VoucherService;

import java.io.IOException;

public class VoucherProgram implements Runnable {
    private final Input input;
    private final Output output;
    private final VoucherService voucherService;

    public VoucherProgram(Input input, Output output, VoucherService voucherService){
        this.voucherService = voucherService;
        this.input = input;
        this.output = output;
    }

    @Override
    public void run() {
        while (true){
            output.initSelect();
            String in = null;
            try {
                in = input.input("");
            } catch (IOException e) {
                output.showExceptionTrace(e);
            }
            if(in.equals("create"))
                create();
            else if(in.equals("list"))
                list();
            else if(in.equals("exit")) {
                output.bye();
                break;
            }
            else
                output.invalidInstruction(in);
        }
    }
    private void create(){};
    private void list(){};
}
