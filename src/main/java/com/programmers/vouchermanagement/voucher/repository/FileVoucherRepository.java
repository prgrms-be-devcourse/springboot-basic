package com.programmers.vouchermanagement.voucher.repository;

import com.programmers.vouchermanagement.voucher.domain.Voucher;
import com.programmers.vouchermanagement.voucher.exception.FileIOException;
import com.programmers.vouchermanagement.voucher.mapper.VoucherMapper;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@Profile("default")
public class FileVoucherRepository implements VoucherRepository {

    private static final File FILE = new File(System.getProperty("user.dir") + "/src/main/resources/data.csv");

    @Override
    public void save(Voucher voucher) {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE, true))) {

            String voucherData = VoucherMapper.fromEntity(voucher);

            bw.write(voucherData);
            bw.newLine();

            bw.flush();

        } catch (IOException e) {
            throw new FileIOException("Voucher not saved due to file issue. ");
        }
    }

    @Override
    public List<Voucher> findAll() {

        List<Voucher> vouchers = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(FILE));

            String data;

            while ((data = br.readLine()) != null) {

                String[] singleData = data.split(",");

                Voucher voucher = VoucherMapper.toEntity(singleData);
                vouchers.add(voucher);
            }

        } catch (FileNotFoundException e) {
            throw new FileIOException("File not found. ");

        } catch (IOException e) {
            throw new FileIOException("Voucher not read due to file issue. ");
        }

        return vouchers;
    }
}
