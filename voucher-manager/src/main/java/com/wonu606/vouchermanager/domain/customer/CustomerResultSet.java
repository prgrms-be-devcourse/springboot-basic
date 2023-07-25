package com.wonu606.vouchermanager.domain.customer;

import java.time.LocalDateTime;

public class CustomerResultSet {

    private final String emailAddress;
    private final String nickname;
    private final LocalDateTime lastUpdated;
    private final LocalDateTime createdDate;

    public CustomerResultSet(String emailAddress, String nickname, LocalDateTime lastUpdated,
            LocalDateTime createdDate) {
        this.emailAddress = emailAddress;
        this.nickname = nickname;
        this.lastUpdated = lastUpdated;
        this.createdDate = createdDate;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getNickname() {
        return nickname;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }
}
