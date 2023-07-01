package kr.co.springbootweeklymission.voucher.domain.repository;

import kr.co.springbootweeklymission.infrastructure.error.exception.FileIOException;
import kr.co.springbootweeklymission.infrastructure.error.exception.NotSupportedException;
import kr.co.springbootweeklymission.infrastructure.error.model.ResponseStatus;
import kr.co.springbootweeklymission.infrastructure.util.FileConverter;
import kr.co.springbootweeklymission.voucher.api.dto.response.VoucherResDTO;
import kr.co.springbootweeklymission.voucher.domain.entity.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Profile("file")
@Repository
public class InFileVoucherRepository implements VoucherRepository {
    private static final File VOUCHER_FILE = new File("src/main/resources/files/voucher_file.txt");

    @Override
    public Voucher save(Voucher voucher) {
        try {
            final Writer writer = new FileWriter(VOUCHER_FILE, true);
            final VoucherResDTO.FILE file = VoucherResDTO.FILE.toVoucherFile(voucher);
            writer.write(FileConverter.toVoucherString(file));
            writer.flush();
            writer.close();

            return voucher;
        } catch (IOException e) {
            throw new FileIOException(ResponseStatus.FAIL_IO_NOT_SAVE_VOUCHER);
        }
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

    @Override
    public void update(Voucher voucher) {
        throw new NotSupportedException(ResponseStatus.FAIL_NOT_SUPPORTED_UPDATE);
    }

    @Override
    public void delete(Voucher voucher) {
        throw new NotSupportedException(ResponseStatus.FAIL_NOT_SUPPORTED_DELETE);
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
