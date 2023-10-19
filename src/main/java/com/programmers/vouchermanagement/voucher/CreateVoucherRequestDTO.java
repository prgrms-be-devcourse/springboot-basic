package com.programmers.vouchermanagement.voucher;

import com.programmers.vouchermanagement.consolecomponent.CreateVoucherMenu;

public record CreateVoucherRequestDTO(CreateVoucherMenu createVoucherMenu, long discountAmount) {
}
