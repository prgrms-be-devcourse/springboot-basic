package com.programmers.voucher.domain.voucher.repository;

import com.programmers.voucher.domain.voucher.domain.Voucher;
import com.programmers.voucher.domain.voucher.domain.VoucherType;
import com.programmers.voucher.domain.voucher.dto.VoucherDto;
import com.programmers.voucher.global.exception.FileAccessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.text.MessageFormat;
import java.util.*;

import static com.programmers.voucher.global.util.CommonErrorMessages.CANNOT_ACCESS_FILE;
import static com.programmers.voucher.global.util.DataAccessConstants.UPDATE_ONE;

@Repository
@Profile("dev")
public class VoucherFileRepository implements VoucherRepository {
    private static final Logger LOG = LoggerFactory.getLogger(VoucherFileRepository.class);

    private final File file;

    public VoucherFileRepository(@Value("${file.vouchers.path}") String filePath,
                                 ResourceLoader resourceLoader) {
        Resource resource = resourceLoader.getResource("file:" + filePath);
        try {
            this.file = resource.getFile();
        } catch (IOException e) {
            String errorMessage = MessageFormat.format(CANNOT_ACCESS_FILE, filePath);

            LOG.error(errorMessage, e);
            throw new FileAccessException(errorMessage, e);
        }
    }

    @Override
    public void save(Voucher voucher) {
        try (
                BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))
        ) {
            String voucherInfo = voucherToCsv(voucher);
            bw.write(voucherInfo);
            bw.newLine();
        } catch (IOException e) {
            String errorMessage = MessageFormat.format(CANNOT_ACCESS_FILE, file.getPath());

            LOG.error(errorMessage, e);
            throw new FileAccessException(errorMessage, e);
        }
    }

    @Override
    public List<Voucher> findAll() {
        List<Voucher> vouchers = new ArrayList<>();
        try (
                BufferedReader br = new BufferedReader(new FileReader(file));
        ) {
            String nextLine;
            while ((nextLine = br.readLine()) != null) {
                Voucher voucher = csvToVoucher(nextLine);
                vouchers.add(voucher);
            }
        } catch (IOException e) {
            String errorMessage = MessageFormat.format(CANNOT_ACCESS_FILE, file.getPath());

            LOG.error(errorMessage, e);
            throw new FileAccessException(errorMessage, e);
        }

        return vouchers;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return findAll().stream()
                .filter(voucher -> Objects.equals(voucher.getVoucherId(), voucherId))
                .findAny();
    }

    @Override
    public void deleteById(UUID voucherId) {
        List<Voucher> vouchers = findAll();
        boolean removed = vouchers.removeIf(v -> Objects.equals(v.getVoucherId(), voucherId));
        if(removed) {
            IncorrectResultSizeDataAccessException ex = new IncorrectResultSizeDataAccessException(UPDATE_ONE, 0);
            LOG.error(ex.getMessage());
            throw ex;
        }

        saveAll(vouchers);
    }

    private void saveAll(List<Voucher> vouchers) {
        try (
                BufferedWriter bw = new BufferedWriter(new FileWriter(file))
        ) {
            for (Voucher voucher : vouchers) {
                String voucherInfo = voucherToCsv(voucher);
                bw.write(voucherInfo);
                bw.newLine();
            }
        } catch (IOException e) {
            String errorMessage = MessageFormat.format(CANNOT_ACCESS_FILE, file.getPath());

            LOG.error(errorMessage, e);
            throw new FileAccessException(errorMessage, e);
        }
    }

    private String voucherToCsv(Voucher voucher) {
        VoucherDto voucherDto = VoucherDto.from(voucher);
        return voucherDto.getVoucherId()
                + "," + voucherDto.getVoucherType()
                + "," + voucherDto.getAmount();
    }

    private Voucher csvToVoucher(String nextLine) {
        String[] voucherInfo = nextLine.split(",");
        UUID voucherId = UUID.fromString(voucherInfo[0]);
        VoucherType voucherType = VoucherType.valueOf(voucherInfo[1]);
        long amount = Long.parseLong(voucherInfo[2]);

        return voucherType.createVoucher(voucherId, amount);
    }
}
