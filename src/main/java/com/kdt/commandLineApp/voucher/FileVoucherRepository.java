package com.kdt.commandLineApp.voucher;

import com.kdt.commandLineApp.exception.FileSaveException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toMap;

@Repository
@Profile("prod")
public class FileVoucherRepository implements VoucherRepository {
    private String fileName;
    private Map<UUID, Voucher> map = new ConcurrentHashMap<>();

    //프로퍼티 클래스에서 설정할 수 있는 심화 개념 (@EnableConfigurationProperties 참고)
    public FileVoucherRepository(@Value("${voucher_info}") String fileName) {
        try {
            this.fileName = fileName;
            loadFile(fileName);
        }
        catch (Exception ignored) {
        }
    }

    public void loadFile(String fileName) throws Exception {
        List<Voucher> vouchers = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream(fileName);
            ObjectInputStream ois = new ObjectInputStream(fis);
            vouchers = (List) ois.readObject();
            map = (Map<UUID, Voucher>) vouchers.stream().collect(toMap((e)-> e.getId(),(e)-> e));
        }
        catch (Exception e) {
            throw new FileSaveException();
        }
    }

    public void savefile() throws IOException {
        System.out.println("df");
        List<Voucher> vouchers = map.values().stream().collect(toCollection(ArrayList::new));

        try {
            FileOutputStream fop = new FileOutputStream(fileName);
            ObjectOutputStream oos = new ObjectOutputStream(fop);
            oos.writeObject(vouchers);
        }
        catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void add(Voucher voucher) {
        map.put(voucher.getId(), voucher);
        try {
            //DisposableBean을 이용한 빈 객체 소멸 전 저장 메소드 호출이 안되네요..
            savefile();
        } catch (Exception e) {
        }
    }

    @Override
    public Optional<Voucher> get(UUID id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public List<Voucher> getAll() {
        return map.values().stream().collect(toCollection(ArrayList::new));
    }

    @Override
    public void remove(UUID vid) {

    }
}
