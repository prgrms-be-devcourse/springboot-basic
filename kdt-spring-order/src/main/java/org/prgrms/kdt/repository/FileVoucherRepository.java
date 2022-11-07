package org.prgrms.kdt.repository;


import org.prgrms.kdt.voucher.*;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Repository
@Primary
public class FileVoucherRepository implements VoucherRepository {

    private final VoucherFactory voucherFactory;
    private final String repository = "fileRepository.txt";
    private final ClassPathResource resource = new ClassPathResource(repository);

    public FileVoucherRepository(VoucherFactory voucherFactory) {
        this.voucherFactory = voucherFactory;
    }


    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try {
            Path pathResource = Paths.get(resource.getURI());
            List<String> repositoryAllContent = Files.readAllLines(pathResource);


            String voucherIdString = voucherId.toString();
            List<Voucher> voucherList = new ArrayList<>();
            for (String str : repositoryAllContent) {
                String[] fields = str.split(",");

                if (voucherIdString.equals(fields[1])) {
                    return Optional.of(getVoucherStatus(fields));
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public void insert(Voucher voucher) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(resource.getFile(), true));

            String voucherTypeString = voucherFactory.createVoucherClassNameString(voucher);

            bw.write(voucherTypeString + "," + voucher.getVoucherId().toString() + "," + voucher.getDiscountValue());
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Voucher> getAllStoredVoucher() {
        List<Voucher> voucherList = new ArrayList<>();
        try {
            Path pathResource = Paths.get(resource.getURI());
            List<String> repositoryAllContent = Files.readAllLines(pathResource);


            for (String str : repositoryAllContent) {
                String[] fields = str.split(",");

                Voucher voucher = getVoucherStatus(fields);

                voucherList.add(voucher);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return voucherList;
    }

    private Voucher getVoucherStatus(String[] fields) {
        String voucherType = fields[0];
        String voucherId = fields[1];
        String discountValue = fields[2];

        Voucher voucher = voucherFactory.createVoucher(voucherType, voucherId, discountValue);

        return voucher;
    }

    @Override
    public void clear() {
        try {
            new FileWriter(resource.getFile(), false).close();
        }catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
