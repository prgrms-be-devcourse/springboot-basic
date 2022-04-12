package org.prgrms.weeklymission.utils;

public enum FilePath {

    VOUCHER_FILE_DB_PATH("db/voucher-db.txt"),
    BLACK_CUSTOMER_FILE_DB_PATH("db/black-db.csv");

    private final String path;

    FilePath(String path) {
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }
}
