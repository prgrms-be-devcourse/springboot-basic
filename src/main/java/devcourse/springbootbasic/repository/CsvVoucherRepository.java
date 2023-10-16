package devcourse.springbootbasic.repository;

import devcourse.springbootbasic.domain.voucher.Voucher;
import devcourse.springbootbasic.domain.voucher.VoucherType;
import devcourse.springbootbasic.exception.FileErrorMessage;
import devcourse.springbootbasic.exception.FileException;
import devcourse.springbootbasic.util.CsvFileHandler;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

@Primary
@Repository
public class CsvVoucherRepository implements VoucherRepository {

    private static final String CSV_LINE_TEMPLATE = "%s,%s,%d";
    private final CsvFileHandler<Voucher> csvFileHandler;
    private final Map<UUID, Voucher> voucherDatabase = new ConcurrentHashMap<>();

    public CsvVoucherRepository(CsvFileHandler<Voucher> voucherCsvFileHandler) {
        this.csvFileHandler = voucherCsvFileHandler;
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
        Function<String[], Voucher> parser = line -> {
            UUID voucherId = UUID.fromString(line[0]);
            VoucherType voucherType = VoucherType.valueOf(line[1]);
            long discountValue = Long.parseLong(line[2]);

            return Voucher.createVoucher(voucherId, voucherType, discountValue);
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
                voucher.getId(), voucher.getVoucherType(), voucher.getDiscountValue());

        csvFileHandler.writeListToCsv(vouchers, serializer);
    }
}
