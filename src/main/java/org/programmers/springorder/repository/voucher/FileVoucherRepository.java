package org.programmers.springorder.repository.voucher;

import org.programmers.springorder.console.Console;
import org.programmers.springorder.constant.ErrorMessage;
import org.programmers.springorder.model.voucher.Voucher;
import org.programmers.springorder.model.voucher.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.*;

@Repository
public class FileVoucherRepository {

    private static final Logger logger = LoggerFactory.getLogger(FileVoucherRepository.class);
    @Value(("${voucherFilePath}"))
    private String filePath;
    private final Console console;

    public FileVoucherRepository(Console console) {
        this.console = console;
    }

    public UUID save(Voucher voucher) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {
            String data = voucher.insertVoucherDataInFile();
            bw.write(data);
            bw.newLine();
        } catch (IOException e) {
            logger.error("errorMessage = {}", ErrorMessage.FILE_SAVE_ERROR_MESSAGE);
            console.printMessage(ErrorMessage.FILE_SAVE_ERROR_MESSAGE);
        }
        return voucher.getVoucherId();
    }

    public List<Voucher> findAll() {
        List<Voucher> voucherList = new ArrayList<>(); // TODO: 동시성 처리 ReadLock, WriteLock synchronized x queue

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                UUID voucherId = UUID.fromString(data[0]);
                long discountValue = Long.parseLong(data[1]);
                VoucherType voucherType = VoucherType.valueOf(data[2]);

                Voucher voucher = Voucher.toVoucher(voucherId, discountValue, voucherType);
                voucherList.add(voucher);
            }
        } catch (FileNotFoundException e) {
            logger.error("errorMessage = {}", ErrorMessage.FILE_NOT_EXIST_MESSAGE);
            console.printMessage(ErrorMessage.FILE_NOT_EXIST_MESSAGE);
        } catch (IOException e) {
            logger.error("errorMessage = {}", ErrorMessage.FILE_FAIL_LOADING_MESSAGE);
            console.printMessage(ErrorMessage.FILE_FAIL_LOADING_MESSAGE);
        }

        return Collections.unmodifiableList(voucherList);
    }

    public Optional<Voucher> findById(UUID voucherId) {
        return findAll().stream()
                .filter(voucher -> voucher.isSameId(voucherId))
                .findFirst();
    }

}
