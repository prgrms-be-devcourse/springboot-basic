package programmers.org.voucher.repository;

import programmers.org.voucher.domain.Voucher;
import programmers.org.voucher.dto.VoucherRequest;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryVoucherRepository implements VoucherRepository {

    private final Map<Long, Voucher> voucherStorage = new ConcurrentHashMap<>();

    @Override
    public void save(Voucher voucher) {
        voucherStorage.put(voucher.getId(), voucher);
    }

    @Override
    public List<Voucher> getAll() {
        return List.copyOf(voucherStorage.values());
    }

    @Override
    public void update(Long id, VoucherRequest request) {
        Voucher voucher = voucherStorage.get(id);
        voucher.updateVoucher(request.getDiscountAmount());
        voucherStorage.put(id, voucher);
    }

    @Override
    public Optional<Voucher> findById(Long id) {
        return Optional.ofNullable(voucherStorage.get(id));
    }

    @Override
    public void deleteById(Long id) {
        voucherStorage.remove(id);
    }
}
