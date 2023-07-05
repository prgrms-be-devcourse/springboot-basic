package com.programmers.voucher.domain.voucher.repository;

import com.programmers.voucher.domain.voucher.domain.Voucher;
import com.programmers.voucher.domain.voucher.domain.VoucherType;
import com.programmers.voucher.domain.voucher.dto.VoucherDto;
import com.programmers.voucher.global.exception.DataAccessException;
import com.programmers.voucher.global.util.CommonErrorMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.programmers.voucher.global.util.CommonErrorMessages.CANNOT_ACCESS_FILE;

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
            String errorMessage = CommonErrorMessages.addFilePath(CANNOT_ACCESS_FILE, filePath);
            LOG.error(errorMessage, e);
            throw new DataAccessException(errorMessage, e);
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
            String errorMessage = CommonErrorMessages.addFilePath(CANNOT_ACCESS_FILE, file.getPath());
            LOG.error(errorMessage, e);
            throw new DataAccessException(errorMessage, e);
        }
    }

    private String voucherToCsv(Voucher voucher) {
        VoucherDto voucherDto = voucher.toDto();
        return voucherDto.getCustomerId()
                + "," + voucherDto.getVoucherType()
                + "," + voucherDto.getAmount();
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
            String errorMessage = CommonErrorMessages.addFilePath(CANNOT_ACCESS_FILE, file.getPath());
            LOG.error(errorMessage, e);
            throw new DataAccessException(errorMessage, e);
        }

        return vouchers;
    }

    private Voucher csvToVoucher(String nextLine) {
        String[] voucherInfo = nextLine.split(",");
        UUID voucherId = UUID.fromString(voucherInfo[0]);
        VoucherType voucherType = VoucherType.valueOf(voucherInfo[1]);
        long amount = Long.parseLong(voucherInfo[2]);

        return voucherType.createVoucher(voucherId, amount);
    }
}
