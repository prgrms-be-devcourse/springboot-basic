package org.programmers.springorder.voucher.repository;

import org.programmers.springorder.console.Console;
import org.programmers.springorder.consts.ErrorMessage;
import org.programmers.springorder.customer.model.Customer;
import org.programmers.springorder.utils.Properties;
import org.programmers.springorder.voucher.model.Voucher;
import org.programmers.springorder.voucher.model.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Profile("dev")
public class FileVoucherRepository implements VoucherRepository {

    private static final Logger logger = LoggerFactory.getLogger(FileVoucherRepository.class);
    private final Console console;

    public FileVoucherRepository(Console console) {
        this.console = console;
    }

    @Override
    public Voucher save(Voucher voucher) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(Properties.getVoucherFilePath(), true))) {
            String data = voucher.insertVoucherDataInFile();
            bw.write(data);
            bw.newLine();
        } catch (IOException e) {
            logger.error("errorMessage = {}", ErrorMessage.FILE_SAVE_ERROR_MESSAGE);
            console.printMessage(ErrorMessage.FILE_SAVE_ERROR_MESSAGE);
        }
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        List<Voucher> voucherList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(Properties.getVoucherFilePath()))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                UUID voucherId = UUID.fromString(data[0]);
                long discountValue = Long.parseLong(data[1]);
                VoucherType voucherType = VoucherType.valueOf(data[2]);
                Voucher voucher = Voucher.toVoucher(voucherId, discountValue, voucherType);
                if (data.length == 4) {
                    UUID customerId = UUID.fromString(data[3]);
                    voucher = Voucher.toVoucher(voucherId, discountValue, voucherType, customerId);
                }
                voucherList.add(voucher);
            }
        } catch (FileNotFoundException e) {
            logger.error("errorMessage = {}", ErrorMessage.FILE_NOT_EXIST_MESSAGE);
            console.printMessage(ErrorMessage.FILE_NOT_EXIST_MESSAGE);
        } catch (IOException e) {
            logger.error("errorMessage = {}", ErrorMessage.FILE_FAIL_LOADING_MESSAGE);
            console.printMessage(ErrorMessage.FILE_FAIL_LOADING_MESSAGE);
        }

        return voucherList;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return findAll().stream()
                .filter(voucher -> voucher.isSameId(voucherId))
                .findFirst();
    }

    //TODO: 구현 필요 기능(jdbc 우선)
    @Override
    public Voucher updateVoucherOwner(Voucher voucher, Customer customer) {
        List<Voucher> allVouchers = findAll();
        int index = allVouchers.indexOf(voucher);
        voucher.updateOwner(customer);
        allVouchers.set(index, voucher);
        writeAllVouchers(allVouchers);
        return voucher;
    }

    private void writeAllVouchers(List<Voucher> allVouchers) {
        clear();
        for (Voucher rewriteVoucher : allVouchers) {
            save(rewriteVoucher);
        }
    }

    @Override
    public List<Voucher> findAllByCustomerId(Customer customer) {
        return findAll().stream()
                .filter(voucher -> voucher.isSameCustomerId(customer.getCustomerId()))
                .toList();
    }

    @Override
    public void deleteVoucher(Voucher voucher) {
        List<Voucher> voucherList = findAll();
        voucherList.remove(voucher);
        writeAllVouchers(voucherList);
    }

    public void clear() {
        try (FileOutputStream fos = new FileOutputStream(Properties.getVoucherFilePath(), false)) {
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
