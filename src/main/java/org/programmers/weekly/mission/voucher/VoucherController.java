package org.programmers.weekly.mission.voucher;

import org.springframework.web.bind.annotation.GetMapping;

public class VoucherController {

    @GetMapping("")
    public String viewMainPage() {
        return "views/main";
    }
}
