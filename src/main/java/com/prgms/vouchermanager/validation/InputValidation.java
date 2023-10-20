package com.prgms.vouchermanager.validation;

import org.springframework.stereotype.Component;

@Component
public class InputValidation {


    public boolean validFrontMenu(int menu) {
        return menu == 1 || menu == 2||menu==3;
    }


    public boolean validVoucherMenu(int menu) {
        return menu == 1 || menu == 2;
    }

    public boolean validCustomerMenu(int menu) {
        return menu == 1;
    }

    public boolean validVoucherPercent(long percent) {
        return percent<=100 ;
    }
}
