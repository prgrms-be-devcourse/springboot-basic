package com.programmers.voucher.domain.voucher.repository;

import com.programmers.voucher.domain.voucher.domain.Voucher;
import com.programmers.voucher.domain.voucher.dto.VoucherDto;
import com.programmers.voucher.global.exception.DataAccessException;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Repository
@Profile("dev")
public class VoucherFileRepository implements VoucherRepository {
    private final File file;

    public VoucherFileRepository(ResourceLoader resourceLoader) {
        Resource resource = resourceLoader.getResource("file:files/vouchers.csv");
        try {
            this.file = resource.getFile();
        } catch (IOException e) {
            throw new DataAccessException("File not found.");
        }
    }

    @Override
    public void save(Voucher voucher) {
        try (
                BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))
        ) {
            VoucherDto voucherDto = voucher.toDto();
            String voucherInfo = voucherDto.getCustomerId()
                    + "," + voucherDto.getVoucherType()
                    + "," + voucherDto.getAmount();
            bw.write(voucherInfo);
            bw.newLine();
        } catch (IOException e) {
            throw new DataAccessException("Cannot write.");
        }
    }

    @Override
    public List<Voucher> findAll() {
        return null;
    }
}
