package team.marco.voucher_management_system.service.voucher;

import team.marco.voucher_management_system.domain.voucher.VoucherType;

import java.util.Optional;
import java.util.UUID;

public class VoucherCreateServiceRequest {
    private VoucherType voucherType;
    private int discountValue;
    private Optional<UUID> code;
    private Optional<String> name;

    public VoucherCreateServiceRequest(VoucherType voucherType, int discountValue, UUID code, String name) {
        this.voucherType = voucherType;
        this.discountValue = discountValue;
        this.code = Optional.ofNullable(code);
        this.name = Optional.ofNullable(name);
    }

    public VoucherCreateServiceRequest(VoucherType voucherType, int discountValue) {
        this.voucherType = voucherType;
        this.discountValue = discountValue;
        this.code = Optional.empty();
        this.name = Optional.empty();
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public int getDiscountValue() {
        return discountValue;
    }

    public Optional<UUID> getCode() {
        return code;
    }

    public Optional<String> getName() {
        return name;
    }
}
