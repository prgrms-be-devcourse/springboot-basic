package org.prgrms.kdt.voucher;

/**
 * Created by yhh1056
 * Date: 2021/08/23 Time: 6:53 오후
 */
public class VoucherData {

    private final String voucherNumber;
    private final long discount;

    public VoucherData(String voucherNumber, long discount) {
        validateEmpty(voucherNumber);
        validateRange(discount);
        validatePercent(voucherNumber, discount);

        this.voucherNumber = voucherNumber;
        this.discount = discount;
    }

    private void validateEmpty(String voucherNumber) {
        if (voucherNumber.isBlank()) {
            throw new IllegalArgumentException("바우처를 선택해주세요.");
        }
    }

    private void validateRange(long percent) {
        if (percent < 0) {
            throw new IllegalArgumentException("할인금액 또는 할인율은 0보다 작을 수 없습니다.");
        }
    }

    private void validatePercent(String voucherNumber, long percent) {
        if (voucherNumber.equals("2")) {
            if (percent < 0 || percent > 100) {
                throw new IllegalArgumentException("할인율은 100 이하로 입력해주세요.");
            }
        }
    }

    public String getVoucherNumber() {
        return voucherNumber;
    }

    public long getDiscount() {
        return discount;
    }
}
