package com.programmers.voucher.repository.loader;

import com.programmers.voucher.voucher.Voucher;
import com.programmers.voucher.voucher.VoucherType;
import org.ini4j.Profile;
import org.ini4j.Wini;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

import static com.programmers.voucher.repository.FileVoucherRepository.VOUCHER_TYPE;
import static com.programmers.voucher.repository.FileVoucherRepository.VOUCHER_VALUE;
import static com.programmers.voucher.voucher.VoucherFactory.createVoucher;
import static com.programmers.voucher.voucher.VoucherType.getValidateVoucherType;
import static java.util.UUID.fromString;

@Component
public class ListLoader implements Loader {
    private final Wini wini;

    @Autowired
    public ListLoader(Wini wini) {
        this.wini = wini;
    }


    @Override
    public void load(Map<UUID, Voucher> cacheMap) {
        for (Map.Entry<String, Profile.Section> sections : wini.entrySet()) {
            if (sections.getValue() != null) {
                Profile.Section section = sections.getValue();

                String id = section.getName();
                UUID uuid = fromString(id);
                String type = section.get(VOUCHER_TYPE);
                long value = section.get(VOUCHER_VALUE, long.class);


                VoucherType voucherType = getValidateVoucherType(type);
                Voucher voucher = createVoucher(uuid, voucherType, value);

                cacheMap.put(uuid, voucher);
            }
        }
    }
}
