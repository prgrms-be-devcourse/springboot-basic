package org.prgrms.kdt.repository;

import org.prgrms.kdt.domain.voucher.Voucher;
import org.prgrms.kdt.Factory.VoucherFactory;
import org.prgrms.kdt.domain.voucher.VoucherType;
import org.prgrms.kdt.io.FileIo;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class FileVoucherRepository implements VoucherRepository{

    private static final String SEPARATOR = " ";
    private final FileIo fileIo;

    public FileVoucherRepository(FileIo fileIo) {
        this.fileIo = fileIo;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        Voucher voucher = null;
        try {
            String line;
            while((line = fileIo.readLine()) != null) {
                voucher = createVoucher(line);

                if (voucherId.equals(voucher.getVoucherId())) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        fileIo.reset();
        return Optional.ofNullable(voucher);
    }

    @Override
    public List<Voucher> findAll() {
        List<Voucher> list = new ArrayList<>();
        try {
            String line;
            while ((line = fileIo.readLine()) != null) {
                Voucher voucher = createVoucher(line);
                list.add(voucher);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        fileIo.reset();
        return list;
    }

    private Voucher createVoucher(String line) {
        String[] splitLine = line.split(SEPARATOR);
        UUID id = mapToUUID(splitLine[0]);
        VoucherType type = mapToVoucherType(splitLine[1]);
        long value = mapToLong(splitLine[2]);
        return VoucherFactory.createVoucher(id, type, value);
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

    private UUID mapToUUID(String str) {
        return UUID.fromString(str);
    }

    private long mapToLong(String str) {
        return Long.parseLong(str);
    }

    private VoucherType mapToVoucherType(String str) {
        return VoucherType.valueOf(str);
    }

}
