package com.programmers.voucher.entity.voucher;

import java.io.Serializable;

public class Voucher implements Serializable {

    private long id;
    private String name;
    private type type;

    public Voucher(long id, String name, type type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Voucher.type getType() {
        return type;
    }

    public void setType(Voucher.type type) {
        this.type = type;
    }

    public enum type {
        FIXED, PERCENT, NA
    }

    @Override
    public String toString() {
        return String.format("[Voucher #%d] %s / %s", id, name, type.name());
    }
}
