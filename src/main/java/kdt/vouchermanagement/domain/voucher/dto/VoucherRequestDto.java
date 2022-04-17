package kdt.vouchermanagement.domain.voucher.dto;

public class VoucherRequestDto {
    private final String voucherTypeNum;
    private final String discountValue;

    public VoucherRequestDto(String voucherTypeNum, String discountValue) {
        this.voucherTypeNum = voucherTypeNum;
        this.discountValue = discountValue;
    }

    public String getVoucherTypeNum() {
        return voucherTypeNum;
    }

    public String getDiscountValue() {
        return discountValue;
    }
}
