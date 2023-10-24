package study.dev.spring.customer.application.dto;

import com.opencsv.bean.CsvBindByName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerData {

	@CsvBindByName
	private String uuid;
	@CsvBindByName
	private String name;
}
