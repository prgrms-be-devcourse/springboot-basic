package org.programmers.spbw1;

import org.programmers.spbw1.io.Input;
import org.programmers.spbw1.io.Output;
import org.programmers.spbw1.voucher.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Optional;

public class VoucherProgram implements Runnable {
    private final Input input;
    private final Output output;
    private final VoucherService voucherService;
    private final VoucherRepository voucherRepository;
    private static final Logger logger = LoggerFactory.getLogger(VoucherProgram.class);

    public VoucherProgram(Input input, Output output, VoucherRepository voucherRepository){
        this.input = input;
        this.output = output;
        this.voucherRepository = voucherRepository;
        voucherService = new VoucherService(voucherRepository);
    }

    @Override
    public void run() {
        while (true){
            output.initSelect();
            String in = "";

            try {
                in = input.input("Instruction : ");
                if(in.equals("exit")) {
                    output.bye();
                    break;
                }

                Method m = Class.forName(this.getClass().getName()).getDeclaredMethod(in);
                m.invoke(this);

            }catch (Exception e){
                output.invalidInstruction(in);
                logger.error("invalid instruction : " + in);
            }
        }
    }
    private void create() throws Exception{
        String type = input.input("1. Fixed Amount\n2. Percent\nChoose 1 or 2 : ");
        Optional<VoucherType> voucherType = VoucherType.getVoucherTypeBySelection(type);

        if (voucherType.isEmpty()){
            logger.error("invalid type selected : " + type);
            output.invalidVoucherSelected();
            return;
        }

        String discountAmount = input.input("discount amount " + VoucherType.getRange(voucherType.get()) + " : ");
        long amount = 0L;
        try {
            amount = Long.parseLong(discountAmount);
        }catch (NumberFormatException e){
            logger.error("NumberFormatException : " + discountAmount);
            output.numFormatException();
            return;
        }

        if (!VoucherType.validRange(voucherType.get(), amount)){
            logger.error("invalid range ERROR, voucher type : " + voucherType.get().toString() + ", tried : " + amount);
            output.invalidRange(voucherType.get());
            return;
        }

        Voucher voucher = voucherService.createVoucher(voucherType.get(), amount);
        logger.info("voucher created, voucher info : " + voucher.toString());
        output.voucherCreated(voucher);
    }

    private void list(){
        logger.info("list_called, stored voucher num : " + voucherRepository.getStoredVoucherNum());
        output.listCalled(voucherRepository.getStoredVoucherNum());
        voucherRepository.showAllVouchers();
    }
}
