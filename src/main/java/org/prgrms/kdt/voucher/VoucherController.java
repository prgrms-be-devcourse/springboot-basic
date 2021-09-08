package org.prgrms.kdt.voucher;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by yhh1056
 * Date: 2021/09/08 Time: 2:55 오후
 */

@Controller
public class VoucherController {

    @GetMapping("/admin")
    public String admin() {
        return "admin/main";
    }

}
