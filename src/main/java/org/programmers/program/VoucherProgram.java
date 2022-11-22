package org.programmers.program;

import org.programmers.program.io.Input;
import org.programmers.program.io.Output;

import org.programmers.program.voucher.model.FixedAmountVoucher;
import org.programmers.program.voucher.model.VoucherType;
import org.programmers.program.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.util.UUID;

import static org.programmers.program.io.Message.*;

@Component
public class VoucherProgram implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(VoucherProgram.class);
    private final Input input = new Input();
    private final Output output = new Output();
    private final VoucherService voucherService;

    public VoucherProgram(VoucherService voucherService){
         this.voucherService = voucherService;
    }

    @Override
    public void run(String... args) {
        output.printString(WELCOME_MESSAGE.getMessage());
        while(true){
            output.printString(SELECTION_MESSAGE.getMessage());
            String in = null;
            try {
                in = input.input("Instruction : ");
                voucherService.createVoucher(VoucherType.FIXED, UUID.randomUUID(), 30L);
                if(in.equals("exit"))
                    break;
//                if(in.equals("create"))
//                    create();
//                else if (in.equals("list"))
//                    list();
//                else if(in.equals("exit"))
//                    break;
//                else{
//                    output.printString("wrong instruction");
//                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private void create(){
    }

    private void list(){
        var allVouchers = voucherService.getAllVouchers();
        allVouchers.forEach(v -> output.printString(v.toString()));
    }
}
