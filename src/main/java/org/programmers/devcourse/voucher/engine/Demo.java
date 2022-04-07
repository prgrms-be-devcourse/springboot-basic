package org.programmers.devcourse.voucher.engine;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Optional;
import java.util.UUID;
import org.programmers.devcourse.voucher.engine.exception.NoSuchOptionException;
import org.programmers.devcourse.voucher.engine.exception.VoucherException;
import org.programmers.devcourse.voucher.engine.io.Input;
import org.programmers.devcourse.voucher.engine.io.Output;
import org.programmers.devcourse.voucher.engine.voucher.Voucher.VoucherType;
import org.programmers.devcourse.voucher.engine.voucher.VoucherService;
import org.programmers.devcourse.voucher.util.ExceptionFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Demo {

  private static final Logger logger = LoggerFactory.getLogger(Demo.class);
  private final Input input;
  private final Output output;
  private final VoucherService voucherService;

  public Demo(Input input, Output output,
      VoucherService voucherService) {
    this.input = input;
    this.output = output;
    this.voucherService = voucherService;
  }


  public void start() {
    logger.info("User started application");
    try (input; output) {

      while (true) {
        Optional<MenuSelection> optionalSelection = input.getSelection();
        try {
          switch (optionalSelection.orElseThrow(
              NoSuchOptionException::new)) {
            case EXIT:
              logger.info("User shut down application");
              output.print("=====Good Bye=====");
              return;
            case CREATE:
              createVoucher();
              break;
            case LIST:
              showAllVouchers();
          }

        } catch (VoucherException e) {
          output.print(e.getMessage());
        }
      }
    } catch (Exception e) {
      // VoucherException 외 치명적 오류 (파일로 보관 예정)
      logger.error(
          ExceptionFormatter.formatExceptionForLogger(e));
      output.print("ERROR : Terminating Process");
    }

  }

  private void showAllVouchers() {
    output.printAllVouchers(voucherService.getAllVouchers());


  }

  private void createVoucher() throws IOException, VoucherException, ReflectiveOperationException {
    // 사용자로 부터 바우처 타입을 입력 받는다.
    VoucherType voucherType = input.getVoucherType();
    long voucherDiscountData = input.getVoucherDiscountData(voucherType);

    UUID voucherId = voucherService.create(voucherType, voucherDiscountData);
    output.print(MessageFormat.format("CREATE SUCCESS! VoucherID : {0}", voucherId));


  }
}
