package devcourse.springbootbasic.repository.voucher;

import devcourse.springbootbasic.domain.voucher.Voucher;
import devcourse.springbootbasic.domain.voucher.VoucherType;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Profile("dev")
@Repository
public class InMemoryVoucherRepository implements VoucherRepository {

    private final Map<UUID, Voucher> voucherDatabase = new ConcurrentHashMap<>();

    @Override
    public Voucher save(Voucher voucher) {
        voucherDatabase.put(voucher.getId(), voucher);
        return voucher;
    }

    @Override
    public List<Voucher> findAllWithFilter(VoucherType voucherType, LocalDate startDate, LocalDate endDate) {
        return voucherDatabase.values()
                .stream()
                .filter(voucher -> voucherType == null || voucher.getVoucherType().equals(voucherType))
                .filter(voucher -> startDate == null || voucher.getCreatedAt().isEqual(startDate.atStartOfDay()) || voucher.getCreatedAt().isAfter(startDate.atStartOfDay()))
                .filter(voucher -> endDate == null || voucher.getCreatedAt().isBefore(endDate.plusDays(1).atStartOfDay()))
                .toList();
    }

    @Override
    public List<Voucher> findAll() {
        return voucherDatabase.values()
                .stream()
                .toList();
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(voucherDatabase.get(voucherId));
    }

    @Override
    public boolean update(Voucher voucher) {
        int updatedRow = voucherDatabase.containsKey(voucher.getId()) ? 1 : 0;
        if (updatedRow == 1) voucherDatabase.put(voucher.getId(), voucher);
        return updatedRow == 1;
    }

    @Override
    public void delete(Voucher voucher) {
        voucherDatabase.remove(voucher.getId());
    }

    @Override
    public List<Voucher> findByCustomerId(UUID customerId) {
        return voucherDatabase.values()
                .stream()
                .filter(voucher -> voucher.getCustomerId().equals(customerId))
                .toList();
    }
}
