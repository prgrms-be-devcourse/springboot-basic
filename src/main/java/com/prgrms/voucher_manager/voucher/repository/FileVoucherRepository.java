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
                            .voucherId(UUID.fromString(lineArr[3]))
                            .amount(Long.parseLong(lineArr[5]))
                            .build();
                    System.out.println(voucher.toString());
                    vouchers.add(voucher);
                }else{
                    PercentDiscountVoucher voucher = PercentDiscountVoucher.builder()
                            .voucherId(UUID.fromString(lineArr[3]))
                            .percent(Long.parseLong(lineArr[5]))
                            .build();
                    System.out.println(voucher.toString());
                    vouchers.add(voucher);
                }
            }
            return vouchers;
        } catch (IOException e) {
            throw new EmptyRepositoryException("FileVoucherRepository 가 비어있습니다.");
        }
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        String lineString = "";
        try{
            br = getBufferReader(file);
            while((lineString = br.readLine()) != null){
                String[] lineArr = lineString.split("=|,");
                String type = lineArr[1];
                String id = lineArr[3];

                if(id.equals(voucherId.toString())) {
                    if(type.equals(VoucherType.FixedAmountVoucher.toString())) {
                        return Optional.of(FixedAmountVoucher.builder()
                                .voucherId(UUID.fromString(id))
                                .amount(Long.parseLong(lineArr[5]))
                                .build());

                    } else {
                        return  Optional.of(PercentDiscountVoucher.builder()
                                .voucherId(UUID.fromString(id))
                                .percent(Long.parseLong(lineArr[5]))
                                .build());
                    }
                }
            }
            return Optional.empty();
        } catch (IOException e) {
            throw new EmptyRepositoryException("FileVoucherRepository 가 비어있습니다.");
        }
    }


    @Override
    public List<Voucher> findByType(String type) {
        String lineString = "";
        List<Voucher> vouchers = new ArrayList<>();
        try{
            br = getBufferReader(file);
            while((lineString = br.readLine()) != null){
                String[] lineArr = lineString.split("=|,");
                VoucherType voucherType = VoucherType.getVoucherType(type);

                if(voucherType.equals(VoucherType.FixedAmountVoucher)) {
                    vouchers.add(FixedAmountVoucher.builder()
                                .voucherId(UUID.fromString(lineArr[3]))
                                .amount(Long.parseLong(lineArr[5]))
                                .build());
                } else {
                    vouchers.add(PercentDiscountVoucher.builder()
                            .voucherId(UUID.fromString(lineArr[3]))
                            .percent(Long.parseLong(lineArr[5]))
                            .build());
                }
            }
            return vouchers;
        } catch (IOException e) {
            throw new EmptyRepositoryException("FileVoucherRepository 가 비어있습니다.");
        }
    }

    //TODO
    // 바우처를 수정 후 새로 다시 파일을 작성해야 하나?
    // 한 줄씩 바로 수정하는 방법 찾아보기.
    @Override
    public Voucher update(Voucher voucher) {
        return null;
    }

    // TODO - 한 줄씩 바로 수정하는 방법 찾으면 빈 문자열로 수정하여 저장
    @Override
    public Voucher delete(Voucher voucher) {
        return null;
    }

    @Override
    public int count() {
        int result = 0;
        String lineString = "";
        try{
            br = getBufferReader(file);
            while((lineString = br.readLine()) != null) result++;
        } catch (IOException e) {
            throw new EmptyRepositoryException("FileVoucherRepository 가 비어있습니다.");
        }
        return result;
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
