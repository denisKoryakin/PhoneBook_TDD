import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class PhoneBookTest {
    @BeforeAll
    public static void beforeAllTests() {
        System.out.println("All PhoneBookTests started");
    }

    @AfterAll
    public static void afterAllTests() {
        System.out.println("All PhoneBookTests completed");
    }

    PhoneBook phoneBook = PhoneBook.get();

    @ParameterizedTest
    @MethodSource("addParametersDefinition")
    public void testAdd(String name, String number, int count) {
//    arrange
//    act
        int result = phoneBook.add(name, number);
//    assert
        Assertions.assertEquals(count, result);
    }

    private static Stream<Arguments> addParametersDefinition() {
        return Stream.of(
                Arguments.of("Иван", "89527812756", 1),
                Arguments.of("Ирина", "87538956759", 2),
                Arguments.of("Иван", "89529532658", 2),
                Arguments.of("Петр", "89634478956", 3)
        );
    }

    @ParameterizedTest
    @MethodSource("findByNumberParametersDefinition")
    public void testFindByNumber(String name, String number) {
//    arrange
        phoneBook.add("Иван", "89527812756");
        phoneBook.add("Ирина", "87538956759");
//    act
        String result = phoneBook.findByNumber(number);
//    assert
        Assertions.assertEquals(name, result);
    }

    private static Stream<Arguments> findByNumberParametersDefinition() {
        return Stream.of(
                Arguments.of("Иван", "89527812756"),
                Arguments.of("Ирина", "87538956759"),
                Arguments.of(null, "99009090900")
        );
    }
}
