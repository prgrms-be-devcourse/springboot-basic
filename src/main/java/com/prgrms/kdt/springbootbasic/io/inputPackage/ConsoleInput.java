package com.prgrms.kdt.springbootbasic.io.inputPackage;

import com.prgrms.kdt.springbootbasic.VoucherList;
import com.prgrms.kdt.springbootbasic.returnFormats.VoucherInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class ConsoleInput implements CustomInput{
    private static final Logger logger = LoggerFactory.getLogger(ConsoleInput.class);

    @Override
    public Optional<String> getCommand(){
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))){
            System.out.print("Please type command : ");
            return Optional.of(bufferedReader.readLine().strip());
        }catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public Optional<VoucherInfo> getNewVoucherInfo() {
        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.print("Please enter the type of voucher : ");
            int voucherType = Integer.parseInt(bufferedReader.readLine().strip());
            List<String> voucherList = Stream.of(VoucherList.values()).map(v -> v.getClassName()).collect(Collectors.toList());

            while (voucherType < 0 || voucherType >= voucherList.size()) {
                logger.info(MessageFormat.format("[ConsoleInput : getNewVoucherInfo] Wrong voucherType has been inputted : {0}", voucherType));
                System.out.println("Please Enter Right Command");
                System.out.print("Please enter the type of voucher : ");
                voucherType = Integer.parseInt(bufferedReader.readLine().strip());
            }

            System.out.print("Please enter the discount amount : ");
            long discountAmount = Integer.parseInt(bufferedReader.readLine().strip());

            while (discountAmount <= 0) {
                logger.info(MessageFormat.format("[ConsoleInput : getNewVoucherInfo] Wrong discountAmount has been inputted : {0}", discountAmount));
                System.out.println("Please Enter Right Amount which is bigger than 0");
                System.out.print("Please enter the discount amount : ");
                discountAmount = Integer.parseInt(bufferedReader.readLine().strip());
            }

            logger.info(MessageFormat.format("[ConsoleInput : getNewVoucherInfo] voucher Type : {0} | discountAmount : {1}", voucherType, discountAmount));

            return Optional.of(new VoucherInfo(voucherList.get(voucherType), discountAmount));
        }catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

}
