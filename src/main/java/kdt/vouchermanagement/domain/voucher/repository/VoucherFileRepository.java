package kdt.vouchermanagement.domain.voucher.repository;

import kdt.vouchermanagement.domain.voucher.domain.Voucher;
import kdt.vouchermanagement.domain.voucher.domain.VoucherType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

@Profile("file")
@Repository
public class VoucherFileRepository implements VoucherRepository {

    private final String voucherFilePath;

    public VoucherFileRepository(@Value("${voucher.path}") String voucherFilePath) {
        this.voucherFilePath = voucherFilePath;
    }

    @PostConstruct
    public void init() {
        File filePath = new File(voucherFilePath);
        if (!filePath.exists()) {
            filePath.mkdirs();
        }
    }

    @PreDestroy
    public void destory() {
        File[] files = new File(voucherFilePath).listFiles();
        for (File file : files) {
            file.delete();
        }
    }

    @Override
    public Voucher save(Voucher voucher) {
        if (voucher == null) {
            throw new IllegalArgumentException("애플리케이션이 null을 사용하려고 합니다.");
        }

        if (voucher.getVoucherId() == null) {
            Voucher entity = createEntity(voucher.getVoucherType(), voucher.getDiscountValue());
            return saveVoucher(entity);
        }

        return saveVoucher(voucher);
    }

    @Override
    public List<Voucher> findAll() {
        return getVouchers();
    }

    @Override
    public Optional<Voucher> findById(Long voucherId) {
        return Optional.empty();
    }

    @Override
    public void deleteById(Long voucherId) {
    }

    private Voucher saveVoucher(Voucher entity) {
        String savePath = new StringBuilder()
                .append(voucherFilePath)
                .append(File.separator)
                .append(entity.getVoucherId())
                .toString();

        if (new File(savePath).exists())  {
            new File(savePath).delete();
        }

        try (FileOutputStream fileOutputStream = new FileOutputStream(savePath);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(entity);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("직렬화된 객체를 스트림에 쓰는 동안 에러가 발생하였습니다.");
        }

        return entity;
    }

    private Voucher createEntity(VoucherType voucherType, int discountValue) {
        Long voucherId = VoucherIdGenerator.idGenerate();
        Voucher entity = voucherType.createEntity(voucherId, discountValue, LocalDateTime.now(), LocalDateTime.now());
        return entity;
    }

    private List<Voucher> getVouchers() {
        File[] files = new File(voucherFilePath).listFiles();

        if (files == null)  {
            return Collections.emptyList();
        }

        List<Voucher> vouchers = new ArrayList<>();

        Arrays.stream(files).forEach(file -> {
            try (
                    FileInputStream fileInputStream = new FileInputStream(file.getPath());
                    ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)
            ) {
                vouchers.add((Voucher)objectInputStream.readObject());
            } catch (IOException e) {
                e.printStackTrace();
                throw new IllegalStateException("스트림으로부터 읽는 동안 입출력 에러가 발생했습니다.");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                throw new IllegalStateException("직렬화된 객체의 클래스를 찾을 수 없습니다.");
            }
        });

        return vouchers;
    }
}
