package org.prgms.voucherProgram.voucher.repository;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.prgms.voucherProgram.customer.exception.WrongFileException;
import org.prgms.voucherProgram.voucher.domain.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("file")
public class FileVoucherRepository implements VoucherRepository {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String voucherFilePath;

    public FileVoucherRepository(@Value("${file.path.voucher}") String voucherFilePath) {
        this.voucherFilePath = voucherFilePath;
    }

    @Override
    public Voucher save(Voucher voucher) {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(
            new FileOutputStream(voucherFilePath, true))) {
            objectOutputStream.writeObject(voucher);
        } catch (IOException e) {
            logger.error("Fail to find a voucher file");
            throw new WrongFileException();
        }
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        List<Voucher> vouchers = new ArrayList<>();
        try (FileInputStream fileInputStream = new FileInputStream(voucherFilePath)) {
            while (true) {
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                Voucher voucher = (Voucher)objectInputStream.readObject();
                vouchers.add(voucher);
            }
        } catch (EOFException | FileNotFoundException e) {
            return vouchers;
        } catch (IOException | ClassNotFoundException e) {
            logger.error("Fail to open the File");
            throw new WrongFileException();
        }
    }

    @Override
    public Voucher update(Voucher voucher) {
        throw new AssertionError();
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        throw new AssertionError();
    }

    @Override
    public List<Voucher> findByTypeAndDate(int type, LocalDateTime start, LocalDateTime end) {
        throw new AssertionError();
    }

    @Override
    public void deleteById(UUID voucherId) {
        throw new AssertionError();
    }

    @Override
    public void deleteAll() {
        throw new AssertionError();
    }

    @Override
    public Voucher assignCustomer(Voucher voucher) {
        throw new AssertionError();
    }

    @Override
    public List<Voucher> findByCustomerId(UUID customerId) {
        throw new AssertionError();
    }

    @Override
    public List<Voucher> findByCustomerEmail(String customerEmail) {
        throw new AssertionError();
    }

    @Override
    public List<Voucher> findNotAssign() {
        throw new AssertionError();
    }

    @Override
    public List<Voucher> findAssigned() {
        throw new AssertionError();
    }
}
