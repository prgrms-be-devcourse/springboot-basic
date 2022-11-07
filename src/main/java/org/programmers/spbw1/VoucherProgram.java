package org.programmers.spbw1;

import org.programmers.spbw1.io.Input;
import org.programmers.spbw1.io.Output;
import org.programmers.spbw1.voucher.FixedAmountVoucher;
import org.programmers.spbw1.voucher.Voucher;
import org.programmers.spbw1.voucher.VoucherService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.UUID;

public class VoucherProgram implements Runnable {
    private final Input input;
    private final Output output;
    private final VoucherService voucherService;
    private static final Logger logger = LoggerFactory.getLogger(VoucherProgram.class);

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
            Object obj = new InstructionClass();
            try {
                in = input.input("Instruction : ");
                if(in.equals("exit")) {
                    output.bye();
                    break;
                }
                Class<?> cls = Class.forName(obj.getClass().getName());
                Method m = cls.getDeclaredMethod(in);
                m.invoke(obj);
//            } catch (IOException e) {
//                output.showExceptionTrace(e);
//            } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException |
//                     IllegalAccessException e) {
//                output.invalidInstruction(in);
            }catch (Exception e){
                output.invalidInstruction(in);
                logger.error("invalid instruction : " + in);
            }
        }
    }
    static class InstructionClass {
        private void create(){
            System.out.println("create called");
            logger.info("create");
            Voucher v = new FixedAmountVoucher(UUID.randomUUID(), -1);
        };
        private void list(){
            System.out.println("list called");
            logger.info("list");
        };
    }
}
