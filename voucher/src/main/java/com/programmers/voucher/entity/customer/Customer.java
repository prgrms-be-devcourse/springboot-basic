package com.programmers.voucher.entity.customer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Customer {

    private long id;
    private String username;
    private String alias;
    private boolean blacklisted;
    private LocalDate createdAt;
    private List<Long> vouchers = new ArrayList<>();

    public Customer(long id, String username, String alias, boolean blacklisted, LocalDate createdAt) {
        this.id = id;
        this.username = username;
        this.alias = alias;
        this.blacklisted = blacklisted;
        this.createdAt = createdAt;
    }

    public Customer(String username, String alias) {
        this.username = username;
        this.alias = alias;
        this.blacklisted = false;
        this.createdAt = LocalDate.now();
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

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public List<Long> getVouchers() {
        return vouchers;
    }

    public void setVouchers(List<Long> vouchers) {
        this.vouchers = vouchers;
    }

    @Override
    public String toString() {
        return String.format("[ CUSTOMER #%d ] %s(%s) / %s / %s", id, username, alias, blacklisted ? "blacked" : "clean", createdAt);
    }

    @Override
    public int hashCode() {
        return (int) this.id;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Customer)) return false;
        Customer other = (Customer) obj;
        return this.id == other.getId() &&
                this.username.equals(other.getUsername()) &&
                this.alias.equals(other.getAlias()) &&
                this.createdAt.equals(other.getCreatedAt());
    }
}
