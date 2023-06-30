package com.devcourse;

import com.devcourse.console.Command;
import com.devcourse.console.Console;
import com.devcourse.voucher.application.dto.CreateVoucherRequest;
import com.devcourse.voucher.presentation.VoucherController;
import com.devcourse.voucher.presentation.dto.ApplicationRequest;
import com.devcourse.voucher.presentation.dto.ApplicationResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.StringTokenizer;

@Component
public class ConsoleRunner {
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final String NOT_SUPPORT_DATE_FORMAT = "[Error] Your Input is incorrect Date Format : ";
    public static final String NOT_A_NUMBER = "[Error] Your Input is not a Number : ";

    private final VoucherController voucherController;
    private final Console console = new Console();

    public ConsoleRunner(VoucherController voucherController) {
        this.voucherController = voucherController;
    }

    public void run() {
        boolean power = true;
        console.greeting();

        while (power) {
            ApplicationRequest request = createRequest();
            ApplicationResponse response = voucherController.mapService(request);
            console.write(response.payload());
            power = response.power();
        }
    }

    private ApplicationRequest createRequest() {
        String inputCommand = console.readCommand();
        Command command = Command.from(inputCommand);

        if (isCreation(command)) {
            String information = console.readVoucherInformation();
            return initCreationRequest(command, information);
        }

        return new ApplicationRequest<>(command, null);
    }

    private ApplicationRequest initCreationRequest(Command command, String information) {
        StringTokenizer tokenizer = new StringTokenizer(information, ",");
        CreateVoucherRequest request = createVoucherRequest(tokenizer);

        return new ApplicationRequest(command, request);
    }

    private CreateVoucherRequest createVoucherRequest(StringTokenizer tokenizer) {
        String type = tokenizer.nextToken();
        int discount = paresDiscount(tokenizer.nextToken());
        LocalDateTime expiredAt = paresExpiration(tokenizer.nextToken());

        return new CreateVoucherRequest(type, discount, expiredAt);
    }

    private LocalDateTime paresExpiration(String expiredAt) {
        try {
            LocalDate localDate = LocalDate.parse(expiredAt, timeFormatter);
            return LocalDateTime.of(localDate, LocalTime.MIDNIGHT);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(NOT_SUPPORT_DATE_FORMAT + expiredAt);
        }
    }

    private Integer paresDiscount(String discount) {
        try {
            return Integer.parseInt(discount);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(NOT_A_NUMBER + discount);
        }
    }

    private boolean isCreation(Command command) {
        return command == Command.CREATE;
    }
}
