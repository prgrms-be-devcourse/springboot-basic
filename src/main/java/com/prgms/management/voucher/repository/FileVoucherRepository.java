package com.prgms.management.voucher.repository;

import com.prgms.management.common.exception.EmptyListException;
import com.prgms.management.common.exception.FindFailException;
import com.prgms.management.common.exception.SaveFailException;
import com.prgms.management.voucher.model.FixedAmountVoucher;
import com.prgms.management.voucher.model.PercentDiscountVoucher;
import com.prgms.management.voucher.model.Voucher;
import com.prgms.management.voucher.model.VoucherType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
@Profile({"dev"})
public class FileVoucherRepository implements VoucherRepository {
    private final Resource resource;
    
    public FileVoucherRepository(@Value("${database.file.voucher}") String filename) {
        DefaultResourceLoader defaultResourceLoader = new DefaultResourceLoader();
        this.resource = defaultResourceLoader.getResource(filename);
    }
    
    @Override
    public Voucher findById(UUID voucherId) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(resource.getFile()))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] array = line.split(",");
                if (voucherId.equals(UUID.fromString(array[1]))) {
                    if (array[0].equals(PercentDiscountVoucher.class.getCanonicalName())) {
                        return new PercentDiscountVoucher(UUID.fromString(array[1]), Integer.parseInt(array[2]));
                    } else if (array[0].equals(FixedAmountVoucher.class.getCanonicalName())) {
                        return new FixedAmountVoucher(UUID.fromString(array[1]), Integer.parseInt(array[2]));
                    }
                }
            }
        } catch (IOException e) {
            throw new FindFailException();
        }
        throw new FindFailException();
    }
    
    @Override
    public List<Voucher> findByType(VoucherType type) {
        // TODO Type에 따른 바우처 목록을 CSV 파일에서 찾아서 반환
        throw new UnsupportedOperationException();
    }
    
    @Override
    public List<Voucher> findByDate(Timestamp start, Timestamp end) {
        // TODO 생성일에 따른 바우처 목록을 CSV 파일에서 찾아서 반환
        throw new UnsupportedOperationException();
    }
    
    @Override
    public List<Voucher> findByTypeAndDate(VoucherType type, Timestamp start, Timestamp end) {
        // TODO 생성일과 Type에 따른 바우처 목록을 CSV 파일에서 찾아서 반환
        throw new UnsupportedOperationException();
    }
    
    @Override
    public List<Voucher> findAll() {
        List<Voucher> vouchers = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(resource.getFile()))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] array = line.split(",");
                if (array[0].equals(PercentDiscountVoucher.class.getCanonicalName())) {
                    vouchers.add(new PercentDiscountVoucher(UUID.fromString(array[1]), Integer.parseInt(array[2])));
                } else if (array[0].equals(FixedAmountVoucher.class.getCanonicalName())) {
                    vouchers.add(new FixedAmountVoucher(UUID.fromString(array[1]), Integer.parseInt(array[2])));
                } else {
                    throw new FindFailException();
                }
            }
        } catch (IOException e) {
            throw new EmptyListException();
        }
        return vouchers;
    }
    
    @Override
    public Voucher save(Voucher voucher) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(resource.getFile(), true))) {
            bufferedWriter.write(voucher.getStringForCSV());
            bufferedWriter.newLine();
        } catch (IOException e) {
            throw new SaveFailException();
        }
        return voucher;
    }
    
    @Override
    public void removeById(UUID voucherId) {
        // TODO ID에 따른 바우처 정보를 CSV 파일에서 삭제
        throw new UnsupportedOperationException();
    }
}