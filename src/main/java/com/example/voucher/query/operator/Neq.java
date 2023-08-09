package com.example.voucher.query.operator;

public class Neq extends Operator {

    private static final String NEQ = "!=";

    public Neq(String column, Object value) {
        super(column, NEQ, value);
    }
}