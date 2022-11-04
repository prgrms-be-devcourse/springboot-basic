package prgms.vouchermanagementapp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import prgms.vouchermanagementapp.controller.RunningState;
import prgms.vouchermanagementapp.controller.VoucherManagementController;
import prgms.vouchermanagementapp.io.Reader;
import prgms.vouchermanagementapp.io.Writer;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@SpringBootApplication
@Component
public class VoucherManagementApp implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(VoucherManagementApp.class, args);
	}

	@Override
	public void run(String... args) {
		Reader reader = new Reader(new BufferedReader(new InputStreamReader(System.in)));
		Writer writer = new Writer();
		RunningState state = new RunningState();
		VoucherManagementController controller = new VoucherManagementController(reader, writer, state);

		Thread voucherManagementThread = new Thread(controller);
		voucherManagementThread.start();
		voucherManagementThread.interrupt();
	}
}
