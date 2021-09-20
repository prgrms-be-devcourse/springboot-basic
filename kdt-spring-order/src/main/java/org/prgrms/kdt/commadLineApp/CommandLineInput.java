package org.prgrms.kdt.commadLineApp;

import org.prgrms.kdt.voucher.VoucherType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;

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
        if(input.equals("1")) {
            return VoucherType.FIXED;
        }
        else if(input.equals("2")) {
            return VoucherType.PERCENT;
        }
        else{
            System.out.println("worng Input!");
            return VoucherType.NONE;
        }
    }

    public static String inputAmount(VoucherType type) throws IOException{
        String input;
        if(type == VoucherType.FIXED) {
            InPutView.inputFixAmount();
        }
        else
            InPutView.inputPercent();
        input = reader.readLine();
        return input;
    }

    public static String inputCustomerName() throws IOException {
        String input;
        input = reader.readLine();
        return input;
    }

    public static String inputCustomerEmail() throws IOException {
        String input;
        input = reader.readLine();
        return input;
    }

    public static void closeReader() throws IOException {
        reader.close();
    }

    public static UUID inputUUid() throws IOException {
        String input;
        input = reader.readLine();
        return UUID.fromString(input);
    }
}
