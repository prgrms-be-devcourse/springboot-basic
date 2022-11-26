package prgms.vouchermanagementapp.repository;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import prgms.vouchermanagementapp.configuration.FileConfig;
import prgms.vouchermanagementapp.domain.Voucher;
import prgms.vouchermanagementapp.repository.util.FileManager;
import prgms.vouchermanagementapp.repository.util.VoucherContentConverter;

import java.io.File;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@Profile("file")
public class FileVoucherRepository implements VoucherRepository {

    private final FileConfig fileConfig;
    private final FileManager fileManager;
    private static final String filepath = "src/main/resources/data/voucher_records.csv";

    public FileVoucherRepository(FileConfig fileConfig, FileManager fileManager) {
        this.fileConfig = fileConfig;
        this.fileManager = fileManager;
    }

    @Override
    public void save(Voucher voucher) {
        File file = fileManager.getFile(fileConfig.getVoucherRecordPath());
        fileManager.writeContent(file, VoucherContentConverter.toContent(voucher));
    }

    @Override
    public List<Voucher> findAll() {
        File file = fileManager.getFile(fileConfig.getVoucherRecordPath());
        return fileManager.readFileByLine(file)
                .stream()
                .map(VoucherContentConverter::toVoucher)
                .collect(Collectors.toList());
    }

    @Override
    public List<Voucher> findById(UUID voucherId) {
        throw new UnsupportedOperationException("FileVoucherRepository doesn't support findById()");
    }

    @Override
    public List<Voucher> findAllByCustomerName() {
        throw new UnsupportedOperationException("FileVoucherRepository doesn't support findAllByCustomerName()");
    }

    @Override
    public void updateVoucher(UUID voucherId, long fixedDiscountLevel) {
        throw new UnsupportedOperationException("FileVoucherRepository doesn't support updateVoucher()");
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException("FileVoucherRepository doesn't support deleteAll()");
    }
}
