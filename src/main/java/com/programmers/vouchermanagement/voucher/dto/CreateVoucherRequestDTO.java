package com.programmers.vouchermanagement.voucher.dto;

import com.programmers.vouchermanagement.consoleapp.menu.CreateVoucherMenu;

public record CreateVoucherRequestDTO(CreateVoucherMenu createVoucherMenu, long discountAmount) {
}
