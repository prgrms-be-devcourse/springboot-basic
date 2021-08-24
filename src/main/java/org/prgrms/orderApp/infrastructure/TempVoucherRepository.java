package org.prgrms.orderApp.infrastructure;

import org.prgrms.orderApp.domain.voucher.model.Voucher;
import org.prgrms.orderApp.domain.voucher.repository.VoucherRepository;

import java.util.*;

//@Repository
public class TempVoucherRepository implements VoucherRepository {
    private List<Voucher> voucher_list= new ArrayList<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        for(Voucher one_voucher:voucher_list) {
            if (one_voucher.getVoucherId().equals(voucherId))
                return Optional.of(one_voucher);
        }
        return Optional.empty();
    }

    @Override
    public int save(Voucher voucher) {
        if (voucher_list.size()>0) {
            // 동일한 voucher id 확인
            for (Voucher one_voucher : voucher_list) {
                if (one_voucher.getVoucherId().equals(voucher.getVoucherId()))
                    return 0;
            }
        }
        voucher_list.add(voucher);
        return 1;

    }

    @Override
    public List<Voucher> findAll() {
        return voucher_list;
    }
}
