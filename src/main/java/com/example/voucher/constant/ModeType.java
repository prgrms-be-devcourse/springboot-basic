package com.example.voucher.constant;

public enum ModeType {

    EXIT,
    CREATE,
    LIST;

    public static ModeType getModeType(String typeName) {

        return ModeType.valueOf(typeName.toUpperCase());

    }

}

