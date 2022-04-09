package org.prgrms.kdt.model;

public enum Function {
    exit(" to exit the program"),
    create(" to create a new voucher"),
    list(" to list all vouchers");

    private String explain;

    Function(String explain) {
        this.explain = explain;
    }

    public String getExplain() {
        return explain;
    }
}
