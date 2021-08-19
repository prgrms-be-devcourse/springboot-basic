package org.prgrms.kdt.Repository;

import org.prgrms.kdt.VO.FixedAmountVoucher;
import org.prgrms.kdt.VO.PercentDiscountVoucher;
import org.prgrms.kdt.VO.Voucher;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

public class VoucherRepoImpl implements VoucherRepository {
    private ArrayList<Voucher> voucherList = new ArrayList<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.empty();
    }

    public ArrayList<Voucher> getVoucherList(){
        return voucherList;
    }

    @Override
    public void createFixedAmountVoucher(long amount, UUID voucherId) {
        //System.out.println("fixed 바우처 생성직전");
        //voucher할인율 받는 코드에서(VoucherTest.java) 숫자 유효성 검증하는 아스키코드를 잘못씀
        voucherList.add(new FixedAmountVoucher(amount, voucherId));
    }

    @Override
    public void createPercentDiscountVoucher(long percent, UUID voucherId) {
        voucherList.add(new PercentDiscountVoucher(percent, voucherId));
    }

    @Override
    public int numVouchers() {
        return voucherList.size();
    }

}
