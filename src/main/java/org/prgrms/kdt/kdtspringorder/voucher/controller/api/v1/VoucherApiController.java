package org.prgrms.kdt.kdtspringorder.voucher.controller.api.v1;

import lombok.RequiredArgsConstructor;
import org.prgrms.kdt.kdtspringorder.voucher.repository.VoucherJdbcRepository;
import org.prgrms.kdt.kdtspringorder.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class VoucherApiController {

    private static final Logger logger = LoggerFactory.getLogger(VoucherJdbcRepository.class);

    private final VoucherService voucherService;

}
