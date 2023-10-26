package com.prgms.vouchermanager.validation;

import org.springframework.stereotype.Component;

@Component
public class InputValidation {


    public boolean validFrontMenu(int menu) {
        return menu>=1&&menu<=4;
    }

    public boolean validVoucherMenu(int menu) {
        return menu>=1 && menu<=6;
    }

    public boolean validCustomerMenu(int menu) {
        return menu>=1 && menu<=7;
    }
    public boolean validWalletMenu(int menu) {
        return menu>=1 && menu<=4;
    }

    public boolean validVoucherPercent(long percent) {
        return percent<=100 ;
    }
    public boolean validCustomerInfo(String name, String email,int blackList) {
        return name.length()<=20
                && email.length()<=40
                && blackList>=1 && blackList<=2 ;
    }
}
