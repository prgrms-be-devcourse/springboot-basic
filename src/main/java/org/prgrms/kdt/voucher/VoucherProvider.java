package org.prgrms.kdt.voucher;

import org.prgrms.kdt.exceptions.NoVoucherException;
import org.prgrms.kdt.exceptions.WrongVoucherTypeException;
import org.prgrms.kdt.utils.MessageType;
import org.prgrms.kdt.utils.VoucherType;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class VoucherProvider {

    private final VoucherStorage voucherStorage;


    public VoucherProvider(VoucherStorage voucherStorage) {
        this.voucherStorage = voucherStorage;
    }

    public void create(VoucherType voucherType, long amount){
        switch (voucherType){
            case FIXED_VOUCHER -> voucherStorage.save(new FixedAmountVoucher(UUID.randomUUID(), amount));
            case PERCENT_VOUCHER -> voucherStorage.save(new PercentDiscountVoucher(UUID.randomUUID(), amount));
            default -> throw new WrongVoucherTypeException(MessageType.SELECT_WRONG.getMessage());
        }
    }

    public List<Voucher> list(){
        if(voucherStorage.findAll().isEmpty()) throw new NoVoucherException("아직 생성된 바우처가 없습니다.");
        return voucherStorage.findAll();
    }
}
