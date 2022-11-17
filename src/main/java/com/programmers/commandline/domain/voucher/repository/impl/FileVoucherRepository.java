package com.programmers.commandline.domain.voucher.repository.impl;

import com.moandjiezana.toml.Toml;
import com.moandjiezana.toml.TomlWriter;
import com.programmers.commandline.domain.voucher.entity.Voucher;
import com.programmers.commandline.domain.voucher.entity.VoucherType;
import com.programmers.commandline.domain.voucher.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
@Profile("prod")
public class FileVoucherRepository implements VoucherRepository {

    private final String filePath;
    private final File file;

    FileVoucherRepository(@Value("${file.voucherResourcesPath}") String filePath) {
        this.filePath = filePath;
        this.file = new File(filePath);
    }

    @Override
    public String save(Voucher voucher) {
        try
        {
            TomlWriter tomlWriter = new TomlWriter();
            File file = new File(filePath + voucher.getVoucherId());
            tomlWriter.write(voucher, file);

            return voucher.getVoucherId();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<Voucher> findAll() {
        List<Voucher> voucherList = new ArrayList<>();
        try {
            File[] files = file.listFiles();
            for (File file : files) {
                Toml toml = new Toml().read(file);
                String type = toml.getString("voucherType");
                String id = toml.getString("voucherId");
                Long discount = toml.getLong("discount");

                Voucher voucher = createVoucherType(type).createVoucher(UUID.fromString(id),discount);
                voucherList.add(voucher);
            }
            return voucherList;
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private VoucherType createVoucherType(String type) {
        return VoucherType.valueOf(type);
    }
}
