package study.dev.spring.common.utils;

import com.opencsv.bean.CsvBindByName;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class TestUser {

	@CsvBindByName
	private String name;

	@CsvBindByName
	private int age;
}
