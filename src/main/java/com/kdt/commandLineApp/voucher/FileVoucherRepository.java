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

    public FileVoucherRepository(@Value("${voucher_info}") String fileName) {
        try {
            this.fileName = fileName;
            loadFile(fileName);
        }
        catch (Exception e) {
            //no need to throw exception (it can use empty array list)
            e.printStackTrace();
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
        try {
            map.put(voucher.getId(), voucher);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Voucher> get(String id) {
        return Optional.ofNullable(map.get(UUID.fromString(id)));
    }

    @Override
    public List<Voucher> getAll() {
        return map.values().stream().collect(toCollection(ArrayList::new));
    }

    @Override
    public void remove(String id) {
        map.remove(UUID.fromString(id));
    }

    @Override
    public void deleteAll() {
        map.clear();
    }

    @Override
    public void destroy() throws Exception {
        savefile();
    }
}
