package com.prgrms.voucher_manager.repository;

import com.prgrms.voucher_manager.exception.EmptyVoucherException;
import com.prgrms.voucher_manager.voucher.FixedAmountVoucher;
import com.prgrms.voucher_manager.voucher.PercentDiscountVoucher;
import com.prgrms.voucher_manager.voucher.Voucher;
import com.prgrms.voucher_manager.voucher.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.UUID;

@Repository
@Profile("dev")
public class FileVoucherRepository implements VoucherRepository {

    private static final Logger logger = LoggerFactory.getLogger(FileVoucherRepository.class);
    private static final File file = new File("voucherDB.csv");
    private static BufferedWriter bw = getBufferWriter(file);
    private static BufferedReader br;

    @Override
    public void save(Voucher voucher) {
        logger.info("FileVoucherRepository - save");
        try{
            bw.write(voucher.getVoucherType()
                    + "," + voucher.getVoucherID()
                    + "," + voucher.getValue()
                    + "\n");
            bw.flush();
//            bw.close();
        } catch (IOException e) {
            logger.error("FileVoucherRepository - save Error");
            e.printStackTrace();
        }
    }

    @Override
    public void findAll() {
        String lineString = "";
        try{
            br = getBufferReader(file);
            while((lineString = br.readLine()) != null){
                String[] lineArr = lineString.split(",");

                if(lineArr[0].equals(VoucherType.FixedAmountVoucher.toString())){
                    System.out.println(FixedAmountVoucher.builder()
                            .id(UUID.fromString(lineArr[1]))
                            .amount(Long.parseLong(lineArr[2]))
                            .build()
                            .getInfo());
                }else{
                    System.out.println(PercentDiscountVoucher.builder()
                            .id(UUID.fromString(lineArr[1]))
                            .percent(Long.parseLong(lineArr[2]))
                            .build()
                            .getInfo());
                }
            }
        } catch (IOException e) {
            throw new EmptyVoucherException("MemoryVoucherRepository 가 비어있습니다.");
           // e.printStackTrace();
        }
        logger.info("FileVoucherRepository - findAll");
    }

    @Override
    public int getRepositorySize() {
        int size = 0;
        String line;
        try{
            br = getBufferReader(file);
            while((line = br.readLine()) != null){
                size++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return size;
    }

    public static BufferedWriter getBufferWriter(File file){
        try{
            bw = new BufferedWriter(new FileWriter(file, true));
        }catch (IOException e) {
            e.printStackTrace();
            logger.error("Error bufferWriter");
        }
        return bw;
    }

    public static BufferedReader getBufferReader(File file){
        try{
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            logger.error("Not Found File {}",file);
            e.printStackTrace();
        }
        return br;
    }

}
