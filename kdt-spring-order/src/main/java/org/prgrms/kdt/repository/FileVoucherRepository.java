package org.prgrms.kdt.repository;


import org.prgrms.kdt.entity.Voucher;
import org.prgrms.kdt.voucher.VoucherFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Profile({"dev", "test"})
public class FileVoucherRepository implements VoucherRepository {

    private static final Logger logger = LoggerFactory.getLogger(FileVoucherRepository.class);
    private final VoucherFactory voucherFactory;

    private final String filePath;
    private final ClassPathResource resource;

    public FileVoucherRepository(VoucherFactory voucherFactory, @Value("${kdt.log-file}") String filePath) {
        this.voucherFactory = voucherFactory;
        this.filePath = filePath;
        this.resource = new ClassPathResource(filePath);
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
            logger.error("{} {}", e.getMessage(), e.getStackTrace());
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
            logger.error("{} {}", e.getMessage(), e.getStackTrace());
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
            logger.error("{} {}", e.getMessage(), e.getStackTrace());
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
            logger.error("{} {}", e.getMessage(), e.getStackTrace());
        }
    }
}
