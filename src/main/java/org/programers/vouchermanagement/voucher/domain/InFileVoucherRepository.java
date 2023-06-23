package org.programers.vouchermanagement.voucher.domain;

import org.programers.vouchermanagement.voucher.exception.NoSuchVoucherException;
import org.programers.vouchermanagement.util.Converter;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Primary
@Profile("dev")
public class InFileVoucherRepository implements VoucherRepository {

    private static final File file = new File("src/main/resources/voucher.txt");

    @Override
    public Voucher save(Voucher voucher) {
        try {
            Writer writer = new FileWriter(file, true);
            writer.write(Converter.toString(voucher));
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
                Voucher voucher = Converter.toVoucher(line);
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

    @Override
    public List<Voucher> findAll() {
        List<Voucher> result = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                Voucher voucher = Converter.toVoucher(line);
                result.add(voucher);
            }
            reader.close();
            return result;
        } catch (IOException e) {
            throw new NoSuchVoucherException("IO 문제로 바우처가 조회되지 않았습니다.");
        }
    }
}
