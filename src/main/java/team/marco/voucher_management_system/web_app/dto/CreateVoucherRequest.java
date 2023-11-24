package team.marco.voucher_management_system.web_app.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import team.marco.voucher_management_system.type_enum.VoucherType;

public record CreateVoucherRequest(
        @NotNull
        VoucherType type,
        @Positive
        int data) {
}
