package org.prgrms.kdt.assignments;

public class VoucherData {

    private final int voucherNumber;
    private final long discountAmount;

    public VoucherData(int voucherNumber, long discountAmount) {
        isEmpty(voucherNumber, discountAmount);
        isValidVoucherNumber(voucherNumber);
        isValidAmount(voucherNumber, discountAmount);

        this.voucherNumber = voucherNumber;
        this.discountAmount = discountAmount;
        
    }



    public void isEmpty(int voucherNumber, long discountAmount){
        if(voucherNumber < 1 || voucherNumber > 2){
            throw new IllegalArgumentException("바우처를 선택하지 않았습니다..");
        }
        if(discountAmount == 0L){
            throw new IllegalArgumentException("할인율을 확인해주세요.");
        }
    }

    public void isValidVoucherNumber(int voucherNumber) {
        if(!(voucherNumber == 1 || voucherNumber == 2)){
            throw new IllegalArgumentException("입력된 바우처 번호를 확인하세요.");
        }
    }

    public void isValidAmount(int voucherNumber, long discountAmount){
        if(voucherNumber == 1 && discountAmount <= 0L){
            throw new IllegalArgumentException("FixedAmountVoucher의 Discount 값은 0보다 작거나 같을 수 없습니다.");
        } else if(voucherNumber == 2 && (discountAmount <= 0L || discountAmount > 100L)){
            throw new IllegalArgumentException("PercentVoucher의 할인율은 0% 이거나 음수 일 수 없고 100%를 초과할 수 없습니다.");
        }
    }

    public int getVoucherNumber() {
        return voucherNumber;
    }

    public long getDiscountAmount() {
        return discountAmount;
    }

}
