package com.kdt.commandLineApp.service.voucher;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryVoucherRepository implements VoucherRepository {
    private Map<Long, Voucher> map = new ConcurrentHashMap<>();

    @Override
    public void add(Voucher voucher) {
        map.put((long) Math.random(), voucher);
    }

    @Override
    public Optional<Voucher> get(long id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public List<Voucher> getAll(int page, int size, String type) {
        List<Voucher> voucherList;

        if ((page < 0) || (size < 0)) {
            return List.of();
        }
        voucherList = map.values()
                .stream()
                .filter((e)-> type.equals(null)||e.getType().equals(type))
                .toList();
        if (page * size >= voucherList.size()) {
            return List.of();
        }
        return voucherList.stream()
                .skip(page * size)
                .limit(size)
                .toList();
    }

    @Override
    public void remove(long id) {
        map.remove(id);
    }

    @Override
    public void deleteAll() {
        map.clear();
    }

    @Override
    public void destroy() throws Exception {

    }
}
