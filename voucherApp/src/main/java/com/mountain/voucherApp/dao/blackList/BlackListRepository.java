package com.mountain.voucherApp.dao.blackList;

import java.io.IOException;
import java.util.List;

public interface BlackListRepository {
    List<BlackListFileFormat> getBlackList() throws IOException;
}
