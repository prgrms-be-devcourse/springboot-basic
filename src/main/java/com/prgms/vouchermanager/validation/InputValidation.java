package com.prgms.vouchermanager.validation;

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

    public boolean validVoucherType(int number) {
        return number==1||number==2;
    }

    public boolean validPercent(long discountValue) {
        return discountValue <= 100;
    }
}
