package com.waterfogsw.voucher.member.service;

import java.io.FileNotFoundException;
import java.util.List;

public interface MemberService {
    List<String> getBlackList() throws FileNotFoundException;
}
