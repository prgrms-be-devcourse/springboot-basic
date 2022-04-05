package org.programmers.devcourse.voucher.engine;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Optional;
import java.util.UUID;
import org.programmers.devcourse.voucher.engine.io.Input;
import org.programmers.devcourse.voucher.engine.io.Output;
import org.programmers.devcourse.voucher.engine.voucher.Voucher;
import org.programmers.devcourse.voucher.engine.voucher.VoucherService;
import org.springframework.stereotype.Component;

@Component
public class Demo {

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

    try (input; output) {

      while (true) {
        Optional<MenuSelection> optionalSelection = input.getSelection();
        if (optionalSelection.isEmpty()) {
          output.print("Wrong command");
          continue;
        }

        switch (optionalSelection.get()) {
          case EXIT:
            output.print("Good Bye");
            return;
          case CREATE:
            createVoucher();
            break;
          case LIST:
            output.print("LIST!");
        }


      }

    } catch (Exception e) {
      e.printStackTrace();
      output.print("Error! terminating application");
    }

  }

  private void createVoucher() throws IOException {
    // 사용자로 부터 바우처 타입을 입력 받는다.
    Voucher.Type voucherType = input.getVoucherType();
    long voucherDiscountData = input.getVoucherDiscountData(voucherType);

    // 바우처 타입이 증가할 때 마다 어떻게 받지?
    // 바우처 타입에 따라 콘솔에 출력할 값이 다르다?

    UUID voucherId = voucherService.create(voucherType, voucherDiscountData);
    output.print(MessageFormat.format("CREATE SUCCESS! VoucherID : {0}", voucherId));
  }
}
