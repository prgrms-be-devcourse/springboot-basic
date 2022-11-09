package com.programmers.voucher.repository.loader;

import com.programmers.voucher.voucher.Voucher;
import com.programmers.voucher.voucher.VoucherValidator;
import org.ini4j.Profile;
import org.ini4j.Wini;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.programmers.voucher.repository.FileVoucherRepository.VOUCHER_TYPE;
import static com.programmers.voucher.repository.FileVoucherRepository.VOUCHER_VALUE;

@Component
public class ListLoader implements Loader {
    private final Wini wini;

    @Autowired
    public ListLoader(Wini wini) {
        this.wini = wini;
    }

    @Override
    public void load(List<Voucher> loadStore) {
        for (Map.Entry<String, Profile.Section> sections : wini.entrySet()) {
            if (sections.getValue() != null) {
                Profile.Section section = sections.getValue();

                String id = section.getName();
                String type = section.get(VOUCHER_TYPE);
                long value = section.get(VOUCHER_VALUE, long.class);

                Voucher voucher = VoucherValidator.getValidateVoucherType(type)
                        .createVoucher(UUID.fromString(id), value);

                loadStore.add(voucher);
            }
        }
    }
}
