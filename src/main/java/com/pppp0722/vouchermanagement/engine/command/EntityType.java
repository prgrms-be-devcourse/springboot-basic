package com.pppp0722.vouchermanagement.engine.command;

public enum EntityType {
    MEMBER,
    VOUCHER,
    WALLET,
    NONE;

    public static EntityType getEntityType(String input) {
        EntityType type;
        switch (input) {
            case "member":
                type = EntityType.MEMBER;
                break;
            case "voucher":
                type = EntityType.VOUCHER;
                break;
            case "wallet":
                type = EntityType.WALLET;
                break;
            default:
                type = EntityType.NONE;
                break;
        }
        return type;
    }
}
