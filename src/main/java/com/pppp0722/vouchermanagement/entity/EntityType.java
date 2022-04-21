package com.pppp0722.vouchermanagement.entity;

public enum EntityType {
    MEMBER,
    VOUCHER,
    WALLET,
    NONE;

    public static EntityType getEntityType(String type) {
        EntityType entityType;
        type = type.toLowerCase();
        switch (type) {
            case "member":
                entityType = EntityType.MEMBER;
                break;
            case "voucher":
                entityType = EntityType.VOUCHER;
                break;
            case "wallet":
                entityType = EntityType.WALLET;
                break;
            default:
                entityType = EntityType.NONE;
                break;
        }

        return entityType;
    }
}
