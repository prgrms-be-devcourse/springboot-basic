package org.prgrms.orderApp.infrastructure.repository;

import org.prgrms.orderApp.domain.voucher.model.Voucher;
import org.prgrms.orderApp.domain.voucher.repository.VoucherRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Profile("dev")
public class TempVoucherRepository implements VoucherRepository {
    private List<Voucher> voucher_list= new ArrayList<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return voucher_list.stream()
                .filter(x ->x.getVoucherId().equals(voucherId))
                .findFirst();
    }

    @Override
    public int save(Voucher voucher) {
        if (voucher_list.size()>0) {
            // 동일한 voucher id 확인
            if(voucher_list
                    .stream()
                    .filter(x ->x.getVoucherId().equals(voucher.getVoucherId()))
                    .findFirst()
                    .isPresent()){
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
