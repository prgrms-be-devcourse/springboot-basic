package org.prgrms.kdt.form;

/**
 * Created by yhh1056
 * Date: 2021/09/16 Time: 1:16 오후
 */
public class VoucherSearchForm {

    private String voucherType;

    private String beforeDate;

    private String afterDate;

    public String getVoucherType() {
        return voucherType;
    }

    public void setVoucherType(String voucherType) {
        this.voucherType = voucherType;
    }

    public String getBeforeDate() {
        return beforeDate;
    }

    public void setBeforeDate(String beforeDate) {
        this.beforeDate = beforeDate;
    }

    public String getAfterDate() {
        return afterDate;
    }

    public void setAfterDate(String afterDate) {
        this.afterDate = afterDate;
    }
}
