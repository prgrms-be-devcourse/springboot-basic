package com.example.voucherproject.common.io.console;

import com.example.voucherproject.common.enums.MenuType;
import com.example.voucherproject.common.enums.ServiceType;
import com.example.voucherproject.common.enums.UserType;
import com.example.voucherproject.common.enums.VoucherType;

public interface Input {
    MenuType selectMenu(ServiceType type);
    VoucherType createVoucher();
    UserType isBlacklist();
    String userName();
    UserType userType();
    ServiceType selectService();
}
