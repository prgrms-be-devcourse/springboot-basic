package com.programmers.order.manager.store;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.programmers.order.domain.Voucher;
import com.programmers.order.dto.VoucherForCsv;
import com.programmers.order.exception.NotCreateFileException;
import com.programmers.order.message.ErrorLogMessage;
import com.programmers.order.message.ErrorMessage;
import com.programmers.order.utils.FileUtils;

@Profile("file")
@Component
public class FileVoucherStoreManager implements VoucherStoreManager {

	private final Logger log = LoggerFactory.getLogger(FileVoucherStoreManager.class);

	private static final String RESOURCE_NAME = "voucher";
	private static final boolean CONTINUE_WRITING = true;

	@Override
	public Voucher saveVoucher(Voucher voucher) {

		File csvFile = this.getCsvFile();
		ObjectWriter writer = this.getWriter();

		try (OutputStreamWriter output = new OutputStreamWriter(new FileOutputStream(csvFile, CONTINUE_WRITING));) {
			writer.writeValues(output)
					.write(voucher);
			output.flush();
		} catch (FileNotFoundException e) {
			log.error(ErrorLogMessage.getLogPrefix(), ErrorLogMessage.NOT_FOUND_FILE);
			e.printStackTrace();
		} catch (IOException e) {
			log.error(ErrorLogMessage.getLogPrefix(), ErrorLogMessage.IO_EXCEPTION);
			e.printStackTrace();
		}

		return voucher;
	}

	@Override
	public List<Voucher> getVouchers() {
		CsvMapper csvMapper = FileUtils.getCsvMapper();
		CsvSchema schema = FileUtils.getSchemaWithHeader(VoucherForCsv.class);

		File csvFile = this.getCsvFile();

		try {
			MappingIterator<Voucher> it = csvMapper
					.readerFor(VoucherForCsv.class)
					.with(schema)
					.readValues(csvFile);

			return it.readAll();
		} catch (IOException e) {
			e.printStackTrace();
			log.error("error : {}", ErrorLogMessage.IO_EXCEPTION);
		}
		return List.of();
	}

	private ObjectWriter getWriter() {
		File csvFile = this.getCsvFile();

		CsvMapper csvMapper = FileUtils.getCsvMapper();

		if (!csvFile.exists()) {
			createFile();
			CsvSchema schema = FileUtils.getSchemaWithHeader(VoucherForCsv.class);

			return csvMapper.writerFor(Voucher.class).with(schema);

		}

		CsvSchema schema = FileUtils.getSchemaWithOutHeader(VoucherForCsv.class);

		return csvMapper.writerFor(Voucher.class).with(schema);
	}

	private void createFile() {
		File csvFile = this.getCsvFile();

		try {
			boolean isSuccessCreate = csvFile.createNewFile();

			if (!isSuccessCreate) {
				throw new NotCreateFileException(ErrorMessage.INTERNAL_PROGRAM_ERROR);
			}

		} catch (IOException exception) {
			exception.printStackTrace();
			log.error(ErrorLogMessage.getLogPrefix(), ErrorLogMessage.IO_EXCEPTION);
		} catch (NotCreateFileException exception) {
			exception.printStackTrace();
			log.error(ErrorLogMessage.getLogPrefix(), ErrorLogMessage.NOT_CREATE_FILE);
		}
	}

	private File getCsvFile() {
		return new File(FileUtils.getWorkingDirectory(), FileUtils.generateCsvFileName(RESOURCE_NAME));
	}

}