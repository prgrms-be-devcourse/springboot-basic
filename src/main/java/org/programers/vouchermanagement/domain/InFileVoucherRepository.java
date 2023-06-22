package org.programers.vouchermanagement.domain;

import org.programers.vouchermanagement.exception.NoSuchVoucherException;
import org.programers.vouchermanagement.util.VoucherConverter;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.Optional;
import java.util.UUID;

@Repository
@Profile("dev")
public class InFileVoucherRepository implements VoucherRepository {

    private static final File file = new File("src/main/resources/file_repository.txt");

    @Override
    public Voucher save(Voucher voucher) {
        try {
            Writer writer = new FileWriter(file, true);
            writer.write(VoucherConverter.toString(voucher));
            writer.flush();
            writer.close();
            return voucher;
        } catch (IOException e) {
            throw new NoSuchVoucherException("IO 문제로 바우처가 저장되지 않았습니다.");
        }
    }

    @Override
    public Optional<Voucher> findById(UUID id) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                Voucher voucher = VoucherConverter.toVoucher(line);
                if (voucher.getId() == id) {
                    return Optional.of(voucher);
                }
            }
            reader.close();
            return Optional.empty();
        } catch (IOException e) {
            throw new NoSuchVoucherException("IO 문제로 바우처가 조회되지 않았습니다.");
        }
    }
}
