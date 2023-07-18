package com.wonu606.vouchermanager.repository.voucher.resultset;

public class VoucherInsertResultSet {

    private final Integer affectedRowsCount;

    public VoucherInsertResultSet(Integer affectedRowsCount) {
        this.affectedRowsCount = affectedRowsCount;
    }

    public Integer getAffectedRowsCount() {
        return affectedRowsCount;
    }
}
