package com.wonu606.vouchermanager.repository.voucherwallet.resultset;

public class WalletUpdateResultSet {

    private final Integer affectedRowsCount;

    public WalletUpdateResultSet(Integer affectedRowsCount) {
        this.affectedRowsCount = affectedRowsCount;
    }

    public Integer getAffectedRowsCount() {
        return affectedRowsCount;
    }
}
