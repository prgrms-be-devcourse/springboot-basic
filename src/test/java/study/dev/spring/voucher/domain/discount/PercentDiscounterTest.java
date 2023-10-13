package study.dev.spring.voucher.domain.discount;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("[PercentDiscounter Test] - Domain Layer")
public class PercentDiscounterTest {

	@Test
	@DisplayName("정률 할인을 수행한다")
	void discountTest() {
		//given
		PercentDiscounter percentDiscounter = new PercentDiscounter();

		//when
		double actual = percentDiscounter.discount(1000, 20);

		//then
		assertThat(actual).isEqualTo(800);
	}
}
