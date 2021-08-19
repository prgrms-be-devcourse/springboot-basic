package org.prgrms.kdt.repository;

import org.prgrms.kdt.domain.voucher.Voucher;
import org.prgrms.kdt.io.FileIo;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class FileVoucherRepository implements VoucherRepository{

    private final FileIo fileIo;

    public FileVoucherRepository(FileIo fileIo) {
        this.fileIo = fileIo;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.empty();
    }

    @Override
    public List<Voucher> findAll() {
        return null;
    }

    @Override
    public Voucher insert(Voucher voucher) {
        try {
            fileIo.writeLine(voucher.toString()+"\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return voucher;
    }
}
