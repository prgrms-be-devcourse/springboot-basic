package org.prgrms.orderApp.voucher;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class TempVoucherRepository implements VoucherRepository {
    private List<Voucher> voucher_list= new ArrayList<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return voucher_list.stream()
                .filter(x ->x.getVoucherId().equals(voucherId))
                .findFirst();
    }

    @Override
    public int insert(Voucher voucher) {
        if (voucher_list.size()>0) {
            // 동일한 voucher id 확인
            if(voucher_list
                    .stream()
                    .anyMatch(x ->x.getVoucherId().equals(voucher.getVoucherId()))){
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
