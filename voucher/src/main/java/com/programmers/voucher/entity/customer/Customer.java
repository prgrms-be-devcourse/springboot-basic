package com.programmers.voucher.entity.customer;

public class Customer {
    private long id;
    private String username;
    private String alias;
    private boolean blacklisted;

    public Customer(long id, String username, String alias, boolean blacklisted) {
        this.id = id;
        this.username = username;
        this.alias = alias;
        this.blacklisted = blacklisted;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public boolean isBlacklisted() {
        return blacklisted;
    }

    public void setBlacklisted(boolean blacklisted) {
        this.blacklisted = blacklisted;
    }

    @Override
    public String toString() {
        return String.format("[ CUSTOMER #%d ] %s(%s) / %s", id, username, alias, blacklisted ? "blacked" : "clean");
    }
}
