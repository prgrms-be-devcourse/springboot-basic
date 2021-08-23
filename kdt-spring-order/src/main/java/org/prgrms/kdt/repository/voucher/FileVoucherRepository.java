package org.prgrms.kdt.repository.voucher;

import org.prgrms.kdt.Factory.VoucherFactory;
import org.prgrms.kdt.domain.voucher.Voucher;
import org.prgrms.kdt.domain.voucher.VoucherType;
import org.prgrms.kdt.io.file.IO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Profile("default")
public class FileVoucherRepository implements VoucherRepository {

    private static final String SEPARATOR = " ";
    private final IO io;

    public FileVoucherRepository(@Qualifier("txtFileIo") IO io) {
        this.io = io;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        io.mark();
        try {
            String line;
            while((line = io.readLine()) != null) {
                Voucher voucher = createVoucher(line);

                if (voucherId.equals(voucher.getVoucherId())) {
                    io.reset();
                    return Optional.of(voucher);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        io.reset();
        return Optional.empty();
    }

    @Override
    public List<Voucher> findAll() {
        io.mark();
        List<Voucher> list = new ArrayList<>();
        try {
            String line;
            while (isNotEmpty((line = io.readLine()))) {
                Voucher voucher = createVoucher(line);
                list.add(voucher);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        io.reset();
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
            io.writeLine(voucher.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return voucher;
    }

    private UUID mapToUUID(String voucherId) {
        return UUID.fromString(voucherId);
    }

    private long mapToLong(String voucherValue) {
        return Long.parseLong(voucherValue);
    }

    private VoucherType mapToVoucherType(String voucherType) {
        return VoucherType.valueOf(voucherType);
    }

    private boolean isNotEmpty(String input) {
        return input != null && input.length() != 0;
    }

}
