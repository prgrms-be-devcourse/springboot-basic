package org.programmers.program;

import org.programmers.program.customer.service.CustomerService;
import org.programmers.program.io.Input;
import org.programmers.program.io.Output;

import org.programmers.program.voucher.controller.VoucherDto;
import org.programmers.program.voucher.model.VoucherType;
import org.programmers.program.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;


import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import static org.programmers.program.io.Message.*;

public class VoucherProgram implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(VoucherProgram.class);
    private final Input input = new Input();
    private final Output output = new Output();
    private final VoucherService voucherService;
    private final CustomerService customerService;

    public VoucherProgram(VoucherService voucherService, CustomerService customerService){
         this.voucherService = voucherService;
         this.customerService = customerService;
    }

    @Override
    public void run(String... args) {
        output.printString(WELCOME_MESSAGE.getMessage());
        while(true){
            output.printString(SELECTION_MESSAGE.getMessage());
            String in = null;
            try {
                in = input.input(INSTRUCTION_SELECTION.getMessage());

                switch (in) {
                    case "create"   :   create();
                    case "list"     :   list();
                    case "exit"     :   break;
                    default         :   output.printString(WRONG_INSTRUCTION_MESSAGE.getMessage());
                }
            } catch (IOException io) {
                throw new RuntimeException(io);
            }
        }
    }
    private void create(){
        String in = null;
        Optional<VoucherType> type;
        Long discountAmount;
        try {
            in = input.input(VOUCHER_TYPE.getMessage());
            type = VoucherType.getVoucherType(in);
            if (type.isEmpty()){
                output.printString(WRONG_VOUCHER_TYPE.getMessage());
                return;
            }
            in = input.input(type.get().getRange());
            discountAmount = Long.parseLong(in);

        }catch (IOException io){
            throw new RuntimeException(io);
        }

        voucherService.createVoucher(type.get(), UUID.randomUUID(), discountAmount);
    }

    private void list(){
        var allVouchers = voucherService.getAllVouchers();
        allVouchers.forEach(v -> output.printString(v.toString()));
    }
}
