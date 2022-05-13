package org.prgrms.springbootbasic.dto;

import org.prgrms.springbootbasic.controller.VoucherType;

public class SearchCondition {

    private VoucherType voucherType;
    private String start;
    private String end;

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public void setVoucherType(VoucherType voucherType) {
        this.voucherType = voucherType;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}
