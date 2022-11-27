package prgms.vouchermanagementapp.repository;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import prgms.vouchermanagementapp.configuration.FileConfig;
import prgms.vouchermanagementapp.domain.Voucher;
import prgms.vouchermanagementapp.repository.util.FileManager;
import prgms.vouchermanagementapp.repository.util.VoucherContentConverter;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@Profile("file")
public class FileVoucherRepository implements VoucherRepository {

    private final FileConfig fileConfig;
    private final FileManager fileManager;

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
    public Optional<Voucher> findById(UUID voucherId) {
        throw new UnsupportedOperationException("FileVoucherRepository doesn't support findById()");
    }

    @Override
    public void updateDiscountLevel(UUID voucherId, long fixedDiscountLevel) {
        throw new UnsupportedOperationException("FileVoucherRepository doesn't support updateVoucher()");
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException("FileVoucherRepository doesn't support deleteAll()");
    }

    @Override
    public void deleteById(UUID voucherId) {
        throw new UnsupportedOperationException("FileVoucherRepository doesn't support deleteById()");
    }
}
