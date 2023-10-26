package devcourse.springbootbasic.util;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

class CsvFileHandlerTest {

    private final static String filePath = "src/test/resources/test.csv";
    private final File file = new File(filePath);
    private CsvFileHandler csvFileHandler;

    @BeforeEach
    void setup() throws IOException {
        if (file.createNewFile()) System.out.println("File created: " + file.getName());
        else System.out.println("File already exists.");
        csvFileHandler = new CsvFileHandler(filePath);
    }

    @AfterEach
    void tearDown() {
        if (file.delete()) System.out.println("Deleted the file: " + file.getName());
        else System.out.println("Failed to delete the file.");
    }

    @Test
    @DisplayName("CSV 파일에 데이터를 읽고 쓸 수 있습니다.")
    void testWriteAndRead() {
        // Given
        String csvLineTemplate = "%d,%s,%d";
        List<Person> personList = new ArrayList<>();
        personList.add(new Person(1, "Ogu", 59));
        personList.add(new Person(2, "Platypus", 95));
        personList.add(new Person(3, "Dduzy", 29));

        // When
        csvFileHandler.writeListToCsv(personList, person -> String.format(csvLineTemplate, person.id, person.name, person.age));
        List<Person> parsedData = csvFileHandler.readListFromCsv(line -> new Person(Integer.parseInt(line[0]), line[1], Integer.parseInt(line[2])), csvLineTemplate);

        // Then
        assertThat(parsedData).hasSize(3);
        assertThat(parsedData.get(0)).isEqualTo(personList.get(0));
        assertThat(parsedData.get(1)).isEqualTo(personList.get(1));
        assertThat(parsedData.get(2)).isEqualTo(personList.get(2));
    }


    static class Person {
        private final int id;
        private final String name;
        private final int age;

        public Person(int id, String name, int age) {
            this.id = id;
            this.name = name;
            this.age = age;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Person person = (Person) o;
            return id == person.id && age == person.age && Objects.equals(name, person.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, name, age);
        }
    }
}
