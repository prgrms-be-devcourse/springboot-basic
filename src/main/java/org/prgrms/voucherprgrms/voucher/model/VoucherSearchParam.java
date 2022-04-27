package org.prgrms.voucherprgrms.voucher.model;

public class VoucherSearchParam {
    String searchType;
    String searchKeyword;

    public VoucherSearchParam(String searchType, String searchKeyword) {
        this.searchType = searchType;
        this.searchKeyword = searchKeyword;
    }

    public String getSearchType() {
        return searchType;
    }

    public String getSearchKeyword() {
        return searchKeyword;
    }

}
