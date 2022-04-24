package com.prgrms.voucher_manager.voucher.repository;

import com.prgrms.voucher_manager.exception.EmptyRepositoryException;
import com.prgrms.voucher_manager.voucher.FixedAmountVoucher;
import com.prgrms.voucher_manager.voucher.PercentDiscountVoucher;
import com.prgrms.voucher_manager.voucher.Voucher;
import com.prgrms.voucher_manager.voucher.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.annotation.PreDestroy;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Profile("dev")
public class FileVoucherRepository implements VoucherRepository {

    private static final Logger logger = LoggerFactory.getLogger(FileVoucherRepository.class);
    private static final File file = new File("voucherDB.csv");
    private static BufferedWriter bw = getBufferWriter(file);
    private static BufferedReader br;

    @Override
    public Voucher insert(Voucher voucher) {
        logger.info("FileVoucherRepository - save");
        try{
            bw.write(voucher.toString());
            bw.flush();
            return voucher;
        } catch (IOException e) {
            logger.error("FileVoucherRepository - save Error", e);
        }
        return null;
    }

    @Override
    public List<Voucher> findAll() {
        String lineString = "";
        List<Voucher> vouchers = new ArrayList<>();
        try{
            br = getBufferReader(file);
            while((lineString = br.readLine()) != null){
                String[] lineArr = lineString.split("=|,");
                if(lineArr[1].equals(VoucherType.FixedAmountVoucher.toString())){
                    FixedAmountVoucher voucher = FixedAmountVoucher.builder()
                            .id(UUID.fromString(lineArr[3]))
                            .amount(Long.parseLong(lineArr[5]))
                            .build();
                    System.out.println(voucher.toString());
                    vouchers.add(voucher);
                }else{
                    PercentDiscountVoucher voucher = PercentDiscountVoucher.builder()
                            .id(UUID.fromString(lineArr[3]))
                            .percent(Long.parseLong(lineArr[5]))
                            .build();
                    System.out.println(voucher.toString());
                    vouchers.add(voucher);
                }
            }
            return vouchers;
        } catch (IOException e) {
            throw new EmptyRepositoryException("MemoryVoucherRepository 가 비어있습니다.");
        }
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.empty();
    }

    @Override
    public List<Voucher> findByType(String type) {
        return null;
    }

    @Override
    public Voucher update(Voucher voucher) {
        return null;
    }

    @Override
    public int count() {
        return 0;
    }


    private static BufferedWriter getBufferWriter(File file){
        try{
            bw = new BufferedWriter(new FileWriter(file, true));
        }catch (IOException e) {
            logger.error("Error bufferWriter", e);
        }
        return bw;
    }

    private static BufferedReader getBufferReader(File file){
        try{
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            logger.error("Not Found File {}",file);
        }
        return br;
    }

    @PreDestroy
    private void preDestroy() throws IOException {
        br.close();
        bw.close();
    }

}
