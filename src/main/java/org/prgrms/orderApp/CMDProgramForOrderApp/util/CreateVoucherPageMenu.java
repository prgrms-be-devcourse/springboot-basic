package org.prgrms.orderApp.CMDProgramForOrderApp.util;

public enum CreateVoucherPageMenu {
    FIXEDAMOUNT(1, "FIXED"),
    PERCENTAMOUNT(2, "PERCENT");

    private int menuNumber;
    private String menuName;

    CreateVoucherPageMenu(int menuNumber, String menuName){
        this.menuNumber = menuNumber;
        this.menuName = menuName;
    }
    public String getMenuName(){
        return menuName;
    }
    public static String getMenuName(int menuNumber){
        if (menuNumber == CreateVoucherPageMenu.FIXEDAMOUNT.menuNumber){
            return CreateVoucherPageMenu.FIXEDAMOUNT.menuName;
        }else if(menuNumber == CreateVoucherPageMenu.PERCENTAMOUNT.menuNumber) {
            return CreateVoucherPageMenu.PERCENTAMOUNT.menuName;
        }else{
            return "";
        }
    }

}

