package org.prgms.management.io.blacklist;

import org.prgms.management.blacklist.vo.Blacklist;
import org.prgms.management.voucher.entity.Voucher;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface Output {
    void init();
    void blacklists(List<Blacklist> blackList);
    void delete(String msg);
}
