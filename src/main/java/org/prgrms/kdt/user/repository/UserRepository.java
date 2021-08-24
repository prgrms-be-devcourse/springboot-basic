package org.prgrms.kdt.user.repository;

import org.prgrms.kdt.user.domain.BannedCustomer;

import java.io.IOException;
import java.util.List;

public interface UserRepository {
    public List<BannedCustomer> getBlackListCSV() throws IOException;
}
