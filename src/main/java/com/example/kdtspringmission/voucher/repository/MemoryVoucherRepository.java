package com.example.kdtspringmission.voucher.repository;

import com.example.kdtspringmission.voucher.domain.Voucher;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemoryVoucherRepository implements VoucherRepository {

    private static Map<Long, Voucher> store = new HashMap<>();
    // TODO: 2021/08/19 sequence를 레포지토리에서 생성하는 것은 별로 안좋아보인다. Voucher의 아이디가 필요한데,
    // voucher 생성 시점에서 id를 생성하고 이를 시퀀스로 사용하는 방법으로 개선하면 좋을 듯.
    private static Long sequence = 0L;

    @Override
    public Long insert(Voucher voucher) {
        store.put(++sequence, voucher);
        return sequence;
    }

    @Override
    public Voucher findById(Long id) {
        return store.get(id);
    }

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(store.values());
    }
}
