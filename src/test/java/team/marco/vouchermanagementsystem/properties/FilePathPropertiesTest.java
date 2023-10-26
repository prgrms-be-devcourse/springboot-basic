package team.marco.vouchermanagementsystem.properties;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.not;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import team.marco.vouchermanagementsystem.configuration.PropertyConfiguration;

@SpringBootTest(classes = PropertyConfiguration.class)
@EnableConfigurationProperties(FilePathProperties.class)
public class FilePathPropertiesTest {
    @Autowired
    FilePathProperties filePathProperties;

    @Test
    @DisplayName("파일 경로 프로퍼티는 비어있으면 안된다.")
    void testConfiguration() {
        // given
        String blacklistPath = filePathProperties.blacklist();
        String voucherDataPath = filePathProperties.voucherData();

        // when

        // then
        assertThat(blacklistPath, not(emptyOrNullString()));
        assertThat(voucherDataPath, not(emptyOrNullString()));
    }
}
