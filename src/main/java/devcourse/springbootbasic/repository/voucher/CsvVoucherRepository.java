package devcourse.springbootbasic.repository.voucher;

import devcourse.springbootbasic.domain.voucher.Voucher;
import devcourse.springbootbasic.domain.voucher.VoucherType;
import devcourse.springbootbasic.util.CsvFileHandler;
import devcourse.springbootbasic.util.UUIDUtil;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

@Profile("file")
@Repository
public class CsvVoucherRepository implements VoucherRepository {

    private static final String CSV_LINE_TEMPLATE = "%s,%s,%s,%d,%s";
    private final CsvFileHandler csvFileHandler;
    private final Map<UUID, Voucher> voucherDatabase = new ConcurrentHashMap<>();

    public CsvVoucherRepository(CsvFileHandler voucherCsvFileHandler) {
        this.csvFileHandler = voucherCsvFileHandler;
    }

    @Override
    public Voucher save(Voucher voucher) {
        voucherDatabase.put(voucher.getId(), voucher);
        return voucher;
    }

    @Override
    public List<Voucher> findAllWithSearchConditions(VoucherType voucherType, LocalDate startDate, LocalDate endDate) {
        return voucherDatabase.values()
                .stream()
                .filter(voucher -> voucherType == null || voucher.getVoucherType().equals(voucherType))
                .filter(voucher -> startDate == null || voucher.getCreatedAt().isAfter(startDate.atStartOfDay()))
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

    @PostConstruct
    public void init() {
        Function<String[], Voucher> parser = line -> {
            UUID voucherId = UUIDUtil.stringToUUID(line[0]);
            VoucherType voucherType = VoucherType.valueOf(line[1]);
            UUID customerId = line[2].equals("null") ? null : UUIDUtil.stringToUUID(line[2]);
            long discountValue = Long.parseLong(line[3]);
            LocalDateTime createdAt = LocalDateTime.parse(line[4]);

            return Voucher.createVoucher(voucherId, voucherType, discountValue, customerId, createdAt);
        };
        List<Voucher> vouchers = csvFileHandler.readListFromCsv(parser, CSV_LINE_TEMPLATE);

        vouchers.forEach(voucher -> voucherDatabase.put(voucher.getId(), voucher));
    }

    @PreDestroy
    public void destroy() {
        List<Voucher> vouchers = voucherDatabase.values()
                .stream()
                .toList();
        Function<Voucher, String> serializer = voucher
                -> String.format(CSV_LINE_TEMPLATE,
                voucher.getId(), voucher.getVoucherType(), voucher.getCustomerId(), voucher.getDiscountValue(), voucher.getCreatedAt());

        csvFileHandler.writeListToCsv(vouchers, serializer);
    }
}
