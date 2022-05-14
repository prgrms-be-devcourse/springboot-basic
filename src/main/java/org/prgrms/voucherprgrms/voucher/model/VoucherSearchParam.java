package org.prgrms.voucherprgrms.voucher.model;

public class VoucherSearchParam {
    String searchType;
    String searchKeyword;
    String startDate;
    String endDate;

    public VoucherSearchParam(String searchType, String searchKeyword, String startDate, String endDate) {
        this.searchType = searchType;
        this.searchKeyword = searchKeyword;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getSearchType() {
        return searchType;
    }

    public String getSearchKeyword() {
        return searchKeyword;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }
}
