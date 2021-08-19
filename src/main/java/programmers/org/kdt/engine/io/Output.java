package programmers.org.kdt.engine.io;

import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;
import programmers.org.kdt.engine.voucher.Voucher;

public interface Output {
    void help();

    void list(Set<Entry<UUID, Voucher>> entrySet);

	void error();
}
