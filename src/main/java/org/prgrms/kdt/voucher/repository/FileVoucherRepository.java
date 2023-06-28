package org.prgrms.kdt.voucher.repository;

import org.prgrms.kdt.util.Converter;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Profile("file")
@Repository // proxy -> new Proxy(FileVoucherRepository);
public class FileVoucherRepository implements VoucherRepository{
/*
    private static class ProxyRepository {

        VoucherRepository repository;

        public Optional<Voucher> findById(UUID voucherId) {
            try {
                repository.findById(voucherId);
            } catch (IOException e) {
                throw new DataAccessException(); // -> RuntimeException
            }
        }
    }
*/
    // data load -> repository?
    // run -> file 1 read -> load -> map, List, Collection -> (running) -> sync -> -> close -> persist(save)
    @Value("${filePath.voucher}")
    private String filePath;

    @Override
    public Optional<Voucher> findById(UUID voucherId) {

        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line = "";

        while ((line = reader.readLine()) != null) {
            String[] record = Converter.stringToArray(line, ",");
            if (record[0].equals(voucherId)) {
                return Optional.of(Converter.stringArrToVoucher(record));
            }
        }

        return Optional.empty();
    }

    @Override
    public Voucher insert(Voucher voucher) throws IOException {
        FileWriter writer = new FileWriter(filePath, true);
        String voucherString = Converter.voucherToString(voucher);
        writer.append(voucherString);
        writer.append("\n");
        writer.close();
        return voucher;
    }

    @Override
    public List<Voucher> findAll() throws IOException {
        List<Voucher> vouchers = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line = "";

        while ((line = reader.readLine()) != null) {
            vouchers.add(Converter.stringToVoucher(line));
        }
        return vouchers;
    }
}
