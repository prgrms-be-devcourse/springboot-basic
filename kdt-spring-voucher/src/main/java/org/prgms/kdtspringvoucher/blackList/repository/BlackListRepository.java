package org.prgms.kdtspringvoucher.blackList.repository;

import org.prgms.kdtspringvoucher.blackList.domain.BlackList;

import java.io.IOException;
import java.util.List;

public interface BlackListRepository {
    List<BlackList> findAll();
}
