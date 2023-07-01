package com.programmers.voucher.stream.blacklist;

import java.io.IOException;
import java.util.List;

public interface BlackListStream {
    List<String> findAll() throws IOException;
}
