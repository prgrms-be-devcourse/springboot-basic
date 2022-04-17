package org.prgrms.springbasic.utils.enumm;

public enum FilePath {

    VOUCHER_FILE_PATH("src/main/resources/vouchers.txt"),
    BLACK_CUSTOMER_FILE_PATH("src/main/resources/black-customers.csv");

    private final String path;

    FilePath(String path) {
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }
}
