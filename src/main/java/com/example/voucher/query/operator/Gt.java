package com.example.voucher.query.operator;

public class Gt extends Operator {

    private static final String GT = ">=";

    public Gt(String column, Object value) {
        super(column, GT, value);
    }

}