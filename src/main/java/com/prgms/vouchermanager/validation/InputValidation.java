package com.prgms.vouchermanager.validation;

public class InputValidation {


    public boolean validFrontMenu(int menu) {
        if (menu == 1 || menu == 2||menu==3) {
            return true;
        }return false;
    }


    public boolean validVoucherMenu(int menu) {
        if (menu == 1 || menu == 2) {
            return true;
        }return false;
    }

    public boolean validCustomerMenu(int menu) {
        if (menu == 1) {
            return true;
        }
        return false;
    }

    public boolean validVoucherType(int number) {
        if (number==1||number==2) {
            return true;
        }else return false;
    }

    public boolean validPercent(long discountValue) {
        if (discountValue > 100) {
            return false;
        }
        return  true;
    }
}
