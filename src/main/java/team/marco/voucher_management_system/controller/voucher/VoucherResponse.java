package team.marco.voucher_management_system.controller.voucher;

import team.marco.voucher_management_system.domain.voucher.Voucher;

import java.util.UUID;

public class VoucherResponse {
    private Long id;
    private UUID code;
    private String name;

    public VoucherResponse(Long id, UUID code, String name) {
        this.id = id;
        this.code = code;
        this.name = name;
    }

    public static VoucherResponse of(Voucher voucher) {
        return new VoucherResponse(voucher.getId(), voucher.getCode(), voucher.getName());
    }

    public Long getId() {
        return id;
    }

    public UUID getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
