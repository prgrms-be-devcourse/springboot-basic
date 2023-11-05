package team.marco.voucher_management_system.web_app.dto;

import team.marco.voucher_management_system.type_enum.VoucherType;

public record CreateVoucherRequest(
        VoucherType type,
        int data) {
}
