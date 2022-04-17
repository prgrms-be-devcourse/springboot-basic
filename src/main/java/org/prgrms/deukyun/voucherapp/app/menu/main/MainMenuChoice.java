package org.prgrms.deukyun.voucherapp.app.menu.main;

/**
 * 메인 메뉴 선택
 */
public enum MainMenuChoice {

    /**
     * 퇴장 메뉴 선택
     */
    EXIT("Type exit to exit the program.", "Voucher App Exit...\n"),

    /**
     * 바우처 생성 메뉴 선택
     */
    CREATE("Type create to create a new voucher.","Enter the type of voucher : fixed, percent\n"),

    /**
     * 바우처 목록 출력 메뉴 선택
     */
    LIST("Type list to list all vouchers.","");

    private final String printEveryLoop;
    private final String printSelected;

    MainMenuChoice(String printLaunched, String printSelected) {
        this.printEveryLoop = printLaunched;
        this.printSelected = printSelected;
    }

    public String getPrintEveryLoop() {
        return printEveryLoop;
    }

    public String getPrintSelected() {
        return printSelected;
    }

    public static MainMenuChoice resolve(String in){
        MainMenuChoice choice;
        try{
            choice = valueOf(MainMenuChoice.class, in.toUpperCase());
        }catch (IllegalArgumentException | NullPointerException ex){
            choice = null;
        }
        return choice;
    }
}
