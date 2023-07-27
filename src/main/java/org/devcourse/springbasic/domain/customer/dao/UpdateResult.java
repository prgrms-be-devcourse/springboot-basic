package org.devcourse.springbasic.domain.customer.dao;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UpdateResult {
    private final int rowsUpdated;

    public boolean isSucceeded() {
        return rowsUpdated > 0;
    }
}