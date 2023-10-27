package org.programmers.springorder.utils;


public class Properties {

    private static final String voucherFilePath = "src/main/resources/csv/voucher.csv";
    private static final String customerFilePath = "src/main/resources/csv/customerList.csv";

    public static String getVoucherFilePath() {
        return voucherFilePath;
    }

    public static String getCustomerFilePath(){
        return customerFilePath;
    }
}
