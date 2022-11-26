package com.programmers.commandline.domain.voucher.repository.impl;

import com.moandjiezana.toml.Toml;
import com.moandjiezana.toml.TomlWriter;
import com.programmers.commandline.domain.voucher.entity.Voucher;
import com.programmers.commandline.domain.voucher.entity.VoucherType;
import com.programmers.commandline.domain.voucher.repository.VoucherRepository;
import com.programmers.commandline.global.io.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Profile("file")
public class VoucherFileRepository implements VoucherRepository {

    private final String filePath;

    public VoucherFileRepository(@Value("${file.voucherResourcesPath}") String filePath) {
        this.filePath = filePath;
    }

    @Override
    public Voucher insert(Voucher voucher) {
        try {
            TomlWriter tomlWriter = new TomlWriter();
            File file = new File(filePath + voucher.getId());
            tomlWriter.write(voucher, file);

            return voucher;
        } catch (IOException e) {
            throw new RuntimeException(Message.CONSUMER_FILE_WRITE_ERROR.getMessage(), e);
        }
    }

    @Override
    public Voucher update(Voucher voucher) {
        TomlWriter tomlWriter = new TomlWriter();
        File voucherFile = new File(filePath + voucher.getId());
        try {
            if (!voucherFile.exists()) {
                throw new NullPointerException(Message.NULL_POINT_FILE.getMessage());
            }
            tomlWriter.write(voucher, voucherFile);
            return new Toml().to(Voucher.class);
        } catch (IOException e) {
            throw new RuntimeException(Message.CONSUMER_FILE_WRITE_ERROR.getMessage(), e);
        }
    }

    @Override
    public int count() {
        File voucherFile = new File(filePath);
        return voucherFile.list().length;
    }

    @Override
    public List<Voucher> findAll() {
        List<Voucher> vouchers = new ArrayList<>();
        File[] voucherFiles = new File(filePath).listFiles();

        for (File voucherFile : voucherFiles) {
            Voucher voucher = convertTomlToVoucher(voucherFile);
            vouchers.add(voucher);
        }
        return vouchers;
    }

    @Override
    public Optional<Voucher> findById(String voucherId) {
        File voucherFile = new File(filePath + voucherId);
        Voucher voucher = convertTomlToVoucher(voucherFile);
        return Optional.ofNullable(voucher);
    }

    @Override
    public void deleteAll() {
        File[] voucherFiles = new File(filePath).listFiles();

        for (File voucherFile : voucherFiles) {
            voucherFile.delete();
        }
    }

    @Override
    public void deleteById(String id) {

    }

    private Voucher convertTomlToVoucher(File file) {
        Toml toml = new Toml();
        Toml voucherToml = toml.read(file);

        UUID id = UUID.fromString(voucherToml.getString("id"));
        VoucherType type = VoucherType.valueOf(voucherToml.getString("type"));
        long discount = Long.parseLong(voucherToml.getString("discount"));
        LocalDateTime createdAt = LocalDateTime.parse(voucherToml.getString("createdAt"));

        return type.createVoucher(id, discount, createdAt);
    }
}
