package com.prgrms.springbootbasic.service.voucher;

import com.prgrms.springbootbasic.domain.voucher.FixedDiscountVoucher;
import com.prgrms.springbootbasic.domain.voucher.PercentDiscountVoucher;
import com.prgrms.springbootbasic.domain.voucher.Voucher;
import com.prgrms.springbootbasic.enums.VoucherType;
import com.prgrms.springbootbasic.repository.voucher.VoucherRepository;
import java.util.Map;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    //생성(create)
    public Voucher createVoucher(VoucherType type, long discount) {
        try {
            Voucher voucher = switch (type) {
                case FIXED -> new FixedDiscountVoucher(discount);
                case PERCENT -> new PercentDiscountVoucher(discount);
            };
            return voucherRepository.insert(voucher);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    public Map<UUID, Voucher> fetchAllVouchers() {
        return voucherRepository.getAllVouchersList();
    }

    //조회(Read) - id를 통해서 조회
    public void findById() {

    }

    //수정(Update)
    public void updetVoucher() {

    }

    //삭제(Delete) -id
    public void deleteById() {

    }

    //삭제(Delete)
    public void deleteAllVoucher() {

    }
}
