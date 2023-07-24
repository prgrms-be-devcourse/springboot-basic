package com.prgmrs.voucher.dto.response;

import com.prgmrs.voucher.model.User;

import java.util.List;
import java.util.stream.Collectors;

public record UserListResponse(List<User> userList) {
    @Override
    public String toString() {
        return userList.stream()
                .map(Object::toString)
                .collect(Collectors.joining("\n"));
    }
}
