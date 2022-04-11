package com.prgrms.kdt.springbootbasic.repository;

import com.prgrms.kdt.springbootbasic.entity.FixedAmountVoucher;
import com.prgrms.kdt.springbootbasic.entity.PercentDiscountVoucher;
import com.prgrms.kdt.springbootbasic.entity.Voucher;
import com.prgrms.kdt.springbootbasic.outputPackage.ConsoleOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Profile("file")
@Repository
public class FileVoucherRepository implements VoucherRepository{
    private static final Logger logger = LoggerFactory.getLogger(FileVoucherRepository.class);

    private final ResourceLoader resourceLoader;

    private final String filePath;

    private static Map<UUID, Voucher> voucherStorage = new ConcurrentHashMap<>();
    private final File file;

    public FileVoucherRepository(ResourceLoader resourceLoader, @Value("${voucher.file}") String filePath) {
        this.resourceLoader = resourceLoader;
        this.filePath = filePath;
        file = initFile();
        initVoucherStorage();
    }

    private File initFile(){
        File file = null;
        try{
            file = resourceLoader.getResource(filePath).getFile();
            logger.info("[FileVoucherRepository:initFile] File has been successfully loaded : " + filePath);
        }catch(IOException e){
            logger.error("[FileVoucherRepository:initFile] Can't find file : "+ filePath);
            file = createFile();
        }finally {
            return file;
        }
    }

    private File createFile() {
        File file =  new File(filePath);
        try {
            file.createNewFile();
            logger.info("[FileVoucherRepository:createFile] File has been successfully created :" + filePath);
        }catch(Exception exception) {
            logger.error("[FileVoucherRepository:createFile] Error occurred while creating file : " + filePath);
        }
        return file;
    }

    @Override
    public Voucher findById(UUID voucherId) {
        return voucherStorage.get(voucherId);
    }

    @Override
    public Voucher saveVoucher(Voucher voucher) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file,true);

            if (voucher instanceof FixedAmountVoucher) {
                fileWriter.write(MessageFormat.format("1,{0},{1}\n", voucher.getVoucherId(), voucher.getDiscountAmount()));
            } else if (voucher instanceof PercentDiscountVoucher) {
                fileWriter.write(MessageFormat.format("2,{0},{1}\n", voucher.getVoucherId(), voucher.getDiscountAmount()));
            }
            fileWriter.flush();
            voucherStorage.put(voucher.getVoucherId(), voucher);

        } catch (Exception e) {
            logger.error("[FileVoucherRepository:saveVoucher] Error has been occurred while saving voucher to file");
        }finally {
            if (fileWriter != null){
                try {
                    fileWriter.close();
                }catch(Exception e){

                }
            }
            return voucher;
        }
    }

    @Override
    public List<Voucher> getAllVouchers() {
        return voucherStorage.values().stream().collect(Collectors.toList());
    }

    private void initVoucherStorage(){
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line = bufferedReader.readLine();
            while (line != null){
                String[] lineArr = line.split(",");
                int type = Integer.parseInt(lineArr[0]);
                UUID voucherId = UUID.fromString(lineArr[1]);
                long discountAmount = Integer.parseInt(lineArr[2]);
                Voucher voucher = null;

                switch (type){
                    case 1:
                        voucher = new FixedAmountVoucher(voucherId,discountAmount);
                        break;
                    case 2:
                        voucher = new PercentDiscountVoucher(voucherId,discountAmount);
                        break;
                }

                voucherStorage.put(voucher.getVoucherId(), voucher);
                line = bufferedReader.readLine();
            }
        }catch(Exception e){
            logger.error("[FileVoucherRepository:initVoucherStorage] Error has been occurred while saving vouchers from file to HashMap");
        }finally {
            if (fileReader != null){
                try {
                    fileReader.close();
                }catch(Exception e){

                }
            }
        }
    }

    private void saveVoucherStorage(){
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file);

            Set<UUID> keySet = voucherStorage.keySet();
            List keyList = keySet.stream().collect(Collectors.toList());
            for (int i = 0; i < keyList.size(); i++) {
                Voucher nowVoucher = voucherStorage.get(keyList.get(i));

                if (nowVoucher instanceof FixedAmountVoucher) {
                    fileWriter.write(MessageFormat.format("1,{0},{1}\n", nowVoucher.getVoucherId(), nowVoucher.getDiscountAmount()));
                } else if (nowVoucher instanceof PercentDiscountVoucher) {
                    fileWriter.write(MessageFormat.format("2,{0},{1}\n", nowVoucher.getVoucherId(), nowVoucher.getDiscountAmount()));
                }
            }
            fileWriter.flush();
        } catch (Exception e) {

        }finally {
            if (fileWriter != null){
                try {
                    fileWriter.close();
                }catch(Exception e){

                }
            }
        }
    }
}
