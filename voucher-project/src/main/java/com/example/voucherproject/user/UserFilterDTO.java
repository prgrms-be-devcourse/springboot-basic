package com.example.voucherproject.user;

import com.example.voucherproject.user.model.UserType;

public record UserFilterDTO(UserType userType, String from, String to) {

}
