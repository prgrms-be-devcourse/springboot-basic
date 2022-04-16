package com.prgrms.kdt.springbootbasic.inputPackage;

import com.prgrms.kdt.springbootbasic.controller.VoucherController;
import com.prgrms.kdt.springbootbasic.returnFormats.VoucherInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.MessageFormat;

@Component
public class ConsoleInput implements CustomInput{
    private static final Logger logger = LoggerFactory.getLogger(ConsoleInput.class);

    private static BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    @Override
    public String getCommand() throws IOException {
        System.out.print("Please type command : ");
        return bufferedReader.readLine().strip();

    }

    @Override
    public VoucherInfo getNewVoucherInfo() throws IOException {
        System.out.print("Please enter the type of voucher : ");
        int voucherType = Integer.parseInt(bufferedReader.readLine().strip());

        while (voucherType<1 || voucherType>2){
            logger.info(MessageFormat.format("[ConsoleInput : getNewVoucherInfo] Wrong voucherType has been inputted : {0}", voucherType));
            System.out.println("Please Enter Right Type (1,2)");
            System.out.print("Please enter the type of voucher : ");
            voucherType = Integer.parseInt(bufferedReader.readLine().strip());
        }

        System.out.print("Please enter the discount amount : ");
        long discountAmount = Integer.parseInt(bufferedReader.readLine().strip());

        while (discountAmount <= 0){
            logger.info(MessageFormat.format("[ConsoleInput : getNewVoucherInfo] Wrong discountAmount has been inputted : {0}", discountAmount));
            System.out.println("Please Enter Right Amount which is bigger than 0");
            System.out.print("Please enter the discount amount : ");
            discountAmount = Integer.parseInt(bufferedReader.readLine().strip());
        }

        logger.info(MessageFormat.format("[ConsoleInput : getNewVoucherInfo] voucher Type : {0} | discountAmount : {1}", voucherType, discountAmount));

        return new VoucherInfo(voucherType,discountAmount);
    }
}
