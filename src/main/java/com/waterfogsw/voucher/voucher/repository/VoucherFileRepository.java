package com.waterfogsw.voucher.voucher.repository;

import com.waterfogsw.voucher.utils.FileUtils;
import com.waterfogsw.voucher.voucher.domain.Voucher;
import com.waterfogsw.voucher.voucher.domain.VoucherType;
import com.waterfogsw.voucher.voucher.dto.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Profile({"default"})
@Repository
public class VoucherFileRepository implements VoucherRepository {

    private static final String INVALID_PATH = "Invalid Path";
    private final String repositoryPath;

    public VoucherFileRepository(@Value("${file-repository-path}") String path) {
        if (!FileUtils.validatePath(path)) {
            throw new IllegalArgumentException(INVALID_PATH);
        }
        this.repositoryPath = path;
    }

    @PostConstruct
    public void init() {
        FileUtils.initFilePath(repositoryPath);
    }

    @PreDestroy
    public void destroy() {
        FileUtils.initFilePath(repositoryPath);
    }

    @Override
    public Voucher save(Voucher voucher) {
        if (voucher == null) {
            throw new IllegalArgumentException();
        }

        if (voucher.getId() == null) {
            Voucher newVoucher = createVoucherEntity(voucher);
            FileUtils.write(repositoryPath, newVoucher.getId().toString(), newVoucher);
            return newVoucher;
        }

        FileUtils.write(repositoryPath, repositoryPath, voucher);
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return FileUtils.readAll(repositoryPath, Voucher.class);
    }

    @Override
    public Optional<Voucher> findById(long id) {
        return Optional.empty();
    }

    @Override
    public void deleteById(long id) {

    }

    @Override
    public List<Voucher> findByType(VoucherType type) {
        return new ArrayList<>();
    }

    @Override
    public List<Voucher> findByDuration(Duration duration) {
        return new ArrayList<>();
    }

    @Override
    public List<Voucher> findByTypeAndDuration(VoucherType type, Duration duration) {
        return new ArrayList<>();
    }

    private Voucher createVoucherEntity(Voucher voucher) {
        Long id = KeyGenerator.keyGenerate();
        return Voucher.toEntity(id, voucher);
    }
}
