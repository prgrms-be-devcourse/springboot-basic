package org.prgrms.kdt.day2_work;

import org.prgrms.kdt.service.VoucherType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CommandLineInput {

    private final static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static CommandType inputCommand() {
        try {
            String input;
            input = reader.readLine().toUpperCase().replace(" ", "");
            return CommandType.valueOf(input);
        }catch (Exception e){
            System.out.println("wrong command!");
            return null;
        }

    }

    public static VoucherType inputType() throws IOException{
        String input;
        input = reader.readLine();
        if(input.equals("1"))
            return VoucherType.FIXED;
        return VoucherType.PERCENT;
    }

    public static String inputAmount(VoucherType type) throws IOException{
        String input;
        if(type == VoucherType.FIXED) {
            Manual.inputFixAmount();
        }
        else
            Manual.inputPercent();
        input = reader.readLine();
        return input;
    }

    public static void closeReader() throws IOException {
        reader.close();
    }
}
