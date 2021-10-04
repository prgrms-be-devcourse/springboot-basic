package com.programmers.voucher.entity.voucher.dto;

import java.time.LocalDate;

public class VoucherListRequest {
    String from = "1970-01-01";
    String to = LocalDate.now().toString();
    String criteria = "";
    String keyword = "";

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getCriteria() {
        return criteria;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
