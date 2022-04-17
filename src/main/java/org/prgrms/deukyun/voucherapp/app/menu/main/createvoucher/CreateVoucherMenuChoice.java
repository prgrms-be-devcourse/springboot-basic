package org.prgrms.deukyun.voucherapp.app.menu.main.createvoucher;

/**
 * 바우처 생성 메뉴 선택
 */
public enum CreateVoucherMenuChoice {
    /**
     * 정액 할인 바우처 생성 메뉴 선택
     */
    FIXED("Enter amount : "),

    /**
     * 정률 할인 바우처 생성 메뉴 선택
     */
    PERCENT("Enter percent : ");

    private final String printSelected;

    CreateVoucherMenuChoice(String printSelected) {
        this.printSelected = printSelected;
    }

    public String getPrintSelected() {
        return printSelected;
    }

    public static CreateVoucherMenuChoice resolve(String in) {
        CreateVoucherMenuChoice choice;
        try{
            choice = valueOf(CreateVoucherMenuChoice.class, in.toUpperCase());
        }catch (IllegalArgumentException | NullPointerException ex){
            choice = null;
        }
        return choice;
    }
}
