package org.programmers.kdt.weekly.voucher.repository;

//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//import java.util.UUID;
//import java.util.concurrent.ConcurrentHashMap;
//import org.programmers.kdt.weekly.voucher.model.Voucher;
//import org.springframework.stereotype.Repository;
//
////@Profile("dev")
//@Repository
//public class VoucherMemoryRepository implements VoucherRepository {
//
//    private static final Map<UUID, Voucher> storage = new ConcurrentHashMap();
//
//    @Override
//    public Voucher insert(Voucher voucher) {
//        storage.put(UUID.randomUUID(), voucher);
//
//        return voucher;
//    }
//
//    @Override
//    public Voucher update(Voucher voucher) {
//        return null;
//    }
//
//    @Override
//    public void deleteById(UUID voucherId) {
//    }
//
//    @Override
//    public void deleteAll() {
//
//    }
//
//    @Override
//    public List<Voucher> findAll() {
//        List<Voucher> voucherList = new ArrayList<>();
//        storage.forEach((uuid, voucher) -> voucherList.add(voucher));
//
//        return List.copyOf(voucherList);
//    }
//
//    @Override
//    public Optional<Voucher> findById(UUID voucherId) {
//        return Optional.empty();
//    }
//
//
//}