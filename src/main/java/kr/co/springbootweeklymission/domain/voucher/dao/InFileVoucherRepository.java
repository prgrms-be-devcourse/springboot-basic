package kr.co.springbootweeklymission.domain.voucher.dao;

import kr.co.springbootweeklymission.domain.voucher.dto.response.VoucherResDTO;
import kr.co.springbootweeklymission.domain.voucher.entity.Voucher;
import kr.co.springbootweeklymission.global.error.exception.FileIOException;
import kr.co.springbootweeklymission.global.error.model.ResponseStatus;
import kr.co.springbootweeklymission.global.util.FileConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Profile("dev")
@Slf4j
@Repository
public class InFileVoucherRepository implements VoucherRepository {
    private static final File VOUCHER_FILE = new File("src/main/resources/file/voucher_file.txt");

    @Override
    public Voucher save(Voucher voucher) {

        try {
            final Writer writer = new FileWriter(VOUCHER_FILE, true);
            final VoucherResDTO.FILE file = Voucher.toVoucherFile(voucher);
            writer.write(FileConverter.toVoucherString(file));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            log.warn("error : " + e.getMessage());
            throw new FileIOException(ResponseStatus.FAIL_IO_NOT_SAVE_VOUCHER);
        }

        return voucher;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try {
            final BufferedReader reader = new BufferedReader(new FileReader(VOUCHER_FILE));
            final Optional<Voucher> voucher = getVoucherById(voucherId, reader);
            reader.close();
            return voucher;
        } catch (IOException e) {
            throw new FileIOException(ResponseStatus.FAIL_NOT_FOUND_VOUCHER);
        }
    }

    @Override
    public List<Voucher> findAll() {
        try {
            final BufferedReader reader = new BufferedReader(new FileReader(VOUCHER_FILE));
            final List<Voucher> vouchers = getVouchers(reader);
            reader.close();
            return vouchers;
        } catch (IOException e) {
            throw new FileIOException(ResponseStatus.FAIL_NOT_FOUND_VOUCHER);
        }
    }

    private static Optional<Voucher> getVoucherById(UUID voucherId,
                                                    BufferedReader reader) throws IOException {
        String info = reader.readLine();

        while (info != null) {
            final Voucher voucher = FileConverter.toVoucher(info);

            if (voucher.getVoucherId().equals(voucherId)) {
                return Optional.of(voucher);
            }

            info = reader.readLine();
        }

        return Optional.empty();
    }

    private static List<Voucher> getVouchers(BufferedReader reader) throws IOException {
        final List<Voucher> vouchers = new ArrayList<>();
        String info = reader.readLine();

        while (info != null) {
            vouchers.add(FileConverter.toVoucher(info));
            info = reader.readLine();
        }

        return vouchers;
    }
}
