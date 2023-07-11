package com.prgmrs.voucher.dto.response;

import com.prgmrs.voucher.model.User;

import java.util.List;

public record UserListResponse(List<User> userList) {
}
