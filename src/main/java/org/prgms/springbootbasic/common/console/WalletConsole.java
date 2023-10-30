package org.prgms.springbootbasic.common.console;

import static org.prgms.springbootbasic.common.console.Console.consoleInput;

public class WalletConsole {
    public static String readCommand(){
        System.out.println();
        System.out.println("Welcome to our wallet service.");
        System.out.println("Type 'allocate' if you want to allocate voucher to a customer." );
        System.out.println("Type 'delete' if you want to delete voucher from a customer.");
        System.out.println("Type 'showVoucher' to view customers with a voucher.");
        System.out.println("Type 'showCustomer' to view vouchers that a customer has.");

        return consoleInput.next();
    }

    public static String typeCustomerId(){
        System.out.println("Type customer Id.");

        return consoleInput.next();
    }

    public static String typeVoucherId(){
        System.out.println("Type voucher Id.");

        return consoleInput.next();
    }

    public static void success(String command){
        System.out.println("'" + command + "' command successfully executed.");
    }
}
