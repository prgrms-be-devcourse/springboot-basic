package devcourse.springbootbasic.repository;

import devcourse.springbootbasic.domain.voucher.Voucher;
import devcourse.springbootbasic.util.CsvFileHandler;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Primary
@Repository
public class CsvVoucherRepository implements VoucherRepository {

    private final CsvFileHandler csvFileHandler;
    private final Map<UUID, Voucher> voucherDatabase = new ConcurrentHashMap<>();

    @Autowired
    public CsvVoucherRepository(CsvFileHandler csvFileHandler) {
        this.csvFileHandler = csvFileHandler;
    }

    @Override
    public Voucher save(Voucher voucher) {
        voucherDatabase.put(voucher.getId(), voucher);
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return voucherDatabase.values()
                .stream()
                .toList();
    }

    @PostConstruct
    public void init() {
        List<Voucher> vouchers = csvFileHandler.readVoucherListFromCsv();
        vouchers.forEach(voucher -> voucherDatabase.put(voucher.getId(), voucher));
    }

    @PreDestroy
    public void destroy() {
        List<Voucher> vouchers = voucherDatabase.values()
                .stream()
                .toList();
        csvFileHandler.writeVoucherListToCsv(vouchers);
    }
}
