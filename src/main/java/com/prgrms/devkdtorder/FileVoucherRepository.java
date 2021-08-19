package com.prgrms.devkdtorder;

import org.ini4j.Wini;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Primary
@Repository
public class FileVoucherRepository implements VoucherRepository, InitializingBean {

    private Wini wini;
    private final String voucherIniPath = System.getProperty("user.home") + "\\" + "voucher.ini";
    private final String OPTION_TYPE = "type";
    private final String OPTION_VALUE = "value";

    @Override
    public Optional<Voucher> findById(UUID voucherId) {

        return getVoucherFromIni(voucherId.toString());
    }

    @Override
    public void save(Voucher voucher) {
        UUID voucherId = voucher.getVoucherId();
        long value = voucher.getValue();
        wini.put(voucherId.toString(), OPTION_VALUE, value);
        wini.put(voucherId.toString(), OPTION_TYPE, voucher.getClass().getSimpleName().replace("Voucher",""));
        try {
            wini.store();
        } catch (IOException ignored) {}
    }

    @Override
    public List<Voucher> findAll() {
        List<Voucher> vouchers = new ArrayList<>();
        for (String voucherId : wini.keySet()) {

            Optional<Voucher> voucher = getVoucherFromIni(voucherId);
            voucher.ifPresent(vouchers::add);
        }
        return vouchers;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        File ini = new File(voucherIniPath);
        if (!ini.exists()) {
            ini.createNewFile();
        }
        wini = new Wini(ini);
    }

    private Optional<Voucher> getVoucherFromIni(String voucherId){
        long value = wini.get(voucherId, OPTION_VALUE,long.class);
        String type = wini.get(voucherId, OPTION_TYPE);

        Optional<VoucherType> voucherType = VoucherType.findByNameOrNo(type);
        return voucherType.map(v -> VoucherFactory.create(v, value));
    }
}
