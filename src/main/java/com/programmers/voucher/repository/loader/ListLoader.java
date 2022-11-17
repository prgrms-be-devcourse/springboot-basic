package com.programmers.voucher.repository.loader;

import com.programmers.voucher.voucher.Voucher;
import com.programmers.voucher.voucher.VoucherType;
import org.ini4j.Profile.Section;
import org.ini4j.Wini;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import static com.programmers.voucher.repository.FileVoucherRepository.VOUCHER_TYPE;
import static com.programmers.voucher.repository.FileVoucherRepository.VOUCHER_VALUE;
import static com.programmers.voucher.voucher.VoucherFactory.createVoucher;
import static com.programmers.voucher.voucher.VoucherType.getValidateVoucherType;
import static java.util.UUID.fromString;

@Profile("file")
@Component
public class ListLoader implements Loader {
    private final Wini wini;

    @Autowired
    public ListLoader(Wini wini) {
        this.wini = wini;
    }

    @Override
    public void load(Map<UUID, Voucher> cacheMap) {
        for (Entry<String, Section> sections : wini.entrySet()) {
            if (sections.getValue() != null) {
                Section section = sections.getValue();

                String id = section.getName();
                UUID uuid = fromString(id);
                String type = section.get(VOUCHER_TYPE);
                long value = section.get(VOUCHER_VALUE, long.class);


                VoucherType voucherType = getValidateVoucherType(type.substring(0, 1));
                Voucher voucher = createVoucher(uuid, voucherType, value);

                cacheMap.put(uuid, voucher);
            }
        }
    }
}
