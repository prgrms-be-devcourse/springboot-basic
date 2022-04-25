package org.programmers.devcourse.voucher.engine;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;
import org.programmers.devcourse.voucher.engine.blacklist.BlackList;
import org.programmers.devcourse.voucher.engine.blacklist.BlackListService;
import org.programmers.devcourse.voucher.engine.exception.VoucherException;
import org.programmers.devcourse.voucher.engine.io.Input;
import org.programmers.devcourse.voucher.engine.io.Output;
import org.programmers.devcourse.voucher.engine.voucher.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class CliVoucherApplication {

  private static final Logger logger = LoggerFactory.getLogger(CliVoucherApplication.class);
  private final Input input;
  private final Output output;
  private final VoucherService voucherService;
  private final BlackListService blackListService;

  public CliVoucherApplication(Input input, Output output, VoucherService voucherService, BlackListService blackListService) {
    this.input = input;
    this.output = output;
    this.voucherService = voucherService;
    this.blackListService = blackListService;
  }

  public void start() {
    while (true) {
      try {
        switch (input.getSelection()) {
          case EXIT:
            output.print("=====Good Bye=====");
            return;
          case CREATE:
            createVoucher();
            break;
          case LIST:
            showAllVouchers();
            break;
          case BLACKLIST:
            showBlacklist();
            break;
          default:
        }

      } catch (VoucherException exception) {
        // 치명적이지 않은 오류 : 프로그램을 계속 실행한다.
        output.print(exception.getMessage());
        logger.error(exception.getClass().getCanonicalName(), exception);
      } catch (Exception exception) {
        // 치명적인 오류 : 프로그램을 반드시 종료해야 한다.
        logger.error("critical error", exception);
        return;
      }
    }
  }

  private void showBlacklist() {
    List<BlackList> list = blackListService.getBlackList();
    output.printBlackList(list);
  }

  private void showAllVouchers() {
    output.printAllVouchers(voucherService.getAllVouchers());
  }

  private void createVoucher() throws IOException, VoucherException {

    String voucherTypeId = input.getVoucherTypeId();
    long voucherDiscountData = input.getDiscountDegree(voucherTypeId);

    UUID voucherId = voucherService.create(voucherTypeId, voucherDiscountData);

    output.print(MessageFormat.format("CREATE SUCCESS! VoucherID : {0}", voucherId));
  }
}
