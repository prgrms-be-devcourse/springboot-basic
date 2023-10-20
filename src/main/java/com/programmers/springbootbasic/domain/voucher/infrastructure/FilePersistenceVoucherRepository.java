package com.programmers.springbootbasic.domain.voucher.infrastructure;

import static com.programmers.springbootbasic.exception.ErrorCode.INVALID_FILE_PATH;

import com.programmers.springbootbasic.domain.voucher.domain.VoucherRepository;
import com.programmers.springbootbasic.domain.voucher.domain.entity.Voucher;
import com.programmers.springbootbasic.exception.exceptionClass.VoucherException;
import com.programmers.springbootbasic.util.FileManager;
import com.programmers.springbootbasic.util.FileProperties;
import java.util.List;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("dev")
@Primary
public class FilePersistenceVoucherRepository implements VoucherRepository {

    private final List<FileManager> fileManagerList;
    private FileManager fileManager;

    public FilePersistenceVoucherRepository(List<FileManager> fileManagerList) {
        this.fileManagerList = fileManagerList;
        fileManagerList.stream().filter((fm) -> fm.supports(FileProperties.getVoucherFilePath()))
            .findFirst()
            .ifPresentOrElse((fm) -> this.fileManager = fm, () -> {
                throw new VoucherException(INVALID_FILE_PATH);
            });
    }

    @Override
    public Voucher save(Voucher voucher) {
        fileManager.write(CsvVoucher.of(voucher), FileProperties.getVoucherFilePath());
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        var csvVouchers = fileManager.read(FileProperties.getVoucherFilePath(), CsvVoucher.class);
        return csvVouchers.stream()
            .map(CsvVoucher::toEntity)
            .toList();
    }
}
