package org.prgrms.orderApp.model.voucher;


public enum VoucherType {
    FIXEDAMOUNT(1, "FIXED", -1),
    PERCENTAMOUNT(2, "PERCENT", 100);

    private int menuNumber, limit;
    private String menuName;

    VoucherType(int menuNumber, String menuName, int limit){
        this.menuNumber = menuNumber;
        this.menuName = menuName;
        this.limit = limit;
    }
    public String getMenuName(){
        return menuName;
    }
    public int getLimit(){
        return limit;
    }
    public static String getMenuName(int menuNumber){
        if (menuNumber == VoucherType.FIXEDAMOUNT.menuNumber){
            return VoucherType.FIXEDAMOUNT.menuName;
        }else if(menuNumber == VoucherType.PERCENTAMOUNT.menuNumber) {
            return VoucherType.PERCENTAMOUNT.menuName;
        }else{
            return "";
        }
    }

}
