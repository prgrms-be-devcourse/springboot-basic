package com.prgmrs.voucher.model.validator;

import com.prgmrs.voucher.model.Voucher;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class OrderValidator {
    public boolean isValidOrder(String order, List<Voucher> voucherList) {
        Pattern pattern = Pattern.compile("^[0-9]+$");
        Matcher matcher = pattern.matcher(order);
        long convertedOrder = Long.parseLong(order);

        boolean hasOnlyNumbers = matcher.matches();
        boolean isValidIndex = convertedOrder > 0 && convertedOrder <= voucherList.size();
        return hasOnlyNumbers && isValidIndex;
    }
}
