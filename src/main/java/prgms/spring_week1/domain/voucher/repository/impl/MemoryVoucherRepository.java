package prgms.spring_week1.domain.voucher.repository.impl;

import org.springframework.stereotype.Repository;
import prgms.spring_week1.domain.voucher.model.Voucher;
import prgms.spring_week1.domain.voucher.repository.VoucherRepository;
import prgms.spring_week1.exception.EmptyListException;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryVoucherRepository implements VoucherRepository {

    private final Map<UUID, Voucher> voucherList = new ConcurrentHashMap<>();

    @Override
    public List<Voucher> findAll() throws EmptyListException {
        List<Voucher> voucehrList = voucherList.values().stream().toList();
        if (voucehrList.isEmpty()) {
            throw new EmptyListException("조회 가능한 바우처 리스트가 없습니다.");
        }
        return voucehrList;
    }

    @Override
    public Voucher insert(Voucher voucher) {
        voucherList.put(voucher.getVoucherId(), voucher);
        return voucher;
    }
}
