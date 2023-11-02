package com.programmers.springbootbasic.domain.voucher.infrastructure;

import static com.programmers.springbootbasic.exception.ErrorCode.INVALID_FILE_PATH;

import com.programmers.springbootbasic.domain.voucher.domain.VoucherRepository;
import com.programmers.springbootbasic.domain.voucher.domain.entity.Voucher;
import com.programmers.springbootbasic.domain.voucher.infrastructure.dto.CsvVoucher;
import com.programmers.springbootbasic.exception.exceptionClass.VoucherException;
import com.programmers.springbootbasic.util.FileManager;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("dev")
@Primary
public class FilePersistenceVoucherRepository implements VoucherRepository {

    private final FileManager fileManager;
    private final String fileName;
    private final ConcurrentHashMap<UUID, Voucher> vouchers;

    public FilePersistenceVoucherRepository(
        List<FileManager> fileManagerList,
        @Value("${file.voucher.path}") String fileName

    ) {
        this.fileName = fileName;
        this.fileManager = initializeFileManager(fileManagerList, fileName);
        this.vouchers = getCsvVoucherHashMap();
    }

    @Override
    public Voucher save(Voucher voucher) {
        fileManager.write(CsvVoucher.of(voucher), fileName, true);
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        var csvVouchers = fileManager.read(fileName, CsvVoucher.class);
        return csvVouchers.stream()
            .map(CsvVoucher::toEntity)
            .toList();
    }

    @Override
    public Optional<Voucher> findById(UUID id) {
        return Optional.ofNullable(vouchers.get(id));
    }

    @Override
    public int deleteById(UUID id) {
        return vouchers.remove(id) == null ? 0 : 1;
    }

    @Override
    public int update(Voucher voucher) {
        return vouchers.put(voucher.getId(), voucher) == null ? 0 : 1;
    }

    private FileManager initializeFileManager(List<FileManager> fileManagerList, String fileName) {
        return fileManagerList.stream()
            .filter(fm -> fm.supports(fileName))
            .findFirst()
            .orElseThrow(() -> new VoucherException(INVALID_FILE_PATH));
    }

    private ConcurrentHashMap<UUID, Voucher> getCsvVoucherHashMap() {
        var csvVouchers = fileManager.read(fileName, CsvVoucher.class);
        ConcurrentHashMap<UUID, Voucher> csvVoucherHashMap = new ConcurrentHashMap<>();
        csvVouchers.forEach(
            csvVoucher -> csvVoucherHashMap.put(UUID.fromString(csvVoucher.getId()),
                csvVoucher.toEntity()));
        return csvVoucherHashMap;
    }
}
