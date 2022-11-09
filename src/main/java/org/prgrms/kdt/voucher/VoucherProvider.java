package org.prgrms.kdt.voucher;

import org.prgrms.kdt.exceptions.WrongVoucherTypeException;
import org.prgrms.kdt.utils.VoucherType;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

import static org.prgrms.kdt.io.IOManager.getSelectWrongMessage;

@Service
public class VoucherProvider {

    private final VoucherStorage voucherStorage;


    public VoucherProvider(VoucherStorage voucherStorage) {
        this.voucherStorage = voucherStorage;
    }

    public void create(VoucherType voucherType, double amount) {
        switch (voucherType) {
            case FIXED_VOUCHER -> {
                voucherStorage.save(new FixedAmountVoucher(UUID.randomUUID(), amount));
            }
            case PERCENT_VOUCHER -> {
                voucherStorage.save(new PercentDiscountVoucher(UUID.randomUUID(), amount));
            }
            default -> {
                throw new WrongVoucherTypeException(getSelectWrongMessage());
            }
        }
    }

    public List<Voucher> list(){
        if(voucherStorage.findAll().isEmpty()) throw new NoVoucherException("아직 생성된 바우처가 없습니다.");
        return voucherStorage.findAll();
    }
}
