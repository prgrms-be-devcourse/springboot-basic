package com.wonu606.vouchermanager.repository.voucherwallet.resultset;

public class WalletInsertResultSet {

    private final Integer affectedRowsCount;

    public WalletInsertResultSet(Integer affectedRowsCount) {
        this.affectedRowsCount = affectedRowsCount;
    }

    public Integer getAffectedRowsCount() {
        return affectedRowsCount;
    }
}
