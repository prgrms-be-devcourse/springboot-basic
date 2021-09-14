package org.prgrms.kdt.repository.voucher;

import org.prgrms.kdt.domain.voucher.Voucher;
import org.prgrms.kdt.domain.voucher.VoucherSearch;
import org.prgrms.kdt.domain.voucher.VoucherType;
import org.prgrms.kdt.factory.VoucherFactory;
import org.prgrms.kdt.io.IO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Profile("local")
public class FileVoucherRepository implements VoucherRepository {

    private static final Logger logger = LoggerFactory.getLogger(FileVoucherRepository.class);
    private static final String SEPARATOR = " ";
    private final IO io;

    public FileVoucherRepository(@Qualifier("txtFileIo") IO io) {
        this.io = io;
    }

    @Override
    public Optional<Voucher> find(Voucher voucher) {
        return Optional.empty();
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        io.mark();
        try {
            String line;
            while(isNotEmpty(line = io.readLine())) {
                Voucher voucher = createVoucher(line);

                if (voucherId.equals(voucher.getVoucherId())) {
                    io.reset();
                    return Optional.of(voucher);
                }
            }
        } catch (Exception e) {
            logger.error(MessageFormat.format("Exception at {0}: {1}", this.getClass().getSimpleName(), e.getMessage()));
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
        } catch (Exception e) {
            logger.error(MessageFormat.format("Exception at {0}: {1}", this.getClass().getSimpleName(), e.getMessage()));
            e.printStackTrace();
        }
        io.reset();
        return list;
    }

    @Override
    public List<Voucher> findAll(VoucherSearch search) {
        return null;
    }


    private Voucher createVoucher(String line) {
        String[] splitLine = line.split(SEPARATOR);
        UUID id = mapToUUID(splitLine[0]);
        VoucherType type = mapToVoucherType(splitLine[1]);
        long value = mapToLong(splitLine[2]);
        return VoucherFactory.createVoucher(id, type, value, LocalDateTime.now());
    }

    @Override
    public Voucher insert(Voucher voucher) {
        try {
            io.writeLine(voucher.toString());
            logger.info(MessageFormat.format("Create at {0}: ", this.getClass().getSimpleName(), voucher.toString()));
        } catch (IOException e) {
            logger.error(MessageFormat.format("Exception at {0}: {1}", this.getClass().getSimpleName(), e.getMessage()));
            e.printStackTrace();
        }
        return voucher;
    }

    @Override
    public Voucher update(Voucher voucher) {
        return null;
    }

    @Override
    public void delete(Voucher voucher) {

    }

    @Override
    public void deleteById(UUID voucherId) {

    }

    @Override
    public void deleteAll() {

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
