package org.programmers.program;

import org.programmers.program.io.Input;
import org.programmers.program.io.Output;
import org.programmers.program.voucher.repository.VoucherRepository;
import org.programmers.program.voucher.service.VoucherService;

import java.io.IOException;

import static org.programmers.program.io.Message.*;

public class VoucherProgram implements Runnable {

    private final Input input;
    private final Output output;
    private final VoucherService voucherService;
    private final VoucherRepository voucherRepository;


    public VoucherProgram(Input input, Output output, VoucherService voucherService, VoucherRepository voucherRepository) {
        this.input = input;
        this.output = output;
        this.voucherService = voucherService;
        this.voucherRepository = voucherRepository;
    }

    @Override
    public void run() {
        output.printString(WELCOME_MESSAGE.getMessage());
        while(true){
            output.printString(SELECTION_MESSAGE.getMessage());
            String in = null;
            try {
                in = input.input("Instruction : ");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if(in.equals("bye"))
                break;

        }
    }
    private void create(){

    }

    private void list(){

    }
}
