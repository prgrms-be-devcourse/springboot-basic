package com.programmers.voucher.entity.customer;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Customer {

    private Long id;
    private String username;
    private String alias;
    private boolean blacklisted;
    private final LocalDate createdAt;
    private final List<Long> vouchers = new LinkedList<>();

    public Customer(Long id, String username, String alias, boolean blacklisted, LocalDate createdAt) {
        this.id = id;
        this.username = username;
        this.alias = alias;
        this.blacklisted = blacklisted;
        this.createdAt = createdAt;
    }

    public Customer(String username, String alias) {
        this(null, username, alias, false, LocalDate.now());
    }

    public Long getId() {
        return id;
    }

    public void registerId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void updateUsername(String username) {
        this.username = username;
    }

    public String getAlias() {
        return alias;
    }

    public void updateAlias(String alias) {
        this.alias = alias;
    }

    public boolean isBlacklisted() {
        return blacklisted;
    }

    public void toggleBlacklisted() {
        blacklisted = !blacklisted;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public List<Long> getVouchers() {
        return vouchers;
    }

    @Override
    public String toString() {
        return String.format("[ CUSTOMER #%d ] %s(%s) / %s / %s", id, username, alias, blacklisted ? "blacked" : "clean", createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, alias, blacklisted, createdAt, vouchers);
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Customer)) return false;
        Customer other = (Customer) obj;
        return (this.id == null || this.id.equals(other.getId())) &&
                this.username.equals(other.getUsername()) &&
                this.alias.equals(other.getAlias()) &&
                this.createdAt.equals(other.getCreatedAt());
    }
}
