package org.programmers.spbw1;

import org.programmers.spbw1.io.Input;
import org.programmers.spbw1.io.Output;
import org.programmers.spbw1.voucher.VoucherService;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
            Object obj = new InstructionClass();
            try {
                in = input.input("");
                Class<?> cls = Class.forName(obj.getClass().getName());
                Method m = cls.getDeclaredMethod(in);
                m.invoke(obj);
            } catch (IOException e) {
                output.showExceptionTrace(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
//            if(in.equals("create"))
//                create();
//            else if(in.equals("list"))
//                list();
//            else if(in.equals("exit")) {
//                output.bye();
//                break;
//            }
//            else
//                output.invalidInstruction(in);
        }
    }
//    private void create(){
//        System.out.println("create called");
//    };
//    private void list(){
//        System.out.println("list called");
//    };
    static class InstructionClass {
        private void create(){
            System.out.println("create called");
        };
    private void list(){
            System.out.println("list called");
        };
    }
}
